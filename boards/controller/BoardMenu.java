package BoardTeamPractice.controller;

import BoardTeamPractice.DAO.BoardDAO;
import BoardTeamPractice.DAO.BoardDAOImpl;
import BoardTeamPractice.VO.Board;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BoardMenu {
    Scanner sc = new Scanner(System.in);
    private BoardDAO dao = BoardDAOImpl.getInstance(); //dao 객체 추가
//    public static int bno=1;
//    List<Board> boards = new ArrayList<>(); //Board게시판! boards게시물!(테이블)

    public void list(){
        System.out.println("[게시물 목록]");
        System.out.println("------------------------------------------");
        System.out.printf("%-10s %-15s %-20s %-40s\n","no","writer","date","title");
        System.out.println("------------------------------------------");

        readAll();
        mainMenu();
    }

    public void mainMenu(){
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("메인 메뉴: 1.Create | 2.Read | 3. Clear | 4.Exit");
        System.out.println("---------------------------------");
        System.out.print("메뉴 선택: ");
        String menuNo = sc.nextLine();
        System.out.println();

        switch (menuNo){
            case "1" -> create();
            case "2" -> read();
            case "3" -> clear();
            case "4" -> exit();
        }
    }

    public void create(){
        System.out.println("[새 게시물 입력]");
        Board board = new Board();
//        board.setBno(bno);
//        bno++;
        System.out.print("제목: ");
        board.setBtitle(sc.nextLine());
        System.out.print("내용: ");
        board.setBcontent(sc.nextLine());
        System.out.print("작성자: ");
        board.setBwriter(sc.nextLine());

        // ✅ 사용자가 날짜를 직접 입력하도록 요청
        System.out.print("날짜 (YYYY-MM-DD 형식, Enter 입력 시 오늘 날짜 자동 설정): ");
        String inputDate = sc.nextLine().trim();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (inputDate.isEmpty()) {
                board.setBdate(new Date()); // ✅ 오늘 날짜 설정
            } else {
                board.setBdate(formatter.parse(inputDate)); // ✅ 입력된 날짜를 Date로 변환
            }
        } catch (Exception e) {
            System.out.println("날짜 형식이 올바르지 않습니다. 기본값(오늘) 사용.");
            board.setBdate(new Date()); // 오류 시 기본값 설정
        }



        System.out.println("-----------------------------------");
        System.out.println("보조 메뉴: 1. Ok | 2. Cancel");
        System.out.print("메뉴 선택: ");
        String menuNo = sc.nextLine();
//        if(menuNo.equals("1")){
//            boards.add(board);
//        }
        if(menuNo.equals("1")){
            try {
                dao.insertBoard(board); // DB에 게시글 추가
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        list();
    }

    public void read() {
        System.out.println("[게시물 읽기]");
        System.out.println("bno: ");
        int bno = Integer.parseInt(sc.nextLine());

        try {
            Board board = dao.getBoard(bno);

            if (board != null) {
                System.out.println("##########");
                System.out.println("번호: " + board.getBno());
                System.out.println("제목: " + board.getBtitle());
                System.out.println("내용: " + board.getBcontent());
                System.out.println("작성자: " + board.getBwriter());
                System.out.println("날짜: " + board.getBdate());
                System.out.println("##########");


                System.out.println("---------------------------");
                System.out.println("보조 메뉴: 1. Update | 2.Delete | 3. List");
                System.out.print("메뉴 선택: ");
                String tmp = sc.nextLine();
                switch (tmp) {
                    case "1" -> update(bno);
                    case "2" -> delete(bno);
                    case "3" -> list();
                }
            } else {
                System.out.println("해당 게시물이 존재하지 않습니다.");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        list();
    }
    public void readAll(){
        try {
            List<Board> boards = dao.getAllBoardList();
            for(Board board : boards){
                System.out.printf("%-10s %-15s %-20s %-40s\n",board.getBno(),board.getBwriter(),board.getBdate(),board.getBtitle());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void clear(){
        System.out.println("[게시물 전체 삭제]");
        System.out.println("------------------");
        System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        String tmp = sc.nextLine();
        if(tmp.equals("1")){
            try {
                dao.clearBoard();
                System.out.println("모든 게시물이 삭제되었습니다");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        list();
    }

    public void exit(){
        System.out.println("** 게시판 종료 **");
        System.exit(0); //정상 종료 (1)이면 비정상종료

    }

    public String getCurrentDate() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = date.format(new Date());
        return currentDate;

    }

    public void update(int bno) {
        System.out.println("[수정 내용 입력]");

        try {
            Board board = dao.getBoard(bno);

            if (board != null) {
                System.out.println("제목: ");
                String changeTitle = sc.nextLine();
                System.out.println("내용: ");
                String changeContent = sc.nextLine();
                System.out.println("작성자: ");
                String changeWriter = sc.nextLine();

                // ✅ bno 값 설정 (필수!)
                board.setBno(bno);
                board.setBtitle(changeTitle);
                board.setBcontent(changeContent);
                board.setBwriter(changeWriter);

                System.out.println("보조 메뉴: 1.Ok | 2.Cancel");
                System.out.print("메뉴 선택: ");
                String tmp = sc.nextLine();
                if (tmp.equals("1")) {
                    dao.updateBoard(board); // DB에서 해당 게시물 업데이트
                } else{
                    System.out.println("해당 게시물이 존재하지 않습니다");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int bno){
        try {
            dao.deleteBoard(bno); //DB에서 게시물 삭제
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




}
