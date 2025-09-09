package org.growthhungry.validator.move;

import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;

public class RookMoveValidator extends MoveValidator {

    public RookMoveValidator(Board board) {
        super(board);
    }

    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        return from.getY() == to.getY() ||
                from.getX() == to.getX();
    }

}
