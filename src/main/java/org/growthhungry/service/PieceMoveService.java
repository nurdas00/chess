package org.growthhungry.service;

import org.growthhungry.model.enums.PieceType;
import org.growthhungry.model.record.MoveResult;
import org.growthhungry.model.enums.Color;
import org.growthhungry.model.MoveSnapshot;
import org.growthhungry.validator.move.MoveValidator;
import org.growthhungry.validator.factory.MoveValidatorFactory;
import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.Piece;

public class PieceMoveService {

    public MoveResult move(Board board, Coordinate from, Coordinate to, Color mover) {
        Piece piece = board.getPieceAt(from.getX(), from.getY());

        if(piece == null || !mover.equals(piece.getColor())) {
            return MoveResult.fail("Invalid coordinates");
        }

        MoveValidator validator = MoveValidatorFactory.getMoveValidator(piece.getPieceType(), piece.getColor());
        if(!validator.check(from, to, piece, board)) {
            return MoveResult.fail("Invalid move");
        }

        Color opponent = (mover == Color.WHITE) ? Color.BLACK : Color.WHITE;

        MoveSnapshot snap = board.makeMove(from, to);

        if (isKingInCheck(board, mover)) {
            board.undoMove(snap);
            return MoveResult.fail("Check");
        }

        if(piece.getPieceType().equals(PieceType.KING)) {
            board.moveKing(mover, to);
        }

        boolean gaveCheck = isKingInCheck(board, opponent);
        return MoveResult.ok(gaveCheck);
    }

    private boolean isSquareAttacked(Board board, Coordinate target, Color byColor) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board.getPieceAt(x, y);
                if (piece == null || piece.getColor() != byColor) continue;

                MoveValidator v = MoveValidatorFactory.getMoveValidator(piece.getPieceType(), piece.getColor());
                if (v.check(new Coordinate(x, y), target, piece, board)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isKingInCheck(Board board, Color kingColor) {
        Coordinate kingSq = board.getKing(kingColor);
        return isSquareAttacked(board, kingSq, opposite(kingColor));
    }

    private Color opposite(Color c) {
        return (c == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
}
