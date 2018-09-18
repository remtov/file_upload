package servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_PATH ="C:\\jsp_study\\workspace\\File_upload\\WebContent\\upload";
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getRequestURI());
		boolean isMulti = ServletFileUpload.isMultipartContent(request);
		if (!isMulti) {
			throw new ServletException("폼태그 확인해라");
		}
		String path = System.getProperty("java.io.tmpdir");
		System.out.println("내 서버의 임시경로 : " + path);
		DiskFileItemFactory dfac = new DiskFileItemFactory();
		dfac.setRepository(new File(path));
		dfac.setSizeThreshold(1024 * 1024 * 5);

		ServletFileUpload sfu = new ServletFileUpload(dfac);
		try {
			List<FileItem> fList = sfu.parseRequest(request);
			Map<String,String> param = new HashMap<String,String>();
			for (FileItem fi : fList) {
				if (!fi.isFormField()) {
					param.put(fi.getFieldName(), fi.getName());
					File sFile = new File(UPLOAD_PATH + File.separator + fi.getName());
					fi.write(sFile);
				} else {
					param.put(fi.getFieldName(), fi.getString("utf-8"));
				}
			}

			String realPath = getServletContext().getRealPath("/upload");
			System.out.println("실제경로 : " + realPath);
			System.out.println(param);
		} catch (Exception e) {

		}
		System.out.println("니 폼태그의 멀티타입은 : " + isMulti);
		doGet(request, response);
	}

}
