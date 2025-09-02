package org.growthhungry.checker.move;

import org.growthhungry.model.Coordinate;

public interface PieceMoveValidityChecker {

    boolean isValidMove(Coordinate from, Coordinate to);
}
