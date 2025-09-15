package org.growthhungry.rule;


import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.enums.Color;

import static java.lang.Math.abs;
import static org.growthhungry.util.CastleUtil.*;

public class CastleRule {


    public static boolean isAllowedToCastle(Board board, Coordinate from, Coordinate to, Color color) {
        int kingY = getKingY(color);
        if(to.getY() != kingY) {
            return false;
        }
        boolean toRight = castleToRight(from, to);
        if (abs(from.getX() - to.getX()) != 2 || board.isKingMoved(color) ||
                !isRookOnPlace(board, from, to, color)) {
            return false;
        }
        int i = toRight ? 5 : 3;
        while (i > 0 && i < 7) {
            if(board.getPieceAt(i, kingY) != null) {
                return false;
            }
            if(toRight) {
                i++;
            } else {
                i--;
            }
        }
        return true;
    }
}
