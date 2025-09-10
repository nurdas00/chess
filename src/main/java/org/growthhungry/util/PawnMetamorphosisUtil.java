package org.growthhungry.util;

import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.Piece;
import org.growthhungry.model.enums.PieceType;

import java.util.Scanner;

public class PawnMetamorphosisUtil {

    public static void changePawn(Board board, Coordinate coordinate) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose piece to turn");
        while(true) {
            String input = scanner.next();
            PieceType newPieceType;
            try {
                 newPieceType = PieceType.valueOf(input);
            } catch (Exception e) {
                System.out.println("Incorrect piece type");
                continue;
            }
            Piece piece = board.getPieceAt(coordinate);
            piece.setPieceType(newPieceType);
            board.setPiece(coordinate, piece);
            break;
        }
    }
}
