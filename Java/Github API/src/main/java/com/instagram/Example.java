package com.instagram;

import com.instagram.api.Users;

public class Example
{

	public static void main(String[] args)
	{
		/*/
		 * AUTH Keys can be located at: https://github.com/settings/tokens
		 */
		Auth auth = new Auth("Auth key found with the URL above").build();
		
		Users users = new Users(auth, "Username of who you want to lookup");
		
		System.out.println(users.getProfileURL());
		System.out.println(users.getFollowers());
		System.out.println(users.getAccountType());
		System.err.println(users.getPublicRepositorieCount());
		System.out.println(users.getBio());
		System.out.println(users.getCompany());
	
	}
}
