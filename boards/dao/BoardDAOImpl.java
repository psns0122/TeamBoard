package BoardTeamPractice.DAO;

import BoardTeamPractice.VO.Board;
import BoardTeamPractice.config.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAOImpl implements BoardDAO {

    private static BoardDAOImpl dao;

    private BoardDAOImpl() {
    }

    public static BoardDAOImpl getInstance() {
        if (dao == null) dao = new BoardDAOImpl();
        return dao;
    }

    private Connection conn;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;

    // 사용한 자원을 반납하는 메서드, 생성된 역순으로 닫아야 함
    private void disconnect() {
        if (rs != null) try {
            rs.close();
        } catch (SQLException e) {
        }
        if (stmt != null) try {
            stmt.close();
        } catch (SQLException e) {
        }
        if (pstmt != null) try {
            pstmt.close();
        } catch (SQLException e) {
        }
        if (conn != null) try {
            conn.close();
        } catch (SQLException e) {
        }

    }


    @Override
    public int insertBoard(Board board) {
        int cnt = 0;
        try {
            conn = DBUtil.getConnection();

            String sql = "insert into Board"
                    + "(btitle, bcontent, bwriter,bdate)"
                    + "values(?,?,?,?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, board.getBtitle());
            pstmt.setString(2, board.getBcontent());
            pstmt.setString(3, board.getBwriter());
            pstmt.setDate(4, new Date(board.getBdate().getTime()));
            cnt = pstmt.executeUpdate();
            // 자동 증가된 bno 값 가져오기
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                board.setBno(rs.getInt(1));
            }
        } catch (SQLException e) {
            cnt = 0;
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return cnt;
    }


    @Override
    public int deleteBoard(int bno) {
        int cnt = 0;

        try {
            conn = DBUtil.getConnection();

            String sql = "delete from Board where bno = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bno);

            cnt = pstmt.executeUpdate();
        } catch (SQLException e) {
            cnt = 0;
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return cnt;
    }

    @Override
    public int updateBoard(Board board) {
        int cnt = 0;

        try {
            conn = DBUtil.getConnection();

            String sql = "update Board set "
                    + "btitle = ?, "
                    + "bcontent = ?, "
                    + "bwriter =?, "
                    + "bdate = ?"
                    + " where bno = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getBtitle());
            pstmt.setString(2, board.getBcontent());
            pstmt.setString(3, board.getBwriter());
            pstmt.setDate(4, new Date(board.getBdate().getTime()));
            pstmt.setInt(5, board.getBno());

            cnt = pstmt.executeUpdate();
        } catch (SQLException e) {
            cnt = 0;
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return cnt;
    }

    @Override
    public List<Board> getAllBoardList() {
        List<Board> boardlist = new ArrayList<>(); // null이 아닌 빈 리스트 반환?
        if(boardlist ==  null || boardlist.isEmpty()){
            System.out.println("게시물이 없습니다.");
        }

        try {
            conn = DBUtil.getConnection();

            String sql = "select bno, btitle, bcontent, bwriter, bdate from Board"
                    + " order by bno";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            boardlist = new ArrayList<>();

            while (rs.next()) {
                Board board = new Board();
                board.setBno(rs.getInt("bno"));
                board.setBtitle(rs.getString("btitle"));
                board.setBcontent(rs.getString("bcontent"));
                board.setBwriter(rs.getString("bwriter"));
                board.setBdate(rs.getDate("bdate"));

                boardlist.add(board);
            }
        } catch (SQLException e) {
            boardlist = null;
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return boardlist;
    }

    @Override
    public Board getBoard(int bno) {
        Board board = null;

        try {
            conn = DBUtil.getConnection();

            String sql = "select bno, btitle, bcontent, bwriter, bdate from Board "
                    + " where bno = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bno);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                board = new Board();
                board.setBno(rs.getInt("bno"));
                board.setBtitle(rs.getString("btitle"));
                board.setBcontent(rs.getString("bcontent"));
                board.setBwriter(rs.getString("bwriter"));
                board.setBdate(rs.getDate("bdate"));
            }
        } catch (SQLException e) {
            board = null;
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return board;

    }

    @Override
    public List<Board> getSearchBoardList(String btitle) {
        List<Board> boardlist = null;

        try {
            conn = DBUtil.getConnection();

            String sql = "select bno, btitle, bcontent, bwriter, bdate from Board "
                    + "where btitle like '%' || ? || '%'"
                    + "order by bno";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, btitle); // btitle을 매개변수로 받기 때문에

            rs = pstmt.executeQuery();

            boardlist = new ArrayList<>();

            while (rs.next()) {
                Board board = new Board();
                board.setBno(rs.getInt("bno"));
                board.setBtitle(rs.getString("btitle"));
                board.setBcontent(rs.getString("bcontent"));
                board.setBdate(rs.getDate("bdate"));

                boardlist.add(board);
            }
        } catch (SQLException e) {
            boardlist = null;
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return boardlist;
    }

    @Override
    public void clearBoard() {
        try {
            conn = DBUtil.getConnection();

            String sql = "TRUNCATE TABLE Board"; // 전체 삭제하면서 번호도 초기화함
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
            if (stmt != null) try {stmt.close();} catch (SQLException e) {e.printStackTrace();}
            if (conn != null) try {conn.close();} catch (SQLException e) {e.printStackTrace();}

        }
    }
}
