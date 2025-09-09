package org.growthhungry.util;

import org.growthhungry.model.MoveSnapshot;

import java.util.Stack;

public class MoveHistoryUtil {

    private MoveHistoryUtil(){}
    private static final Stack<MoveSnapshot> history = new Stack<>();

    public static void add(MoveSnapshot snapshot) {
        history.push(snapshot);
    }

    public static MoveSnapshot getLast() {
        if(!history.isEmpty()) {
            return history.peek();
        }
        return null;
    }

    public static void deleteLast() {
        if(!history.isEmpty()) {
            history.pop();
        }
    }
}
