package org.growthhungry.checker.move;

import org.growthhungry.model.Coordinate;

public class BishopMoveValidityChecker implements PieceMoveValidityChecker {
    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());
        return dx == dy && dx != 0;
    }
}
