/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mineap.nndd.server.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import org.mineap.nndd.server.model.NNDDVideo;

/**
 * 
 * @author shiraminekeisuke(MineAP)
 */
public class NNDDVideoDao
{

	/**
	 * 指定されたidを持つ {@link NNDDVideo} を探します。
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public NNDDVideo getById(long id) throws SQLException
	{

		Connection connection = null;

		try
		{
			connection = SqlConnectionManager.getInstance().createConnection();

			Statement statement = connection.createStatement();

			statement.setQueryTimeout(30);

			ResultSet rs = statement
					.executeQuery("select * from nnddvideo where id = " + id);

			if (rs.next())
			{
				return createByResultSet(rs);
			} else
			{
				return null;
			}

		} catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		} finally
		{
			if (connection != null)
			{
				try
				{
					connection.close();
				} catch (SQLException e)
				{
				}
			}
		}
	}

	/**
	 * 指定されたビデオIDから {@link NNDDVideo} を探します。
	 * 
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public NNDDVideo getByVideoKey(String key) throws SQLException
	{

		Connection connection = null;

		try
		{
			connection = SqlConnectionManager.getInstance().createConnection();

			Statement statement = connection.createStatement();

			statement.setQueryTimeout(30);

			ResultSet rs = statement
					.executeQuery("select * from nnddvideo where key like '"
							+ key + "'");

			if (rs.next())
			{
				return createByResultSet(rs);
			} else
			{
				return null;
			}

		} catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		} finally
		{
			if (connection != null)
			{
				try
				{
					connection.close();
				} catch (SQLException e)
				{
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @param dirId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<NNDDVideo> getNNDDVideoByFileId(long dirId)
			throws SQLException
	{
		Connection connection = null;

		Statement statement = null;

		try
		{
			connection = SqlConnectionManager.getInstance().createConnection();

			statement = connection.createStatement();

			statement.setQueryTimeout(30);

			ResultSet rs = statement
					.executeQuery("select * from nnddvideo where dirPath_id = "
							+ dirId);

			ArrayList<NNDDVideo> videos = new ArrayList<NNDDVideo>();

			while (rs.next())
			{
				videos.add(createByResultSet(rs));
			}

			return videos;

		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally
		{
			if (connection != null)
			{
				try
				{
					connection.close();
				} catch (SQLException e)
				{
				}
			}
		}
	}

	/**
	 * 全ての {@link NNDDVideo} オブジェクトを探して返します。
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<NNDDVideo> getAllNNDDVideo() throws SQLException
	{

		Connection connection = null;

		Statement statement = null;

		try
		{
			connection = SqlConnectionManager.getInstance().createConnection();

			statement = connection.createStatement();

			statement.setQueryTimeout(30);

			ResultSet rs = statement.executeQuery("select * from nnddvideo");

			ArrayList<NNDDVideo> videos = new ArrayList<NNDDVideo>();

			while (rs.next())
			{
				videos.add(createByResultSet(rs));
			}

			return videos;

		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally
		{
			if (connection != null)
			{
				try
				{
					connection.close();
				} catch (SQLException e)
				{
				}
			}
		}
	}

	/**
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private NNDDVideo createByResultSet(ResultSet resultSet)
			throws SQLException
	{
		NNDDVideo nnddVideo = new NNDDVideo();

		long id = resultSet.getLong("id");
		String key = resultSet.getString("key");
		String uri = resultSet.getString("uri");
		String videoName = resultSet.getString("videoName");
		boolean isEconomy = resultSet.getBoolean("isEconomy");
		long modDate = resultSet.getLong("modificationDate");
		long createDate = resultSet.getLong("creationDate");
		String thumbUrl = resultSet.getString("thumbUrl");
		long playCount = resultSet.getLong("playCount");
		long time = resultSet.getLong("time");
		long lastPlayDate = (long) resultSet.getDouble("lastPlayDate");
		boolean yetReading = resultSet.getBoolean("yetReading");

		nnddVideo.setId(id);
		nnddVideo.setKey(key);
		nnddVideo.setUri(uri);
		nnddVideo.setVideoName(videoName);
		nnddVideo.setIsEconomy(isEconomy);
		nnddVideo.setModificationDate(new Date(modDate));
		nnddVideo.setCreationDate(new Date(createDate));
		nnddVideo.setThumbUrl(thumbUrl);
		nnddVideo.setPlayCount(playCount);
		nnddVideo.setTime(time);
		nnddVideo.setLastPlayDate(new Date(lastPlayDate));
		nnddVideo.setYetReading(yetReading);

		return nnddVideo;

	}
}
