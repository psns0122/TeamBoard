package jdbc.homework.boards.dao;

import jdbc.homework.boards.vo.Board;

import java.util.List;

public interface BoardDao {
    void insertBoard(Board board);
    List<Board> getAllBoards();
    Board getBoardById(int bno);
    void deleteBoard(int bno);
    void updateBoard(int bno, String newTitle, String newContent, String newWriter);
    void clearAllBoards();
}
