package org.growthhungry.validator.move;

import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.PieceType;
import org.growthhungry.model.Coordinate;

public class PawnMoveValidator extends MoveValidator {

    private final boolean isWhite;

    public PawnMoveValidator(Color color) {
        this.isWhite = color.equals(Color.WHITE);
    }

    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        int direction = isWhite ? 1 : -1;

        if (dx == 0 && dy == direction) {
            return true;
        }

        if (dx == 0 && dy == 2 * direction &&
                ((isWhite && from.getY() == 0) || (!isWhite && from.getY() == 6))) {
            return true;
        }

        if (Math.abs(dx) == 1 && dy == direction) {
            return true;
        }

        return false;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

}
