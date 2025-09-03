package org.growthhungry.validator.move;

import org.growthhungry.model.enums.PieceType;
import org.growthhungry.model.Coordinate;

public class BishopMoveValidator extends MoveValidator {
    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        int dx = Math.abs(from.getX() - to.getX());
        int dy = Math.abs(from.getY() - to.getY());
        return dx == dy && dx != 0;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

}
