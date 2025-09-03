package org.growthhungry.validator.move;

import org.growthhungry.model.enums.PieceType;
import org.growthhungry.model.Coordinate;

public class RookMoveValidator extends MoveValidator {

    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        return from.getY() == to.getY() ||
                from.getX() == to.getX();
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

}
