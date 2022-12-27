package com.ach.sponsor.upload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class ImagesUploadServlet
 */
@WebServlet("/ImagesUploadServlet")
public class ImagesUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	//ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResource");
	//String UPLOAD_DIRECTORY = bundle.getString("MANDATE_IMAGES_PATH");
	
	private final String UPLOAD_DIRECTORY = "E:\\NACH_TEST_FILES\\IMAGES\\";
	
    public ImagesUploadServlet() {
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
		System.out.println("calling upload Servlet");
		System.out.println("Image Path:" + UPLOAD_DIRECTORY);
		HttpSession session = request.getSession();
		String Status =" ";
		
		if(ServletFileUpload.isMultipartContent(request)) {
			
			System.out.println("request:"+request);
			
			List<FileItem> multiparts = new ServletFileUpload(
					
				new DiskFileItemFactory()).parseRequest(request);
			
			
			for (FileItem item : multiparts) {
				
				
				if(!item.isFormField()) {
					
					
				}
			}
			
			
			)	
			
		}
		
		
		
		
		doGet(request, response);
	}

}
