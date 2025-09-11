package org.growthhungry.util;

import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.PieceType;

public class CastleUtil {
    public static boolean castleToRight(Coordinate from, Coordinate to) {
        return from.getX() < to.getX();
    }
    public static int getRookX(Coordinate from, Coordinate to) {
        return castleToRight(from, to) ? 7 : 0;
    }
    public static int getKingY(Color color) {
        return color == Color.WHITE ? 0 : 7;
    }

    public static boolean isRookOnPlace(Board board, Coordinate from, Coordinate to, Color color) {
        int rookX = getRookX(from, to);
        return board.getPieceAt(rookX, getKingY(color)).getPieceType() == PieceType.ROOK;
    }
}
