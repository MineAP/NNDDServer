package org.mineap.nndd.server.api.getdirlist;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mineap.nndd.server.dao.NNDDFileDao;
import org.mineap.nndd.server.dao.NNDDVideoDao;
import org.mineap.nndd.server.model.NNDDFile;
import org.mineap.nndd.server.model.NNDDVideo;

/**
 * Servlet implementation class GetDirList
 */
@WebServlet("/api/getdirlist")
public class GetDirList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String TYPE_XML = "XML";
	
	private static final String TYPE_HTML = "HTML";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDirList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String rootDirPath = request.getParameter("root");
		String type = request.getParameter("type");

		try
		{

			if (rootDirPath == null)
			{

				if (type == null || TYPE_XML.equals(type.toUpperCase()))
				{
					
					throw new UnsupportedOperationException();
					
				} else if (TYPE_HTML.equals(type.toUpperCase()))
				{
					writeHTML(request, response, out);
				}

			} else
			{

			}

		} finally
		{
			out.close();
		}
		
	}
	
	/**
	 * @param request
	 * @param response
	 * @param out
	 * @throws IOException
	 */
	public void writeHTML(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) throws IOException
	{
		NNDDFileDao dao = new NNDDFileDao();
		try
		{
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
			out.println("<html>");

			out.println("<title>ディレクトリ一覧</title>");
			out.println("<body>");
			
			List<NNDDFile> dirList = dao.getAllDir();
			
			for (NNDDFile file : dirList)
			{
				
				String str = "<a href=\"<URL>\"><DIR_NAME></a><br />";
				
				str = str.replace("<URL>", "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/NNDDServer/api/getvideolist?target=" + file.getId() + "&type=html");
				str = str.replace("<DIR_NAME>", file.getFile().getName());
				
				out.println(str + "\n");
				
			}
			
			if (dirList.isEmpty())
			{
				out.println("not found.");
			}
			out.println("</body>");
			
			out.println("</html>");
			
			
			
		} catch (SQLException e)
		{
			response.sendError(HttpsURLConnection.HTTP_INTERNAL_ERROR);
			e.printStackTrace();
		}
	}

}
