/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mineap.nndd.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
	 *
	 * @return
	 */
	public Connection createConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;

		try
		{

			connection = DriverManager.getConnection("jdbc:sqlite:/Volumes/MK1646GSX/NNDD/system/library.db");

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
