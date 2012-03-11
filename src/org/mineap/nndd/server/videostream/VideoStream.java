/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mineap.nndd.server.videostream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mineap.nndd.server.cache.NnddVideoCacheManager;
import org.mineap.nndd.server.dao.NNDDVideoDao;
import org.mineap.nndd.server.model.NNDDVideo;

/**
 * 
 * @author shiraminekeisuke
 */
public class VideoStream extends HttpServlet
{

	private NnddVideoCacheManager cacheManager = NnddVideoCacheManager
			.getInstance();

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			
			String pathInfo = request.getPathInfo();

			if (pathInfo != null && pathInfo.length() > 0)
			{
				pathInfo = pathInfo.substring(1);
			}
			
			Logger.getLogger(VideoStream.class.getName()).log(Level.INFO,
					"request:" + pathInfo);

			NNDDVideo video = cacheManager.getNNDDVideo(pathInfo);
			if (video == null)
			{

				NNDDVideoDao dao = new NNDDVideoDao();
				try
				{
					video = dao.getByVideoKey(pathInfo);
				} catch (SQLException ex)
				{
					Logger.getLogger(VideoStream.class.getName()).log(
							Level.SEVERE, null, ex);
				}

			}

			if (video == null)
			{
				response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR,
						"video not found.");
				return;
			}

			File file = NNDDVideoUtil.getFile(video);
			if (!file.exists())
			{
				response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR,
						"file not found. " + file.toString());
				return;
			} else
			{
				Logger.getLogger(VideoStream.class.getName()).log(Level.INFO,
						"file:" + file.getName());
			}
			
			String extension = "flv";
			int index = file.getName().indexOf(".");
			if (index != -1 && file.getName().length() > index)
			{
				extension = file.getName().substring(index + 1);
			}

			extension = extension.toLowerCase();

			BufferedInputStream bis = null;
			BufferedOutputStream outputStream = null;
			try
			{

				bis = new BufferedInputStream(new FileInputStream(file));

				if (extension.indexOf("flv") != -1)
				{
					response.setContentType("application/x-shockwave-flash");
				} else if (extension.indexOf("mp4") != -1)
				{
					response.setContentType("video/mp4");
				} else if (extension.indexOf("swf") != -1)
				{
					response.setContentType("application/x-shockwave-flash");
				}

				response.setHeader("Connection", "keep-alive");
				response.setContentLength((int) file.length());
				response.setHeader("Content-Disposition", "filename=\"smile."
						+ extension + "\"");

				int len = 0;
				byte[] buffer = new byte[1024];
				outputStream = new BufferedOutputStream(
						response.getOutputStream());
				while ((len = bis.read(buffer)) != -1)
				{
					outputStream.write(buffer, 0, len);
				}

			} finally
			{
				if (bis != null)
				{
					bis.close();
				}
				if (outputStream != null)
				{
					outputStream.close();
				}
			}

		} catch (Throwable e)
		{
			Logger.getLogger(VideoStream.class.getName()).log(Level.SEVERE,
					null, e);
			response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR,
					e.getMessage());
		} finally
		{
			Logger.getLogger(VideoStream.class.getName()).log(Level.INFO,
					"completed.");
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo()
	{
		return "Short description";
	}// </editor-fold>

}
