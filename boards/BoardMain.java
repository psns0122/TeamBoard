package jdbc.homework.boards;

import jdbc.homework.boards.controller.BoardController;

public class BoardMain {
    public static void main(String[] args) {
        BoardController controller = new BoardController();
        controller.list();
    }
}
