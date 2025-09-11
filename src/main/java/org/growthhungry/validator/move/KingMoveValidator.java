package org.growthhungry.validator.move;

import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.enums.Color;
import org.growthhungry.rule.CastleRule;

import static java.lang.Math.abs;

public class KingMoveValidator extends MoveValidator {
    private final Color color;

    public KingMoveValidator(Board board, Color color) {
        super(board);
        this.color = color;
    }

    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        if (abs(from.getX() - to.getX()) <= 1 && abs(from.getY() - to.getY()) <= 1) {
            return true;
        }

        return CastleRule.isAllowedToCastle(board, from, to, color);
    }

}