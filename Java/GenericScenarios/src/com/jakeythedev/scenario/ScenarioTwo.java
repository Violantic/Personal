package com.jakeythedev.scenario;

import com.jakeythedev.scenario.examples.ScenarioTwoHash;

public class ScenarioTwo
{

	/*/
	 * Using Generics to make your own HashMap based class
	 * 
	 * Example: located in the 'examples' package
	 */
	
	public static void main(String[] args)
	{
		ScenarioTwoHash<String, Integer> test = new ScenarioTwoHash<>();
		test.put("Test", 5);
		test.put("Hello", 1);
		
		log(test.get("Test"));
		log(test.get("Hello"));
		
		test.clear();
	}
	
	private static <T> void log(T input) 
	{	
		System.out.println(input);
	}
}
