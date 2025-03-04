package BoardTeamPractice.DAO;

import BoardTeamPractice.VO.Board;

import java.sql.SQLException;
import java.util.List;

public interface BoardDAO {
    // vo에 담겨진 자료를 DB에 insert 하는 메서드
    public int insertBoard(Board board) throws SQLException;

    // 게시글 번호를 인수값으로 받아서 삭제하는 메소드
    public int deleteBoard(int bno) throws SQLException;

    //vo를 이용하여 DB에 update하는 메서드
    public int updateBoard(Board board) throws SQLException;

    // DB에서 전체 게시글 목록을 가져오는 메서드
    public List<Board> getAllBoardList() throws SQLException;

    //게시글 번호를 인수로 받아 해당 정보를 가져오는 메소드
    public Board getBoard(int bno);

    // 게시글의 제목값을 인수로 받아서 게시글을 검색하는 메서드
    public List<Board> getSearchBoardList(String btitle);

    //게시글 전체 삭제하는 메소드
    public void clearBoard();


}
