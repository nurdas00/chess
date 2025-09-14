package org.growthhungry;


import org.growthhungry.service.BoardService;

public class Main {
    public static void main(String[] args) {
        BoardService boardService = new BoardService();
        boardService.startGame();
    }
}