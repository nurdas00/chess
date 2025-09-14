package org.growthhungry.model;

import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.PieceType;

public class Board {

    private final Piece[][] cells = new Piece[8][8];
    private Coordinate whiteKing;
    private Coordinate blackKing;

    private boolean whiteKingMoved;
    private boolean blackKingMoved;

    public Piece getPieceAt(int x, int y) {
        return cells[x][y];
    }

    public Piece getPieceAt(Coordinate coordinate) {
        return getPieceAt(coordinate.getX(), coordinate.getY());
    }

    public void setPiece(Coordinate c, Piece p) {
        cells[c.getX()][c.getY()] = p;
    }

    public MoveSnapshot makeMove(Coordinate from, Coordinate to) {
        Piece moved = getPieceAt(from);
        Piece captured = getPieceAt(to);
        setPiece(to, moved);
        setPiece(from, null);
        return new MoveSnapshot(from, to, moved, captured);
    }

    public void undoMove(MoveSnapshot s) {
        setPiece(s.getFrom(), s.getMoved());
        setPiece(s.getTo(), s.getCaptured());
    }

    public boolean isInside(Coordinate c) {
        int x = c.getX();
        int y = c.getY();
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public Coordinate getKing(Color color) {
        return color.equals(Color.WHITE) ? whiteKing : blackKing;
    }

    public void moveKing(Color color, Coordinate coordinate) {
        if (color.equals(Color.WHITE)) {
            whiteKing = coordinate;
        } else {
            blackKing = coordinate;
        }
    }

    public void removePiece(Coordinate coordinate) {
        cells[coordinate.getX()][coordinate.getY()] = null;
    }

    public void init() {
        clear();

        for (int x = 0; x < 8; x++) {
            Coordinate cW = new Coordinate(x, 1);
            Piece wP = preparePiece(PieceType.PAWN, Color.WHITE);
            wP.setCoordinate(cW);
            setPiece(cW, wP);

            Coordinate cB = new Coordinate(x, 6);
            Piece bP = preparePiece(PieceType.PAWN, Color.BLACK);
            bP.setCoordinate(cB);
            setPiece(cB, bP);
        }

        Piece wR1 = preparePiece(PieceType.ROOK, Color.WHITE);   wR1.setCoordinate(new Coordinate(0,0));
        setPiece(new Coordinate(0,0), wR1);
        Piece wR2 = preparePiece(PieceType.ROOK, Color.WHITE);   wR2.setCoordinate(new Coordinate(7,0));
        setPiece(new Coordinate(7,0), wR2);

        Piece wN1 = preparePiece(PieceType.KNIGHT, Color.WHITE); wN1.setCoordinate(new Coordinate(1,0));
        setPiece(new Coordinate(1,0), wN1);
        Piece wN2 = preparePiece(PieceType.KNIGHT, Color.WHITE); wN2.setCoordinate(new Coordinate(6,0));
        setPiece(new Coordinate(6,0), wN2);

        Piece wB1 = preparePiece(PieceType.BISHOP, Color.WHITE); wB1.setCoordinate(new Coordinate(2,0));
        setPiece(new Coordinate(2,0), wB1);
        Piece wB2 = preparePiece(PieceType.BISHOP, Color.WHITE); wB2.setCoordinate(new Coordinate(5,0));
        setPiece(new Coordinate(5,0), wB2);

        Piece wQ  = preparePiece(PieceType.QUEEN, Color.WHITE);  wQ.setCoordinate(new Coordinate(3,0));
        setPiece(new Coordinate(3,0), wQ);
        Piece wK  = preparePiece(PieceType.KING,  Color.WHITE);  wK.setCoordinate(new Coordinate(4,0));
        setPiece(new Coordinate(4,0), wK);
        moveKing(Color.WHITE, new Coordinate(4, 0));
        whiteKingMoved = false;

        Piece bR1 = preparePiece(PieceType.ROOK, Color.BLACK);   bR1.setCoordinate(new Coordinate(0,7));
        setPiece(new Coordinate(0,7), bR1);
        Piece bR2 = preparePiece(PieceType.ROOK, Color.BLACK);   bR2.setCoordinate(new Coordinate(7,7));
        setPiece(new Coordinate(7,7), bR2);

        Piece bN1 = preparePiece(PieceType.KNIGHT, Color.BLACK); bN1.setCoordinate(new Coordinate(1,7));
        setPiece(new Coordinate(1,7), bN1);
        Piece bN2 = preparePiece(PieceType.KNIGHT, Color.BLACK); bN2.setCoordinate(new Coordinate(6,7));
        setPiece(new Coordinate(6,7), bN2);

        Piece bB1 = preparePiece(PieceType.BISHOP, Color.BLACK); bB1.setCoordinate(new Coordinate(2,7));
        setPiece(new Coordinate(2,7), bB1);
        Piece bB2 = preparePiece(PieceType.BISHOP, Color.BLACK); bB2.setCoordinate(new Coordinate(5,7));
        setPiece(new Coordinate(5,7), bB2);

        Piece bQ  = preparePiece(PieceType.QUEEN, Color.BLACK);  bQ.setCoordinate(new Coordinate(3,7));
        setPiece(new Coordinate(3,7), bQ);
        Piece bK  = preparePiece(PieceType.KING,  Color.BLACK);  bK.setCoordinate(new Coordinate(4,7));
        setPiece(new Coordinate(4,7), bK);
        moveKing(Color.BLACK, new Coordinate(4, 7));
        blackKingMoved = false;
    }


    public void clear() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                cells[x][y] = null;
            }
        }
        whiteKing = null;
        blackKing = null;
    }

    private static Piece preparePiece(PieceType type, Color color) {
        Piece p = new Piece();
        p.setPieceType(type);
        p.setColor(color);
        return p;
    }

    public boolean isKingMoved(Color color) {
        return color == Color.WHITE ? whiteKingMoved : blackKingMoved;
    }

    public void setKingMoved(Color color, boolean moved) {
        if(color == Color.WHITE) {
            whiteKingMoved = moved;
        } else {
            blackKingMoved = moved;
        }
    }
}