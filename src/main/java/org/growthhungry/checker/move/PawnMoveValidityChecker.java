package org.growthhungry.checker.move;

import org.growthhungry.Color;
import org.growthhungry.model.Coordinate;

public class PawnMoveValidityChecker implements PieceMoveValidityChecker {

    private final boolean isWhite;

    public PawnMoveValidityChecker(Color color) {
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
                ((isWhite && from.getY() == 2) || (!isWhite && from.getY() == 7))) {
            return true;
        }

        if (Math.abs(dx) == 1 && dy == direction) {
            return true;
        }

        return false;
    }
}
