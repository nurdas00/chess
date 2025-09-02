package org.growthhungry.checker.move;

import org.growthhungry.model.Coordinate;

public class QueenMoveValidityChecker implements PieceMoveValidityChecker {
    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());

        return (dx == 0 && dy != 0) ||
                (dy == 0 && dx != 0) ||
                (dx == dy && dx != 0);
    }
}
