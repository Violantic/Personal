package com.instagram.api;

import org.json.JSONObject;

import com.instagram.Auth;
import com.instagram.web.JSONWebsiteParser;

public class Users
{

	/* Object for the Auth class to grab the Auth key to bypass limits on the Github connections. */
	private Auth auth;
	
	/* Object to use each JSON Object we grab and a username String so we can get the username of the user they want to see.. */
	private String username;
	private JSONObject object;

	/* Where you enter your auth and username of the user you want to view. */
	public Users(Auth auth, String username)
	{
		this.auth = auth;
		this.username = username;
	}
	
	/* Returns their current company they have on Github. */
	
	public String getCompany()
	{
		
		try
		{
			object = new JSONObject(JSONWebsiteParser.readWebsite("https://api.github.com/users/" + username + "?access_token=" + auth.getAuthKey()));
			
			String company = object.get("company").toString();
			return company.equals("null") ? "None" : company;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Are you sure this is an account?");
		}
		
		return "Error";
	}
	
	/* Returns their current bio they have on Github. */
	
	public String getBio()
	{
		
		try
		{
			object = new JSONObject(JSONWebsiteParser.readWebsite("https://api.github.com/users/" + username + "?access_token=" + auth.getAuthKey()));
			
			String bio = object.get("bio").toString();
			return bio.equals("null") ? "None" : bio;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Are you sure this is an account?");
		}
		
		return "Error";
	}
	
	/* Returns their current public repo count they have on Github. */
	
	public int getPublicRepositorieCount()
	{
		
		try
		{
			object = new JSONObject(JSONWebsiteParser.readWebsite("https://api.github.com/users/" + username + "?access_token=" + auth.getAuthKey()));
			
			return Integer.parseInt(object.get("public_repos").toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Are you sure this is an account?");
		}
		
		return 0;
	}
	
	/* Returns their current user account type they have on Github. */
	
	public String getAccountType()
	{
		
		try
		{
			object = new JSONObject(JSONWebsiteParser.readWebsite("https://api.github.com/users/" + username + "?access_token=" + auth.getAuthKey()));

			String type = object.get("type").toString();
			return type.equals("null") ? "User" : type;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Are you sure this is an account?");
		}
		
		return "Error";
	}
	
	/* Returns their current follower count they have on Github. */
	
	public int getFollowers()
	{
		
		try
		{
			object = new JSONObject(JSONWebsiteParser.readWebsite("https://api.github.com/users/" + username + "?access_token=" + auth.getAuthKey()));
			return Integer.parseInt(object.get("followers").toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Are you sure this is an account?");
		}
		
		return 0;
	}
	
	/* Returns their current amount of people they follow on Github. */
	
	public int getFollowing()
	{
		
		try
		{
			object = new JSONObject(JSONWebsiteParser.readWebsite("https://api.github.com/users/" + username + "?access_token=" + auth.getAuthKey()));
			return Integer.parseInt(object.get("following").toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Are you sure this is an account?");
		}
		
		return 0;
	}
	
	/* Returns their current profile url they have on Github. */
	
	public String getProfileURL()
	{
		
		try
		{
			object = new JSONObject(JSONWebsiteParser.readWebsite("https://api.github.com/users/" + username + "?access_token=" + auth.getAuthKey()));
			return object.get("html_url").toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Are you sure this is an account?");
		}
		
		return "Error";
	}
	
	public String getUsername() { return username; }
}
