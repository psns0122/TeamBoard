package jdbc.homework.boards.service;

/**
 * 게시판 서비스 인터페이스
 * 게시물 생성, 조회, 수정, 삭제 등의 기능을 제공
 */
public interface BoardService {
    /**
     * 새로운 게시물을 생성하는 메서드
     * @param title 게시물 제목
     * @param content 게시물 내용
     * @param writer 작성자
     */
    void createBoard(String title, String content, String writer);
    /**
     * 모든 게시물을 출력하는 메서드
     */
    void listBoards();
    /**
     * 특정 게시물을 조회하는 메서드
     * @param bno 게시물 번호
     */
    void readBoard(int bno);
    /**
     * 특정 게시물을 수정하는 메서드
     * @param bno 게시물 번호
     */
    void updateBoard(int bno);
    /**
     * 특정 게시물을 삭제하는 메서드
     * @param bno 게시물 번호
     */
    void deleteBoard(int bno);
    /**
     * 모든 게시물을 삭제하는 메서드
     */
    void clearBoards();

}
