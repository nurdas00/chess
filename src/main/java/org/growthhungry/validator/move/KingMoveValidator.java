package org.growthhungry.validator.move;

import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;

public class KingMoveValidator extends MoveValidator {
    public KingMoveValidator(Board board) {
        super(board);
    }

    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        return Math.abs(from.getX() - to.getX()) <= 1 && Math.abs(from.getY() - to.getY()) <= 1;
    }

}