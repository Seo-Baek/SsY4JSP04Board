package com.sist.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.model.BoardDAO;
import com.sist.model.BoardDTO;

/**
 * Servlet implementation class UpdateOkServlet
 */
@WebServlet("/updateOK.do")
public class UpdateOkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateOkServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//한글 인코딩 처리
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");
    	
    	// 1. 수정폼 창에서 넘어온 데이터를 처리해주자.
    	String board_writer = request.getParameter("writer").trim();
    	String board_title = request.getParameter("title").trim();
    	String board_cont = request.getParameter("content");
    	String board_pwd = request.getParameter("pwd").trim();
    	
    	// 수정폼 창에서 type="hidden"으로 넘어온 데이터도 반드시 처리해주어야 한다.
    	int board_no = Integer.parseInt(request.getParameter("no"));
    	
    	BoardDTO dto = new BoardDTO();
    	dto.setBoard_no(board_no);
    	dto.setBoard_writer(board_writer);
    	dto.setBoard_title(board_title);
    	dto.setBoard_cont(board_cont);
    	dto.setBoard_pwd(board_pwd);
    	
    	BoardDAO dao = new BoardDAO();
    	int res = dao.updateBoard(dto);
    	
    	PrintWriter out = response.getWriter();
    	
    	if(res > 0) {
    		out.println("<script>");
    		out.println("alert('게시물 수정 성공')");
    		out.println("location.href='cont.do?no="+ board_no + "'");
    		out.println("</script>");
    	} else if (res == -1) {
    		out.println("<script>");
    		out.println("alert('비밀번호가 틀렸습니다.')");
    		out.println("history.back()");
    		out.println("</script>");
    	} else {
    		out.println("<script>");
    		out.println("alert('게시물 수정 실패했습니다.')");
    		out.println("history.back()");
    		out.println("</script>");
    	}
    }

}
