package org.growthhungry.model;

import org.growthhungry.model.enums.Color;
import org.growthhungry.model.enums.PieceType;

public class Board {

    private final Piece[][] cells = new Piece[8][8];
    private Coordinate whiteKing;
    private Coordinate blackKing;

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
            setPiece(new Coordinate(x, 1), preparePiece(PieceType.PAWN, Color.WHITE));
            setPiece(new Coordinate(x, 6), preparePiece(PieceType.PAWN, Color.BLACK));
        }

        setPiece(new Coordinate(0, 0), preparePiece(PieceType.ROOK, Color.WHITE));
        setPiece(new Coordinate(7, 0), preparePiece(PieceType.ROOK, Color.WHITE));
        setPiece(new Coordinate(1, 0), preparePiece(PieceType.KNIGHT, Color.WHITE));
        setPiece(new Coordinate(6, 0), preparePiece(PieceType.KNIGHT, Color.WHITE));
        setPiece(new Coordinate(2, 0), preparePiece(PieceType.BISHOP, Color.WHITE));
        setPiece(new Coordinate(5, 0), preparePiece(PieceType.BISHOP, Color.WHITE));
        setPiece(new Coordinate(3, 0), preparePiece(PieceType.QUEEN, Color.WHITE));
        setPiece(new Coordinate(4, 0), preparePiece(PieceType.KING, Color.WHITE));
        moveKing(Color.WHITE, new Coordinate(4, 0));

        setPiece(new Coordinate(0, 7), preparePiece(PieceType.ROOK, Color.BLACK));
        setPiece(new Coordinate(7, 7), preparePiece(PieceType.ROOK, Color.BLACK));
        setPiece(new Coordinate(1, 7), preparePiece(PieceType.KNIGHT, Color.BLACK));
        setPiece(new Coordinate(6, 7), preparePiece(PieceType.KNIGHT, Color.BLACK));
        setPiece(new Coordinate(2, 7), preparePiece(PieceType.BISHOP, Color.BLACK));
        setPiece(new Coordinate(5, 7), preparePiece(PieceType.BISHOP, Color.BLACK));
        setPiece(new Coordinate(3, 7), preparePiece(PieceType.QUEEN, Color.BLACK));
        setPiece(new Coordinate(4, 7), preparePiece(PieceType.KING, Color.BLACK));
        moveKing(Color.BLACK, new Coordinate(4, 7));
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
}