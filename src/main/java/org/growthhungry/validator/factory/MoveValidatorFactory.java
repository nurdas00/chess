package org.growthhungry.validator.factory;

import org.growthhungry.validator.move.*;
import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.PieceType;

public class MoveValidatorFactory {

    public static MoveValidator getMoveValidator(PieceType pieceType, Color c) {
        return switch (pieceType) {
            case KING -> new KingMoveValidator();
            case BISHOP -> new BishopMoveValidator();
            case ROOK -> new RookMoveValidator();
            case QUEEN -> new QueenMoveValidator();
            case PAWN -> new PawnMoveValidator(c);
            case KNIGHT -> new KnightMoveValidator();
        };
    }
}
