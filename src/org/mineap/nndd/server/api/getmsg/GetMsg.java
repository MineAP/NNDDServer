/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mineap.nndd.server.api.getmsg;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.mineap.nndd.server.cache.NnddVideoCacheManager;
import org.mineap.nndd.server.dao.NNDDVideoDao;
import org.mineap.nndd.server.model.NNDDVideo;
import org.mineap.nndd.server.videostream.NNDDVideoUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 *
 * @author shiraminekeisuke
 */
public class GetMsg extends HttpServlet {

	private NnddVideoCacheManager cacheManager = NnddVideoCacheManager.getInstance();


    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		
		try
		{
			Document xmlDocument = null;

			InputStream is = request.getInputStream();

			try
			{

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder newDocumentBuilder = factory.newDocumentBuilder();

				xmlDocument = newDocumentBuilder.parse(new BufferedInputStream(is));

			} finally
			{
				is.close();
			}

			if (xmlDocument == null)
			{
				response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR);
				return;
			}

			response.setContentType("text/html;charset=UTF-8");

			PrintWriter out = response.getWriter();
			BufferedReader reader = null;
			try
			{

				//var xml:String = "<thread fork=\"1\" user_id=\"" + user_id + "\" res_from=\"1000\" version=\"20061206\" thread=\"" + threadId + "\" />";

				Element element = xmlDocument.getDocumentElement();

				String id = element.getAttribute("thread");


				NNDDVideo video = cacheManager.getNNDDVideo(id);
				if (video == null)
				{
					NNDDVideoDao dao = new NNDDVideoDao();
					video = dao.getByVideoKey(id);
					cacheManager.setNNDDVideo(video);
				}

				File file = NNDDVideoUtil.getFile(video);

				String filePath = file.getPath();

				filePath = filePath.substring(0, filePath.length() - 4);

				filePath += ".xml";

				System.out.println(filePath);

				file = new File(filePath);

				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
				int len = 0;
				char[] buffer = new char[1024];
				while ((len = reader.read(buffer)) != -1)
				{
					out.write(buffer, 0, len);
				}

			} finally
			{

				if(out != null){
					out.close();
				}

				if(reader != null){
					reader.close();
				}

			}


		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR);
		}

    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
