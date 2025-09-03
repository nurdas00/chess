package org.growthhungry.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MoveSnapshot {
    private Coordinate from;
    private Coordinate to;
    private Piece moved;
    private Piece captured;
}
