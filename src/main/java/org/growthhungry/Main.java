package org.growthhungry;


import org.growthhungry.model.Board;
import org.growthhungry.model.Coordinate;
import org.growthhungry.model.Piece;
import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.MoveResultType;
import org.growthhungry.model.enums.PieceType;
import org.growthhungry.model.record.MoveResult;
import org.growthhungry.service.PieceMoveService;

import java.util.Scanner;

public class Main {
    private static final boolean USE_UNICODE = false;
    public static void main(String[] args) {

        Board board = new Board();
        board.init();
        PieceMoveService pieceMoveService = new PieceMoveService(board);
        printBoard(board);

        Scanner scanner = new Scanner(System.in);
        String move = scanner.nextLine();
        String[] coordinates = move.split(" ");
        if(coordinates.length > 2) {
            System.out.println("Invalid coordinates");
            return;
        }
        Coordinate from = getCoordinate(coordinates[0]);
        Coordinate to = getCoordinate(coordinates[1]);

        int k=0;
        while(true) {
            MoveResult result = pieceMoveService.move(from, to, getColor(k));
            System.out.println(result);
            if(result.resultType() == MoveResultType.OK || result.resultType() == MoveResultType.CHECK) {
                printBoard(board);
                k++;
            }
            if(result.resultType() == MoveResultType.FAIL) {
                System.out.println(result.message());
            }
            if(result.resultType() == MoveResultType.MATE || result.resultType() == MoveResultType.STALEMATE) {
                System.out.println(result.message());
                break;
            }
            move = scanner.nextLine();
            coordinates = move.split(" ");
            if(coordinates.length != 2) {
                System.out.println("Invalid coordinates");
                return;
            }
            from = getCoordinate(coordinates[0]);
            to = getCoordinate(coordinates[1]);
        }
    }

    private static Color getColor(int k) {
        return k % 2 == 0 ? Color.WHITE : Color.BLACK;
    }

    private static Coordinate getCoordinate(String c) {
        c = c.trim();
        if (c.length() != 2) {
            System.out.println("Invalid coordinate");
        }

        int x = c.charAt(0) - 'a';
        int y = c.charAt(1) - '1';

        return new Coordinate(x, y);
    }

    private static void printBoard(Board board) {
        final String files = "   a   b   c   d   e   f   g   h";
        System.out.println(files);
        System.out.println(" +---+---+---+---+---+---+---+---+");

        for (int y = 7; y >= 0; y--) {

            System.out.print((y + 1) + "|");


            for (int x = 0; x < 8; x++) {
                Piece p = board.getPieceAt(x, y);
                String s = symbol(p);
                System.out.print(" " + s + " |");
            }

            System.out.println();

            System.out.println(" +---+---+---+---+---+---+---+---+");
        }
        System.out.println(files);
    }

    private static String symbol(Piece p) {
        if (p == null) return " ";
        if (USE_UNICODE) {
            return unicodeFor(p.getPieceType(), p.getColor());
        } else {
            return asciiFor(p.getPieceType(), p.getColor());
        }
    }

    private static String asciiFor(PieceType t, Color c) {
        char ch = switch (t) {
            case KING -> 'K';
            case QUEEN -> 'Q';
            case ROOK -> 'R';
            case BISHOP -> 'B';
            case KNIGHT -> 'N';
            case PAWN -> 'P';
        };
        return (c == Color.WHITE) ? String.valueOf(ch) : String.valueOf(Character.toLowerCase(ch));
    }

    private static String unicodeFor(PieceType t, Color c) {
        // White: ♔♕♖♗♘♙  Black: ♚♛♜♝♞♟
        return switch (t) {
            case KING -> (c == Color.WHITE) ? "♔" : "♚";
            case QUEEN -> (c == Color.WHITE) ? "♕" : "♛";
            case ROOK -> (c == Color.WHITE) ? "♖" : "♜";
            case BISHOP -> (c == Color.WHITE) ? "♗" : "♝";
            case KNIGHT -> (c == Color.WHITE) ? "♘" : "♞";
            case PAWN -> (c == Color.WHITE) ? "♙" : "♟";
        };
    }
}