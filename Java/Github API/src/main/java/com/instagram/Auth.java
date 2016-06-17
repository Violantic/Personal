package com.instagram;

import org.json.JSONObject;

import com.instagram.web.JSONWebsiteParser;

public class Auth
{
	private String authKey;
	public Auth(String authKey)
	{
		this.authKey = authKey;
	}
	
	public Auth setKey(String authKey)
	{
		this.authKey = authKey;
		return this;
	}
	
	/*/
	 * This build method is mainly used to confirm the key is valid and that it works correctly.
	 */
	
	public Auth build()
	{
		
		try
		{
			new JSONObject(JSONWebsiteParser.readWebsite("https://api.github.com/?access_token=" + authKey));
			System.out.println("Auth key is valid and you are now connected to the Github API!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Auth key is invalid!");
		}
		
		return this;
	}
	
	public String getAuthKey() { return authKey; }
}
