package com.ddmandate.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.ddmandate.dao.CancelBulkMandateDao;
import com.ddmandate.service.BulkCancelMandateService;


/**
 * Servlet implementation class CancelBulkUploadController
 */

public class CancelBulkUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelBulkUploadController() {
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

		System.out.println("calling CancelBulkUploadController DoPost method");

		ResourceBundle path = ResourceBundle.getBundle("DB");
		final String UPLOAD_DIRECTORY = path.getString("DD_TXN_DIRECTORY");
		
		
		CancelBulkMandateDao dao = new CancelBulkMandateDao();
		
		
		
		//final String UPLOAD_DIRECTORY = "C:\\uploads";
		PrintWriter out = response.getWriter();
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				String name = "", UMRN = "", utility_code = "", value = "", cancellation_code = "";
				for (FileItem item : multiparts) {
					System.out.println("1");
					System.out.println("item.isFormField():" + item.isFormField());
					if (!item.isFormField()) {
						File fileSaveDir = new File(UPLOAD_DIRECTORY);
						if (!fileSaveDir.exists()) {
							fileSaveDir.mkdir();
						}
						
						  System.out.println("2"); name = new File(item.getName()).getName();
						  item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
						  System.out.println("name:" + name + "fname:" + item.getName());
						 
					} /*
						 * else { System.out.println("3"); String otherFieldName = item.getFieldName();
						 * value = item.getString(); System.out.println("otherFieldName:" +
						 * otherFieldName); System.out.println("value:" + value);
						 * 
						 * if (otherFieldName.equals("utility_name")) { UMRN = value; } else if
						 * (otherFieldName.equals("utility_code")) { utility_code = value; } else if
						 * (otherFieldName.equals("email_id")) { cancellation_code = value; }
						 * System.out.println("utility_name:" + UMRN);
						 * System.out.println("utility_code:" + utility_code); }
						 */

				}
				/*
				 * FileUploadService service = new FileUploadService();
				 * System.out.println("name:" + name); boolean status =
				 * service.uploadFileData(UPLOAD_DIRECTORY + File.separator + name,
				 * UPLOAD_DIRECTORY, utility_code, name); System.out.println("status:" +
				 * status);
				 * 
				 * if (status) { boolean isExist = dao.checkFileExist(name); if (isExist) {
				 * out.print("{\"status\":5}"); } } else { out.print("{\"status\":4}"); }
				 */
				// check extension of file
				System.out.println("actual name of file:" + name);
				System.out.println("get extension:" + FilenameUtils.getExtension(name));
				if (!FilenameUtils.getExtension(name).equalsIgnoreCase("xlsx")
						&& !FilenameUtils.getExtension(name).equalsIgnoreCase("xls")) {
					out.print("{\"status\":7}");
				} else {
					// system will check file already uploaded

					boolean isExist = dao.checkFileExist(name);
					if (isExist) {
						// if exist show message file already uploaded
						out.print("{\"status\":5}");
					} else {
						// if file not exist then upload.check utility code validation

						BulkCancelMandateService service = new BulkCancelMandateService();
						System.out.println("name:" + name);
						int status = service.uploadFileData(UPLOAD_DIRECTORY + File.separator + name, UPLOAD_DIRECTORY,
								utility_code, name, UMRN, cancellation_code);
						System.out.println("status:" + status);
						if (status == 1) {

							// valid utility code
							out.print("{\"status\":3}");

						} else if (status == 3) {
							out.print("{\"status\":6}");
						} else if (status == 8) {
							out.print("{\"status\":8}");

						} else if (status == 11) {
							out.print("{\"status\":11}");
						} else if (status == 12) {
							out.print("{\"status\":12}");
						} else {
							// invalid utility code
							out.print("{\"status\":4}");
						}
					}
				}

			} catch (Exception e) {
				// exception handling
				e.printStackTrace(System.out);
			}

			// out.print("{\"status\":1}");
		}

	
	}

}
