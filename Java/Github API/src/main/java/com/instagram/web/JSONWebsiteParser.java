package com.instagram.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class JSONWebsiteParser
{
	
	/*/
	 * Method used to read from websites and return JSON so I can grab specific things such as 'followers'.
	 */

	public static String readWebsite(String websiteURL) throws Exception
	{
		BufferedReader bufferedReader = null;
		
		try
		{
			URL url = new URL(websiteURL);
			
			bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer stringBuffer = new StringBuffer();
			
			int read;
			char[] chars = new char[1024];
			
			while ((read = bufferedReader.read(chars)) != -1)
				stringBuffer.append(chars, 0, read);

			return stringBuffer.toString();
		}
		finally
		{
			if (bufferedReader != null) bufferedReader.close();
		}
	}
}
