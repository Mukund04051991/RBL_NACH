package com.ddmandate.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ddmandate.bean.UtilityDetails;
import com.ddmandate.dao.ACHSponsorDao;
import com.ddmandate.dao.MandateDashboardDao;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetMandateDashboardController
 */

public class GetMandateDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMandateDashboardController() {
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
		MandateDashboardDao dao = new MandateDashboardDao();

		String operation = request.getParameter("operation");
		System.out.println("operation:" + operation);

		if (operation.equals("getAll")) {
			List<UtilityDetails> clist = dao.getAllUtilityCodes();
			Gson json = new Gson();
			String utilityList = json.toJson(clist);
			response.setContentType("text/html");
			response.getWriter().write(utilityList);
		}
		if (operation.equals("getCodes")) {
			String utility_name = request.getParameter("utility_name");
			System.out.println("utility_name:" + utility_name);
			List<UtilityDetails> slist = dao.getUtilityCodeByUtilityName(utility_name);
			Gson json = new Gson();
			String utilityCodeList = json.toJson(slist);
			response.setContentType("text/html");
			response.getWriter().write(utilityCodeList);
		}

		
	}

	

}
