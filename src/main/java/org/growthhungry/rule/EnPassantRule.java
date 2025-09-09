package org.growthhungry.rule;

import java.util.Optional;
import org.growthhungry.model.*;
import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.PieceType;

public class EnPassantRule {

    private EnPassantRule() {}

    public record EnPassantCapture(Coordinate capturedAt, Piece capturedPiece) {}

    public static Optional<EnPassantCapture> detect(Board board,
                                                    Coordinate from,
                                                    Coordinate to,
                                                    Piece pawn,
                                                    MoveSnapshot lastMove) {
        if (pawn == null || pawn.getPieceType() != PieceType.PAWN) return Optional.empty();

        Piece dest = board.getPieceAt(to);
        int dx = Math.abs(to.getX() - from.getX());
        int dy = to.getY() - from.getY();
        int dir = (pawn.getColor() == Color.WHITE) ? 1 : -1;
        if (!(dx == 1 && dy == dir && dest == null)) return Optional.empty();

        if (lastMove == null) return Optional.empty();
        Piece moved = lastMove.getMoved();
        if (moved == null || moved.getPieceType() != PieceType.PAWN || moved.getColor() == pawn.getColor())
            return Optional.empty();

        int oppStartRank = (pawn.getColor() == Color.WHITE) ? 6 : 1;
        int fromY = lastMove.getFrom().getY();
        int toY   = lastMove.getTo().getY();
        boolean twoStepFromStart = (fromY == oppStartRank) && Math.abs(toY - fromY) == 2;

        boolean sameToX = lastMove.getTo().getX() == to.getX();
        boolean sameFromY  = lastMove.getTo().getY() == from.getY();

        if (!twoStepFromStart || !sameToX || !sameFromY) return Optional.empty();

        Coordinate capturedAt = new Coordinate(to.getX(), from.getY());
        Piece capturedPiece = board.getPieceAt(capturedAt);
        if (capturedPiece == null || capturedPiece.getPieceType() != PieceType.PAWN
                || capturedPiece.getColor() == pawn.getColor()) {
            return Optional.empty();
        }

        return Optional.of(new EnPassantCapture(capturedAt, capturedPiece));
    }
}
