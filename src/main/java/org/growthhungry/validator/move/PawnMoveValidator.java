package org.growthhungry.validator.move;

import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.Piece;
import org.growthhungry.model.enums.Color;

public class PawnMoveValidator extends MoveValidator {

    private final boolean isWhite;

    public PawnMoveValidator(Color color, Board board) {
        super(board);
        this.isWhite = color.equals(Color.WHITE);
    }

    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        int direction = isWhite ? 1 : -1;

        Piece destPiece = board.getPieceAt(to);

        if (dx == 0 && dy == direction && destPiece == null) {
            return true;
        }

        if (dx == 0 && dy == 2 * direction && destPiece == null &&
                ((isWhite && from.getY() == 1) || (!isWhite && from.getY() == 6))) {
            return true;
        }

        return Math.abs(dx) == 1 && dy == direction && destPiece != null &&
                ((isWhite && destPiece.getColor() == Color.BLACK) || (!isWhite && destPiece.getColor() == Color.WHITE));
    }

}
