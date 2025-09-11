package org.growthhungry.validator.factory;

import org.growthhungry.model.Board;
import org.growthhungry.validator.move.*;
import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.PieceType;

public class MoveValidatorFactory {

    public static MoveValidator getMoveValidator(Board board, PieceType pieceType, Color c) {
        return switch (pieceType) {
            case KING -> new KingMoveValidator(board, c);
            case BISHOP -> new BishopMoveValidator(board);
            case ROOK -> new RookMoveValidator(board);
            case QUEEN -> new QueenMoveValidator(board);
            case PAWN -> new PawnMoveValidator(c, board);
            case KNIGHT -> new KnightMoveValidator(board);
        };
    }
}
