package com.jakeythedev.engine.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 14/05/2016
 */

public class Repository
{
	
	/* I added this class for the fuure incase I want to add stastics although not currently in use as of this build.. */

	private Connection connection;

	private String ip = "127.0.0.1", port = "3306", database = "database", username = "root", password = "root";

	public Repository()
	{
		openConnection(ip, port, database, username, password);
	}

	public Repository(String ip, String port, String database, String username, String password)
	{
		this.ip = ip;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;

		openConnection(ip, port, database, username, password);
	}

	public void createTable(String table, String columns) throws SQLException
	{

		final String CREATE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS " + table + "(" + columns + ");";
		
		PreparedStatement statement = null;

		try
		{
			statement = connection.prepareStatement(CREATE_IF_NOT_EXISTS);
			statement.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error creating table named " + table + "!");
			return;
		}
		finally
		{
			if (statement != null)
				statement.close();
		}
	}

	public void openConnection(String ip, String port, String db, String username, String password)
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, username, password);
			System.out.println("Repository connection established! Connected to " + ip + ":" + port + " with the username: " + username);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Error occured whilst trying to open connection!");
			return;
		}
	}

	public void closeConnection()
	{
		try
		{
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Could not close connection. See stack trace for info!");
		}
	}
	
	public void sendUpdate(String query)
	{

		PreparedStatement statement = null;
		
		try
		{
			statement = getConnection().prepareStatement(query);
			statement.executeUpdate();
			statement.close();
			
			System.out.println("Sent " + query + " to database!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Could not send update to database!");
		}
	}
	
	public ResultSet sendQuery(String query)
	{
		ResultSet set = null;
		
		try
		{
			PreparedStatement statment = getConnection().prepareStatement(query);
			set = statment.executeQuery();
			
			System.out.println("Sent " + query + " to database!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Could not send query to database!");
		}
		
		return set;
	}

	public Connection getConnection()
	{
		return connection;
	}
}
