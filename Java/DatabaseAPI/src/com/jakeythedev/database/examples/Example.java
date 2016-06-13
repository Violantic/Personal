package com.jakeythedev.database.examples;

import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import com.jakeythedev.database.Database;

public class Example extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		
		/*/
		 * CONNECTION TO THE DATABASE
		 */
		
		Database database = new Database("localhost", "3306", "mcc", "root", "root");

		/*/
		 * CREATING A TABLE WITH SPECIFIC COLUMNS  
		 */
		
		try
		{
			database.createTable("test", "name varchar(45), rank varchar(45)");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		/*/
		 * SEND A QUERY TO THE DATABASE
		 */
		
		database.sendQuery("SELECT * FROM Table WHERE name='debug'");
		
		/*/
		 * SEND AN UPDATE TO THE DATABASE
		 */
		
		database.sendUpdate("UPDATE Table SET rank='temp' WHERE name='debug'");
	}
}
