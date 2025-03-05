package jdbc.homework.boards.dao;

import jdbc.homework.boards.confing.DBUtil;
import jdbc.homework.boards.vo.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BoardDao 인터페이스 구현체 - 데이터베이스에서 게시글을 관리합니다.
 */
public class BoardDaoImpl implements BoardDao{
    private static BoardDaoImpl dao;
    private BoardDaoImpl(){}
    /**
     * 싱글턴 인스턴스 반환 메서드
     * @return BoardDaoImpl 인스턴스
     */
    public static BoardDaoImpl getInstance(){
        if(dao == null) dao = new BoardDaoImpl();
        return dao;
    }
    @Override
    public void insertBoard(Board board) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();

            String sql = "INSERT INTO board (btitle, bcontent, bwriter, bdate) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, board.getBtitle());
            pstmt.setString(2, board.getBcontent());
            pstmt.setString(3, board.getBwriter());
            pstmt.setTimestamp(4, Timestamp.valueOf(board.getBdate())); // LocalDateTime -> Timestamp 변환
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public List<Board> getAllBoards() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<Board> boards = new ArrayList<>();
        String sql = "SELECT * FROM board";

        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Board board = new Board();
                board.setBno(rs.getInt("bno"));
                board.setBtitle(rs.getString("btitle"));
                board.setBcontent(rs.getString("bcontent"));
                board.setBwriter(rs.getString("bwriter"));
                board.setBdate(rs.getTimestamp("bdate").toLocalDateTime());
                boards.add(board);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return boards;
    }

    @Override
    public Board getBoardById(int bno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            String sql = "SELECT * FROM board WHERE bno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bno);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                Board board = new Board();
                board.setBno(rs.getInt("bno"));
                board.setBtitle(rs.getString("btitle"));
                board.setBcontent(rs.getString("bcontent"));
                board.setBwriter(rs.getString("bwriter"));
                board.setBdate(rs.getTimestamp("bdate").toLocalDateTime()); // Timestamp -> LocalDateTime 변환
                return board;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    @Override
    public void deleteBoard(int bno) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();

            String sql = "DELETE FROM board WHERE bno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bno);

            int cnt = pstmt.executeUpdate();

            if(cnt>0){
                System.out.println(bno + "인 회원 삭제 성공!!");
            }else{
                System.out.println(bno + "삭제에 실패했습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(pstmt!=null) try{ pstmt.close();   }catch(SQLException e){}
            if(conn!=null) try{ conn.close();   }catch(SQLException e){}
        }
    }

    @Override
    public void updateBoard(int bno, String newTitle, String newContent, String newWriter) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();

            String sql = "UPDATE board SET btitle = ?, bcontent = ?, bwriter = ? WHERE bno = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newTitle);
            pstmt.setString(2, newContent);
            pstmt.setString(3, newWriter);
            pstmt.setInt(4, bno);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("게시물이 성공적으로 수정되었습니다.");
            } else {
                System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllBoards() {
        Connection conn = null;
        Statement stmt = null;

        try {
            //예외가 발생할 수 있는
            conn = DBUtil.getConnection();

            String sql = "DELETE FROM board";
            stmt = conn.createStatement();

            int rows = stmt.executeUpdate(sql);

            if (rows > 0) {
                System.out.println("모든 게시물이 삭제되었습니다.");
            } else {
                System.out.println("삭제할 게시물이 없습니다.");
            }
        } catch (SQLException e) {
            // SQLException 예외를 처리
            e.printStackTrace(); // 예외의 스택 추적을 출력하여 디버깅에 도움을 줌
        } finally {
            // 자원 해제 코드
            if(stmt!=null) try{ stmt.close();   }catch(SQLException e){}
            if(conn!=null) try{ conn.close();   }catch(SQLException e){}

        }
    }

}
