package org.growthhungry.service;

import lombok.RequiredArgsConstructor;
import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.MoveSnapshot;
import org.growthhungry.model.Piece;
import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.MoveResultType;
import org.growthhungry.model.enums.PieceType;
import org.growthhungry.model.record.MoveResult;
import org.growthhungry.rule.EnPassantRule;
import org.growthhungry.util.MoveHistoryUtil;
import org.growthhungry.util.PawnMetamorphosisUtil;
import org.growthhungry.validator.factory.MoveValidatorFactory;
import org.growthhungry.validator.move.MoveValidator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static org.growthhungry.util.CastleUtil.getKingY;
import static org.growthhungry.util.CastleUtil.getRookX;

@RequiredArgsConstructor
public class PieceMoveService {
    private final Board board;

    public MoveResult move(Coordinate from, Coordinate to, Color mover) {
        if(!board.isInside(from) || !board.isInside(to)) {
            return MoveResult.of(MoveResultType.FAIL, "Invalid coordinates");
        }
        Piece piece = board.getPieceAt(from.getX(), from.getY());

        if (piece == null || !mover.equals(piece.getColor())) {
            return MoveResult.of(MoveResultType.FAIL, "Invalid coordinates");
        }

        MoveValidator validator = MoveValidatorFactory.getMoveValidator(board, piece.getPieceType(), piece.getColor());
        if (!validator.check(from, to, piece)) {
            return MoveResult.of(MoveResultType.FAIL, "Invalid move");
        }

        var epOpt = EnPassantRule.detect(board, from, to, piece, MoveHistoryUtil.getLast());
        Coordinate epSq = epOpt.map(EnPassantRule.EnPassantCapture::capturedAt).orElse(null);
        Piece epPc = epOpt.map(EnPassantRule.EnPassantCapture::capturedPiece).orElse(null);

        Color opponent = opposite(mover);

        MoveSnapshot snapshot = board.makeMove(from, to);

        if (epPc != null) {
            board.removePiece(epSq);
        }

        boolean isPawnMeta = false;
        int yForPawnsMetamorphosis = mover == Color.WHITE ? 7 : 0;
        if (piece.getPieceType() == PieceType.PAWN && to.getY() == yForPawnsMetamorphosis) {
            PawnMetamorphosisUtil.changePawn(board, to);
            isPawnMeta = true;
        }
        MoveSnapshot castleSnapshot = null;
        boolean kingMoved = piece.getPieceType() == PieceType.KING;
        boolean isCastle = kingMoved && abs(from.getX() - to.getX()) == 2;
        boolean returnKingMoved = false;
        Coordinate prevKing = null;
        if (kingMoved) {
            prevKing = board.getKing(mover);
            if (isCastle) {
                int rookDestX = (from.getX() + to.getX()) / 2;
                int kingY = getKingY(mover);
                int rookX = getRookX(from, to);
                Piece rook = board.getPieceAt(rookX, kingY);
                castleSnapshot = board.makeMove(rook.getCoordinate(), new Coordinate(rookDestX, rook.getCoordinate().getY()));
            }
            board.moveKing(mover, to);
            if (!board.isKingMoved(mover)) {
                returnKingMoved = true;
                board.setKingMoved(mover, true);
            }

        }

        if (isKingInCheck(mover)) {
            if (returnKingMoved) {
                board.setKingMoved(mover, false);
            }
            if (isPawnMeta) {
                piece.setPieceType(PieceType.PAWN);
                board.setPiece(from, piece);
            }
            if (epPc != null) {
                board.setPiece(epSq, epPc);
            }
            if (kingMoved) {
                board.moveKing(mover, prevKing);
            }
            if (isCastle) {
                board.undoMove(castleSnapshot);
            }
            board.undoMove(snapshot);
            return MoveResult.of(MoveResultType.FAIL, "You are under check. Try another move");
        }


        boolean gaveCheck = isKingInCheck(opponent);

        if (gaveCheck) {
            if (isMate(opponent)) {
                return MoveResult.of(MoveResultType.MATE, "Mate, " + mover.name() + " won");
            }
            return MoveResult.of(MoveResultType.CHECK, "CHECK");
        }

        MoveHistoryUtil.add(snapshot);
        return MoveResult.of(MoveResultType.OK, opponent.name() + "s turn to move");
    }

    private boolean isMate(Color defenderColor) {
        Color attackerColor = (defenderColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
        Coordinate kingC = board.getKing(defenderColor);
        if (kingC == null) throw new IllegalStateException("King not found: " + defenderColor);

        if (kingHasEscape(kingC, defenderColor, attackerColor)) {
            return false;
        }

        List<Coordinate> attackers = findAttackers(kingC, attackerColor);

        if (attackers.size() >= 2) {
            return true;
        }

        Coordinate checkerSq = attackers.get(0);
        if (canAnyMoveTo(defenderColor, checkerSq, kingC)) {
            return false;
        }

        Piece checker = board.getPieceAt(checkerSq);
        if (checker != null && checker.getPieceType() != PieceType.KNIGHT) {
            for (Coordinate blockSq : squaresBetween(kingC, checkerSq)) {
                if (canAnyMoveTo(defenderColor, blockSq, kingC)) {
                    return false;
                }
            }
        }

        return true;
    }


    private boolean kingHasEscape(Coordinate kingC, Color defenderColor, Color attackerColor) {
        int kx = kingC.getX(), ky = kingC.getY();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                Coordinate to = new Coordinate(kx + dx, ky + dy);
                if (!board.isInside(to)) continue;

                Piece dst = board.getPieceAt(to);
                if (dst != null && dst.getColor() == defenderColor) continue;

                MoveSnapshot snap = board.makeMove(kingC, to);
                board.moveKing(defenderColor, to);

                boolean safe = !isSquareAttacked(to, attackerColor);

                board.undoMove(snap);
                board.moveKing(defenderColor, kingC);

                if (safe) return true;
            }
        }
        return false;
    }

    private List<Coordinate> findAttackers(Coordinate target, Color byColor) {
        List<Coordinate> res = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board.getPieceAt(x, y);
                if (p == null || p.getColor() != byColor) continue;

                MoveValidator v = MoveValidatorFactory.getMoveValidator(board, p.getPieceType(), p.getColor());
                if (v.check(new Coordinate(x, y), target, p)) {
                    res.add(new Coordinate(x, y));
                }
            }
        }
        return res;
    }

    private boolean canAnyMoveTo(Color defenderColor, Coordinate toSq, Coordinate kingC) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board.getPieceAt(x, y);
                if (p == null || p.getColor() != defenderColor) continue;
                if (p.getPieceType() == PieceType.KING) continue;

                Coordinate from = new Coordinate(x, y);

                MoveValidator v = MoveValidatorFactory.getMoveValidator(board, p.getPieceType(), p.getColor());
                if (!v.check(from, toSq, p)) continue;

                MoveSnapshot snap = board.makeMove(from, toSq);

                boolean kingSafe = !isSquareAttacked(kingC, opposite(defenderColor));

                board.undoMove(snap);

                if (kingSafe) return true;
            }
        }
        return false;
    }

    private List<Coordinate> squaresBetween(Coordinate a, Coordinate b) {
        List<Coordinate> path = new ArrayList<>();
        int dx = Integer.compare(b.getX(), a.getX());
        int dy = Integer.compare(b.getY(), a.getY());

        int x = a.getX() + dx, y = a.getY() + dy;
        while (x != b.getX() || y != b.getY()) {
            path.add(new Coordinate(x, y));
            x += dx;
            y += dy;
        }

        if (!path.isEmpty()) {
            path.remove(path.size() - 1);
        }
        return path;
    }

    private boolean isSquareAttacked(Coordinate target, Color byColor) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board.getPieceAt(x, y);
                if (piece == null || piece.getColor() != byColor) continue;

                MoveValidator v = MoveValidatorFactory.getMoveValidator(board, piece.getPieceType(), piece.getColor());
                if (v.check(new Coordinate(x, y), target, piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isKingInCheck(Color kingColor) {
        Coordinate kingSq = board.getKing(kingColor);
        return isSquareAttacked(kingSq, opposite(kingColor));
    }

    private Color opposite(Color c) {
        return (c == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
}
