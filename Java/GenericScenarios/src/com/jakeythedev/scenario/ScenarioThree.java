package com.jakeythedev.scenario;

public class ScenarioThree
{

	/*/
	 * Using Generic methods to allow different data types but under specific classes that they extend.
	 * 
	 * Example: Below..
	 */
	
	public static void main(String[] args)
	{
		log("Hey"); //This should have a compile error because a String does not extend or implement the class number.
		log(123);
	}
	
	private static <T extends Number> void log(T input) 
	{	
		System.out.println(input);
	}

}
