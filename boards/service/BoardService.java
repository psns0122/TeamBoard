package BoardTeamPractice.Service;

import BoardTeamPractice.VO.Board;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {

    // vo를 DB에 insert 하는 메서드, return 1: 작업 성공, 0: 작업 실패
    public int insertBoard(Board board) throws SQLException;


    // 게시글 번호를 인수값으로 받아 삭제하는 메서드
    public int deleteBoard(int bno) throws SQLException;

    //하나의 Board vo를 이용하여 DB에 update하는 메서드
    public int updateBoard(Board board) throws SQLException;

    // DB에서 전체 게시글 목록을 가져오는 메서드
    public List<Board> getAllBoardList() throws SQLException;

    // 게시글 번호를 인수값으로 받아 게시글 정보를 가져오는 메서드
    public Board getBoard(int bno);

    //게시글의 제목을 인수값으로 받아 게시글을 검색하는 메서드
    public List<Board> getSearchBoardList(String btitle);

    //게시물 전체 삭제
    public void clearBoard();



}
