package org.growthhungry.validator.move;

import org.growthhungry.model.enums.PieceType;
import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.Piece;

public class KnightMoveValidator extends MoveValidator {

    @Override
    public boolean check(Coordinate from, Coordinate to, Piece piece, Board board) {
        return isValidMove(from, to) && notFriendlyFire(board, to, piece.getColor());
    }
    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        return Math.abs(from.getX() - to.getX()) == 2 && Math.abs(from.getY() - to.getY()) == 1 ||
               Math.abs(from.getX() - to.getX()) == 1 && Math.abs(from.getY() - to.getY()) == 2;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

}
