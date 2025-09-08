package org.growthhungry.model.record;

import org.growthhungry.model.enums.MoveResultType;

public record MoveResult(MoveResultType resultType, String message) {
    public static MoveResult of(MoveResultType resultType, String message) {
        return new MoveResult(resultType, message);
    }
}
