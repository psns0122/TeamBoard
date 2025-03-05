package jdbc.homework.boards.controller;

import jdbc.homework.boards.service.BoardServiceImpl;

import java.util.Scanner;

public class BoardController {

    private final BoardServiceImpl boardService = new BoardServiceImpl();
    private final Scanner sc = new Scanner(System.in);

    public void list() {
        while (true) {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
            System.out.println("---------------------------------");
            System.out.print("메뉴 선택: ");
            String menuNo = sc.nextLine();
            System.out.println();

            switch (menuNo) {
                case "1" -> create();
                case "2" -> read();
                case "3" -> clear();
                case "4" -> exit();
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    private void create() {
        System.out.println("[새 게시물 입력]");
        System.out.print("제목: ");
        String title = sc.nextLine();
        System.out.print("내용: ");
        String content = sc.nextLine();
        System.out.print("작성자: ");
        String writer = sc.nextLine();

        boardService.createBoard(title, content, writer);
        boardService.listBoards();
    }

    private void read() {
        System.out.println("[게시물 읽기]");
        System.out.print("bno: ");
        int bno = Integer.parseInt(sc.nextLine());

        boardService.readBoard(bno);
    }

    private void clear() {
        System.out.println("[게시물 전체 삭제]");
        System.out.println("------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        String menuNo = sc.nextLine();

        if (menuNo.equals("1")) {
            boardService.clearBoards();
        }
        boardService.listBoards();
    }

    private void exit() {
        System.out.println("** 게시판 종료 **");
        System.exit(0);
    }
}
