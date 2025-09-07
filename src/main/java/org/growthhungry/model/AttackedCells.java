package org.growthhungry.model;

import lombok.Getter;
import lombok.Setter;
import org.growthhungry.model.enums.Color;

@Getter
@Setter
public class AttackedCells {
    private String[][] cells;

    public void markCell(int x, int y, String piece) {
        cells[x][y] = piece;
    }

    public String getCell(int x, int y) {
        return cells[x][y];
    }

    public boolean isUnderAttack(int x, int y, Color color) {
        String c = cells[x][y];
        boolean isWhite = color.equals(Color.WHITE);
        return c != null && ((isWhite && c.charAt(0) == 'b') || (!isWhite && c.charAt(0) == 'w'));
    }
}
