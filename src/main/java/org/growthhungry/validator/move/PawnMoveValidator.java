package org.growthhungry.validator.move;

import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.Piece;
import org.growthhungry.model.enums.Color;
import org.growthhungry.rule.EnPassantRule;
import org.growthhungry.util.MoveHistoryUtil;

import static java.lang.Math.abs;

public class PawnMoveValidator extends MoveValidator {

    private final Color color;

    public PawnMoveValidator(Color color, Board board) {
        super(board);
        this.color = color;
    }

    @Override
    public boolean isValidMove(Coordinate from, Coordinate to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        int direction = dir();

        Piece destPiece = board.getPieceAt(to);

        if (dx == 0 && dy == direction && destPiece == null) {
            return true;
        }

        if (dx == 0 && dy == 2 * direction && destPiece == null && from.getY() == startY()) {
            Coordinate mid = new Coordinate(from.getX(), from.getY() + direction);
            if (board.getPieceAt(mid) == null) {
                return true;
            }
        }

        if (abs(dx) == 1 && dy == direction && destPiece != null && destPiece.getColor() != color) {
            return true;
        }

        return EnPassantRule.detect(board, from, to, board.getPieceAt(from), MoveHistoryUtil.getLast()).isPresent();
    }

    private int dir() { return (color == Color.WHITE) ? 1 : -1; }
    private int startY() { return (color == Color.WHITE) ? 1 : 6; }
}
