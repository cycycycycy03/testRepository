package com.kh.board.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.JDBCTemplate;
import com.kh.common.model.vo.PageInfo;


public class BoardDao {
	
	private Properties prop = new Properties();
	
	public BoardDao() {
		String filePath = BoardDao.class.getResource("/sql/board/board-mapper.xml").getPath();
		try {
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	public int selectListCount(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		int listCount = 0;
		String sql = prop.getProperty("selectListCount");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		
		return listCount;
	}

	public ArrayList<Board> selectList(Connection conn, PageInfo pi) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Board> list = new ArrayList<>();
		String sql = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			/*
			 * boardLimit(한 페이지에 보여져야할 게시글 개수)가 10일 때
			 * currentPage: 1 -> 시작: 1, 끝: 10
			 * currentPage: 2 -> 시작: 11, 끝: 20
			 * 
			 * 시작값: 1+boardLimit(currentPage-1)
			 * 끝값: (시작값 + boardLimit)-1
			 * 
			 * */
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = (startRow + pi.getBoardLimit()) - 1;
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Board(rset.getInt("BOARD_NO")
								 , rset.getString("CATEGORY_NAME")
								 , rset.getString("BOARD_TITLE")
								 , rset.getString("USER_ID")
								 , rset.getInt("COUNT")
								 , rset.getDate("CREATE_DATE")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}
	
	public ArrayList<Category> categoryList(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Category> list = new ArrayList<>();
		String sql = prop.getProperty("categoryList");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				list.add(new Category(rset.getInt("CATEGORY_NO")
									, rset.getString("CATEGORY_NAME")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stmt);
		}
		
		return list;
	}

	public int insertBoard(Connection conn, Board b) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, Integer.parseInt(b.getBoardWriter()));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int insertAttachment(Connection conn, Attachment at) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int increaseCount(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public Board selectBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board b = null;
		String sql = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = new Board(rset.getInt("BOARD_NO")
							, rset.getString("CATEGORY_NAME")
							, rset.getString("BOARD_TITLE")
							, rset.getString("BOARD_CONTENT")
							, rset.getString("USER_ID")
							, rset.getDate("CREATE_DATE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return b;
	}

	public Attachment selectAttachment(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Attachment at = null;
		String sql = prop.getProperty("selectAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				at = new Attachment();
				at.setRefBno(rset.getInt("REF_BNO"));
				at.setOriginName(rset.getString("ORIGIN_NAME"));
				at.setChangeName(rset.getString("CHANGE_NAME"));
				at.setFilePath(rset.getString("FILE_PATH"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return at;
	}

	public int updateBoard(Connection conn, Board b) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(b.getCategory()));
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, b.getBoardNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int updateAttachment(Connection conn, Attachment at) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			pstmt.setInt(4, at.getRefBno());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int newInsertAttachment(Connection conn, Attachment at) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("newInsertAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, at.getRefBno());
			pstmt.setString(2, at.getOriginName());
			pstmt.setString(3, at.getChangeName());
			pstmt.setString(4, at.getFilePath());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deleteBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

}
