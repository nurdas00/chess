package org.growthhungry.model.record;

public record MoveResult(boolean success, boolean gaveCheck, String reason) {
    public static MoveResult ok(boolean gaveCheck) {
        return new MoveResult(true, gaveCheck, null);
    }

    public static MoveResult fail(String reason) {
        return new MoveResult(false, false, reason);
    }
}
