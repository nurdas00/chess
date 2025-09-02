package org.growthhungry.checker.move;

import org.growthhungry.model.Coordinate;

public class KingMoveValidityChecker implements PieceMoveValidityChecker {
    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        return Math.abs(from.getX() - to.getX()) <= 1 && Math.abs(from.getY() - to.getY()) <= 1;
    }
}