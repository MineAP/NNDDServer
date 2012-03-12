package org.mineap.nndd.server.dao;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mineap.nndd.server.model.NNDDFile;

/**
 * @author shiraminekeisuke
 *
 */
public class NNDDFileDao
{

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public NNDDFile getById(long id) throws SQLException
	{
		Connection connection = null;
		
		try
		{
			connection = SqlConnectionManager.getInstance().createConnection();
			
			Statement statement = connection.createStatement();

			statement.setQueryTimeout(30);
			
			ResultSet rs = statement.executeQuery("select * from file where id = " + id);
			
			if (rs.next())
			{
				return createByResultSet(rs);
			}
			else
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
	 * @return
	 * @throws SQLException
	 */
	public List<NNDDFile> getAllDir() throws SQLException
	{
		Connection connection = null;

		Statement statement = null;

		try
		{
			connection = SqlConnectionManager.getInstance().createConnection();

			statement = connection.createStatement();

			statement.setQueryTimeout(30);

			ResultSet rs = statement.executeQuery("select * from file");

			ArrayList<NNDDFile> dirList = new ArrayList<NNDDFile>();

			while (rs.next())
			{
				NNDDFile file = createByResultSet(rs);
				if (file != null)
				{
					dirList.add(file);
				}
			}

			return dirList;

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
	 * @param rs
	 * @return
	 */
	private NNDDFile createByResultSet(ResultSet rs) throws SQLException
	{
		long id = rs.getLong("id");
		String path = rs.getString("dirpath");
		
		try
		{
			return new NNDDFile(id, new File(new URI(path)));
		} catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
