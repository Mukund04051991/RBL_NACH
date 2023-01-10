package com.ddmandate.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.ddmandate.dao.MandateDashboardDao;

/**
 * Servlet implementation class MandateDashboardController
 */

public class MandateDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MandateDashboardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		PrintWriter out = response.getWriter();

		JSONArray array = null;
		
		MandateDashboardDao dao = new MandateDashboardDao();
		System.out.println("In the MandateDashboardController");
		String utility_name = request.getParameter("utility_name");
		System.out.println("utility_name"+utility_name);
		String utility_code = request.getParameter("utility_code");
		System.out.println("utility_code"+utility_code);
		String To_date = request.getParameter("To_date");
		System.out.println("To_date"+To_date);
		
		array = dao.getMandateDetails(utility_name,utility_code,To_date);;

		System.out.println("array:" + array);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        out.print(array);
		
		
		
		
		/*
		 * RequestDispatcher rd = request.getRequestDispatcher("/NewFile2.jsp");
		 * rd.forward(request, response);
		 */
		
		
		
	}

}
