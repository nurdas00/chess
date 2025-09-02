package org.growthhungry.checker.move;

import org.growthhungry.model.Coordinate;

public class RookMoveValidityChecker implements PieceMoveValidityChecker {

    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        return from.getY() == to.getY() ||
                from.getX() == to.getX();
    }
}
