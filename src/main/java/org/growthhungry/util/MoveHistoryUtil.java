package org.growthhungry.util;

import org.growthhungry.model.MoveSnapshot;

import java.util.Stack;

public class MoveHistoryUtil {

    private static final Stack<MoveSnapshot> history = new Stack<>();

    public static void add(MoveSnapshot snapshot) {
        history.push(snapshot);
    }

    public static MoveSnapshot getLast() {
        return history.peek();
    }

    public static void deleteLast() {
        if(!history.isEmpty()) {
            history.pop();
        }
    }
}
