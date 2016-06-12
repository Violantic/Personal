package com.jakeythedev.scenario;

public class ScenarioOne
{

	/*/
	 * Using Generics to allow a methods input to allow different data types
	 * 
	 * Example: log("Hey"); log(123); etc
	 */
	
	public static void main(String[] args)
	{
		log("Hey");
		log(123);
	}
	
	private static <T> void log(T input) 
	{	
		System.out.println(input);
	}
}
