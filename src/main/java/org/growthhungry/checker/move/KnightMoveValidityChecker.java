package org.growthhungry.checker.move;

import org.growthhungry.model.Coordinate;

public class KnightMoveValidityChecker implements PieceMoveValidityChecker {
    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        return Math.abs(from.getX() - to.getX()) == 2 && Math.abs(from.getY() - to.getY()) == 1 ||
               Math.abs(from.getX() - to.getX()) == 1 && Math.abs(from.getY() - to.getY()) == 2;
    }
}
