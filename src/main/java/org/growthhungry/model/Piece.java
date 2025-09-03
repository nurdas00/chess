package org.growthhungry.model;

import lombok.Getter;
import lombok.Setter;
import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.PieceType;

@Getter
@Setter
public class Piece {
    private PieceType pieceType;
    private Color color;
    private Coordinate coordinate;
}
