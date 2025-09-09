package org.growthhungry.validator.move;

import lombok.RequiredArgsConstructor;
import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.Piece;
import org.growthhungry.model.enums.Color;

@RequiredArgsConstructor
public abstract class MoveValidator {

    protected final Board board;

    public boolean check(Coordinate from, Coordinate to, Piece piece) {
        return isValidMove(from, to) && isPathClear(from, to) && notFriendlyFire(to, piece.getColor());
    }
    abstract boolean isValidMove(Coordinate from, Coordinate to);
    public boolean isPathClear(Coordinate from, Coordinate to) {
        int stepX = Integer.compare(to.getX(), from.getX());
        int stepY = Integer.compare(to.getY(), from.getY());

        boolean straight = (stepX == 0) ^ (stepY == 0);
        boolean diagonal = stepX != 0 && stepY != 0 && Math.abs(to.getX()-from.getX()) == Math.abs(to.getY()-from.getY());
        if (!(straight || diagonal)) return true;

        int x = from.getX() + stepX;
        int y = from.getY() + stepY;
        while (x != to.getX() || y != to.getY()) {
            if (board.getPieceAt(x, y) != null) return false;
            x += stepX;
            y += stepY;
        }
        return true;
    }
    public boolean notFriendlyFire(Coordinate to, Color c) {
        Piece target = board.getPieceAt(to.getX(), to.getY());

        return target == null || !c.equals(target.getColor());
    }
}
