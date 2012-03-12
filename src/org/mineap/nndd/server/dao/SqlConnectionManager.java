/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mineap.nndd.server.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author shiraminekeisuke
 */
public class SqlConnectionManager
{

	private static SqlConnectionManager manager = new SqlConnectionManager();

	/**
	 *
	 * @return
	 */
	public static SqlConnectionManager getInstance()
	{
		return manager;
	}

	/**
	 * コンストラクタ。不可視。
	 */
	private SqlConnectionManager()
	{
	}
	
	/**
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public Connection createConnection() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException
	{
		File currentDir = new File(System.getProperty("user.dir"));
		
		File file = new File(currentDir.getAbsolutePath() + File.separator + "nnddserver.properties");
		
		Properties properties = new Properties();
		properties.load(new FileReader(file));
		
		String path = properties.getProperty("dbPath", "library.db");
		
		return createConnection(path);
	}

	/**
	 * @param dbPath
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection createConnection(String dbPath) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;

		try
		{

			connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

			Statement statement = connection.createStatement();

			statement.setQueryTimeout(30);

			return connection;

		} catch (SQLException e)
		{
			if (connection != null)
			{
				connection.close();
			}

			throw e;
		}
	}
}
