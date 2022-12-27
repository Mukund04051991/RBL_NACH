package com.ddmandate.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.ddmandate.dao.MandateFilesDao;


/**
 * Servlet implementation class SponsorMandateController
 */
public class SponsorMandateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SponsorMandateController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");

		System.out.println("action:" + action);

		MandateFilesDao fDao = new MandateFilesDao();

		JSONArray array = null;

		if (action.equals("getFileDetails")) {
			String upload_date = request.getParameter("fromdate");
			array = fDao.getMandateFileDetails(upload_date);

			System.out.println("array:" + array);

			response.setContentType("application/json");

			response.setCharacterEncoding("utf-8");

			out.print(array);

		} else if (action.equals("verify")) {
			System.out.println("in verify");
			String filename = request.getParameter("filename");
			System.out.println("filename:" + filename);
			if (!filename.equals("")) {
				boolean status = fDao.verifyMandateFile(filename);
				System.out.println("status:" + status);
				if (status) {
					out.print("Mandate Verified Successfully");
				} else {
					out.print("Mandate Verification Failed");
				}
			} else {
				out.print("No Data Found");
			}

		} else if (action.equals("reject")) {
			System.out.println("in reject");
			String filename = request.getParameter("filename");
			System.out.println("filename:" + filename);
			if (!filename.equals("")) {
				boolean status = fDao.rejectMandateFile(filename);
				System.out.println("status:" + status);
				if (status) {
					out.print("Mandate Rejected Successfully");
				} else {
					out.print("Mandate Rejection Failed");
				}
			} else {
				out.print("No Data Found");
			}
		}

	}

}
