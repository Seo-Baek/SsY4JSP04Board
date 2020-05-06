package com.sist.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

	Connection con = null;				// DB연결 객체
	PreparedStatement pstmt = null;		// DB에 SQL문을 전송하는 객체
	ResultSet rs = null;				// SQL문을 실행 후 결과 값을 가진 객체
	String sql = null;
	
	public BoardDAO() { // 나중에는 static으로 상주시킨다(싱글턴). memory관점으로..
	// 기본 생성자
		String driver="oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "genie";
		String password = "1234";
		
		try {
			// 1. 드라이버 로딩
			Class.forName(driver);
			
			// 2. DB와 연결
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}// 생성자 end
	
	// board1 테이블의 전체 레코드를 조회하여 ArrayList 객체에 저장
	public List<BoardDTO> getList(){
		List<BoardDTO> board = new ArrayList<BoardDTO>();
		
		try {
			sql = "select * from board1 order by board_no desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) { // next() : 데이터가 존재하면 true값 반환
				// 테이블에서 하나의 레코드를 가져와서 각각의 컬럼을 DTO 객체에 저장.
				// rs는 커서타입이어서 rs.next()가 실행되면 커서가 다음 데이터로 이동한다.
				BoardDTO dto = new BoardDTO();	
				dto.setBoard_no(rs.getInt("board_no"));
				dto.setBoard_writer(rs.getString("board_writer"));
				dto.setBoard_title(rs.getString("board_title"));
				dto.setBoard_cont(rs.getString("board_cont"));
				dto.setBoard_pwd(rs.getString("board_pwd"));
				dto.setBoard_hit(rs.getInt("board_hit"));
				dto.setBoard_regdate(rs.getString("board_regdate"));
				
				board.add(dto);
			}
			rs.close(); pstmt.close(); con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return board;
	}	// getList() 메소드 end
	
	// board1 테이블의 글번호에 해당하는 조회수 증가시키는 메소드.
	public void boardHit(int no) {
		
		try {
			sql = "update board1 set board_hit = board_hit+1 where board_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
			
			pstmt.close(); // con.close();  우리가 해야할 중요한 일은 데이터를 가져오는 건데,
						   //               벌써 닫아버리면 상세내역을 가져올 수 없음.
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	//boardHit() 메소드 end
	
	// 글 번호에 해당하는 글을 조회하는 메소드
	public BoardDTO getCont(int no) {
		BoardDTO dto = new BoardDTO();
		
		try {
			sql = "select * from board1 where board_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto.setBoard_no(rs.getInt("board_no"));
				dto.setBoard_writer(rs.getString("board_title"));
				dto.setBoard_title(rs.getString("board_title"));
				dto.setBoard_cont(rs.getString("board_cont"));
				dto.setBoard_pwd(rs.getString("board_pwd"));
				dto.setBoard_hit(rs.getInt("board_hit"));
				dto.setBoard_regdate(rs.getString("board_regdate"));
			}
			rs.close(); pstmt.close(); con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	} // getCont() 메소드 end
	
	public int insertBoard(BoardDTO dto) {
		int result = 0;
		
		try {
			sql = "insert into board1 "
					+ "values(board1_seq.nextval, ?,?,?,?, default, sysdate)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, dto.getBoard_writer());
			pstmt.setString(2, dto.getBoard_title());
			pstmt.setString(3, dto.getBoard_cont());
			pstmt.setString(4, dto.getBoard_pwd());
			
			result = pstmt.executeUpdate();
			pstmt.close(); con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}	// insertBoard() 메소드 end
	
	// board1 테이블의 게시글을 수정하는 메소드
	public int updateBoard(BoardDTO dto) {
		int result = 0;
		
		try {
			sql = "select* from board1 where board_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBoard_no());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(dto.getBoard_pwd().equals(rs.getString("board_pwd"))) {
					sql = "update board1 set board_title=?, "+
							"board_cont=? where board_no=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, dto.getBoard_title());
					pstmt.setString(2, dto.getBoard_cont());
					pstmt.setInt(3, dto.getBoard_no());
					result = pstmt.executeUpdate();
				} else { // 비밀번호가 틀린 경우
					result = -1;
				}	
			}
			rs.close(); pstmt.close(); con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}	// updateBoard() 메소드 end
	
	// board1 테이블에서 게시글을 삭제하는 메소드
	public int deleteBoard(int no, String pwd) {
		int result = 0;
		
		try {
			sql = "select * from board1 where board_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(pwd.equals(rs.getString("board_pwd"))) {
					sql = "delete from board1 where board_no=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, no);
					result = pstmt.executeUpdate();
				} else {
					result = -1;
				}
			}
			rs.close(); pstmt.close(); con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}	// deleteBoard() 메소드 end
	
	// board1 테이블에서 게시물을 검색하는 메소드
	public List<BoardDTO> searchBoard(String find_field, String find_name){
		List<BoardDTO> search = new ArrayList<BoardDTO>();
		
		if(find_field.equals("title")) {	// 제목으로 검색한 경우
			try {
				sql = "select *  from board1 where board_title like ? order by board_no desc";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+find_name+"%");	// 꼭이렇게 해야 제대로 쿼리가 송출된다.
				rs = pstmt.executeQuery();
				while(rs.next()) {
					BoardDTO dto = new BoardDTO();	
					dto.setBoard_no(rs.getInt("board_no"));
					dto.setBoard_writer(rs.getString("board_writer"));
					dto.setBoard_title(rs.getString("board_title"));
					dto.setBoard_cont(rs.getString("board_cont"));
					dto.setBoard_pwd(rs.getString("board_pwd"));
					dto.setBoard_hit(rs.getInt("board_hit"));
					dto.setBoard_regdate(rs.getString("board_regdate"));
					
					search.add(dto);
				}
				rs.close(); pstmt.close(); con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if(find_field.equals("cont")) {			
			try {
				sql = "select *  from board1 where board_cont like ? order by board_no desc";	
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+find_name+"%");
				rs = pstmt.executeQuery();
				while(rs.next()) {
					BoardDTO dto = new BoardDTO();	
					dto.setBoard_no(rs.getInt("board_no"));
					dto.setBoard_writer(rs.getString("board_writer"));
					dto.setBoard_title(rs.getString("board_title"));
					dto.setBoard_cont(rs.getString("board_cont"));
					dto.setBoard_pwd(rs.getString("board_pwd"));
					dto.setBoard_hit(rs.getInt("board_hit"));
					dto.setBoard_regdate(rs.getString("board_regdate"));
					
					search.add(dto);
				}
				rs.close(); pstmt.close(); con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			try {
				sql = "select *  from board1 where board_title like ? or board_cont like ? order by board_no desc";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+find_name+"%");
				pstmt.setString(2, "%"+find_name+"%");
				rs = pstmt.executeQuery();
				while(rs.next()) {
					BoardDTO dto = new BoardDTO();	
					dto.setBoard_no(rs.getInt("board_no"));
					dto.setBoard_writer(rs.getString("board_writer"));
					dto.setBoard_title(rs.getString("board_title"));
					dto.setBoard_cont(rs.getString("board_cont"));
					dto.setBoard_pwd(rs.getString("board_pwd"));
					dto.setBoard_hit(rs.getInt("board_hit"));
					dto.setBoard_regdate(rs.getString("board_regdate"));
					
					search.add(dto);
				}
				rs.close(); pstmt.close(); con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return search;
	}	// searchBoard() 메소드 end
	
}
