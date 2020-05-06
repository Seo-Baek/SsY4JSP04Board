package com.sist.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.model.BoardDAO;
import com.sist.model.BoardDTO;

@WebServlet("/cont.do")
public class ContServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ContServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	int board_no = Integer.parseInt(request.getParameter("no")); // 앞 전에 넘긴 변수명을 인자로 넣어준다.
    	
    	BoardDAO dao = new BoardDAO();
    	dao.boardHit(board_no);	//조회수 증가 메소드 호출
    	
    	BoardDTO cont = dao.getCont(board_no);
    	
    	request.setAttribute("Cont", cont);
    	RequestDispatcher rd = request.getRequestDispatcher("board_cont.jsp");
    	rd.forward(request, response);
    
    }
    
}