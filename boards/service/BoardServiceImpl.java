package BoardTeamPractice.Service;

import BoardTeamPractice.DAO.BoardDAO;
import BoardTeamPractice.DAO.BoardDAOImpl;
import BoardTeamPractice.VO.Board;

import java.sql.SQLException;
import java.util.List;

public class BoardServiceImpl implements BoardService {
    private BoardDAO dao;

    private static BoardServiceImpl service;

    private BoardServiceImpl(){
        dao = BoardDAOImpl.getInstance();
    }

    public static BoardServiceImpl getInstance(){
        if(service == null)service = new BoardServiceImpl();
        return service;
    }

    @Override
    public int insertBoard(Board board) throws SQLException {
        return dao.insertBoard(board);
    }

    @Override
    public int deleteBoard(int bno) throws SQLException {
        return dao.deleteBoard(bno);
    }

    @Override
    public int updateBoard(Board board) throws SQLException {
        return dao.updateBoard(board);
    }

    @Override
    public List<Board> getAllBoardList() throws SQLException {
        return dao.getAllBoardList();
    }

    @Override
    public Board getBoard(int bno) {
        return dao.getBoard(bno);
    }

    @Override
    public List<Board> getSearchBoardList(String btitle) {
        return dao.getSearchBoardList(btitle);
    }

    @Override
    public void clearBoard() {
        dao.clearBoard();
    }
}
