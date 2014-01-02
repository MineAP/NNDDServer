package org.mineap.nndd.server.api.getvideolist;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mineap.nndd.server.dao.NNDDVideoDao;
import org.mineap.nndd.server.model.NNDDVideo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Servlet implementation class GetVideoList
 */
@WebServlet("/api/getvideolist")
public class GetVideoList extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static final String TARGET_ALL = "ALL";
	
	private static final String TYPE_XML = "XML";
	
	private static final String TYPE_HTML = "HTML";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetVideoList()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

	/**
	 * @param request
	 * @param response
	 */
	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try
		{
			String target = request.getParameter("target");

			String type = request.getParameter("type");
			
			if (target == null || TARGET_ALL.equals(target.toUpperCase()))
			{
				
				if (type == null || TYPE_XML.equals(type.toUpperCase()))
				{
				
					writeXML(request, response, out, -1);
					
				}
				else if (TYPE_HTML.equals(type.toUpperCase()))
				{
					
					writeHTML(request, response, out, -1);
					
				}
				
			} else
			{
				
				long id = Long.parseLong(target);
				
				if (type == null || TYPE_XML.equals(type.toUpperCase()))
				{
				
					writeXML(request, response, out, id);
					
				}
				else if (TYPE_HTML.equals(type.toUpperCase()))
				{
					
					writeHTML(request, response, out, id);
					
				}
				
			}

		} finally
		{
			out.close();
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @throws IOException 
	 */
	public void writeXML(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out, long rootId) throws IOException
	{
		NNDDVideoDao dao = new NNDDVideoDao();
		try
		{
			
			List<NNDDVideo> videoList = null;
			if (rootId == -1)
			{
				videoList = dao.getAllNNDDVideo();
			} else if (rootId > -1)
			{
				videoList = dao.getNNDDVideoByFileId(rootId);
			}
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Element root = document.createElement("channel");
			document.appendChild(root);
			
			Element title = document.createElement("title");
			Text textNode = document.createTextNode("NNDD Server Library");
			title.appendChild(textNode);
			root.appendChild(title);
			
			Element link = document.createElement("link");
			textNode = document.createTextNode(request.getRequestURI());
			link.appendChild(textNode);
			root.appendChild(link);
			
			Element description = document.createElement("description");
			textNode = document.createTextNode("NNDD Server Library Summary.");
			description.appendChild(textNode);
			root.appendChild(description);
			
			Element items = document.createElement("items");
			root.appendChild(items);
			
			
			CreateVideoElement createVideoElement = new CreateVideoElement();
			int count = 1;
			for (NNDDVideo nnddVideo : videoList)
			{
				
				Element item = createVideoElement.createElement(document, nnddVideo, count);
				
				items.appendChild(item);
				
				count++;
				
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer newTransformer = transformerFactory.newTransformer();
			newTransformer.transform(new DOMSource(root), new StreamResult(out));
			
		} catch (SQLException e)
		{
			response.sendError(HttpsURLConnection.HTTP_INTERNAL_ERROR);
			e.printStackTrace();
		} catch (ParserConfigurationException e)
		{
			response.sendError(HttpsURLConnection.HTTP_INTERNAL_ERROR);
			e.printStackTrace();
		} catch (TransformerException e)
		{
			response.sendError(HttpsURLConnection.HTTP_INTERNAL_ERROR);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @throws IOException 
	 */
	public void writeHTML(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out, long rootId) throws IOException
	{
		NNDDVideoDao dao = new NNDDVideoDao();
		try
		{
			out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
			out.println("<html>");

			out.println("<title>“®‰æˆê——</title>");
			out.println("<body>");

			List<NNDDVideo> videoList = null;
			if (rootId == -1)
			{
				videoList = dao.getAllNNDDVideo();
			} else if (rootId > -1)
			{
				videoList = dao.getNNDDVideoByFileId(rootId);
			}

			if (videoList == null || videoList.isEmpty())
			{
				out.println("not found.");
			} else
			{

				for (NNDDVideo nnddVideo : videoList)
				{

					String str = "<a href=\"<URL>\"><VIDEO_NAME></a><br />";

//					str = str.replace(
//							"<URL>",
//							"http://" + request.getLocalName() + ":"
//									+ request.getLocalPort()
//									+ "/NNDDServer/videostream/"
//									+ nnddVideo.getKey());
					
					str = str.replace(
							"<URL>",
							"http://" + request.getLocalName() + ":"
									+ request.getLocalPort()
									+ "/NNDDServer/WatchPage.jsp?videoid="
									+ nnddVideo.getKey());
					
					str = str.replace("<VIDEO_NAME>", nnddVideo.getVideoName());

					out.println(str + "\n");

				}

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
