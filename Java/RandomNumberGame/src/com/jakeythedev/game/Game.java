package com.jakeythedev.game;

import java.util.Random;

import javax.swing.JOptionPane;

public class Game
{


	private static boolean wonGame = false;
	private static int attempts = 5;

	public static void main(String[] args)
	{
		wonGame = false;
		beginGame(true);
	}

	public static void beginGame(boolean startIntro)
	{
		if (startIntro)
		{
			log("Welcome to the game. The concept of the game is basically to guess the number that randomly generates.");
			log("When the game first starts. The number is between 1 - 20.");
			log("You only get 5 attempts! Do not waste them.");
		}

		try
		{

			GameLevels[] gameLevels = GameLevels.values();

			
			String[] choices = new String[] { gameLevels[0].name(), gameLevels[1].name(), gameLevels[2].name()};

			GameLevels level = GameLevels.valueOf((String) JOptionPane.showInputDialog(null, "Choose a Level",
			        "Choose a Level using the drop down", JOptionPane.QUESTION_MESSAGE, null,  choices,  choices[0])); 
			
			log(level.name().toUpperCase() + " level selected!");

			int randomInt = createRandomInt(level);
			
			while (!wonGame)
			{

				String numberChosen = JOptionPane.showInputDialog("Choose a number: ");

				try
				{
					int numberChosenInt = Integer.parseInt(numberChosen);
					System.out.println(randomInt);

					if (numberChosenInt == randomInt)
					{
						log("Congratulations! You won the game and you had " + attempts + " attempt(s) left!");
						attempts = 5;
						wonGame = true;
						
						String playAgain = JOptionPane.showInputDialog("Would you like to play again? [Yes|No]: ");
						
						if (playAgain.equalsIgnoreCase("Yes"))
						{
							beginGame(false);
							wonGame = false;
						}

						return;
					}
					
					log("You have " + attempts + " attempts left!");

					if (attempts <= 0)
					{
						log("You lost the game.. BOO. The actual number was: " + randomInt);
						attempts = 5;
						beginGame(false);
						wonGame = false;
						return;
					}

					attempts--;
				}
				catch (Exception e)
				{
					log("You are required to use an integer..");
					wonGame = false;
					beginGame(false);
				}
			}
		}
		catch (Exception e)
		{
			log("That is not a level..");
			System.exit(1);
		}
	}

	public static int createRandomInt(GameLevels level)
	{

		Random random = new Random();

		if (level == GameLevels.BASIC)
		{
			return random.nextInt(20);
		}
		else if (level == GameLevels.MEDIUM)
		{
			return random.nextInt(40);
		}
		else if (level == GameLevels.HARD)
		{
			return random.nextInt(60);
		}

		return 0;
	}
	private static <T> void log(T value) 
	{
		JOptionPane.showMessageDialog(null, value);
	}
}
