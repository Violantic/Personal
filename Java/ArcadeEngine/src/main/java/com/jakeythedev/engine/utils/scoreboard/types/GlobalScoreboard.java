package com.jakeythedev.engine.utils.scoreboard.types;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.jakeythedev.engine.game.GameManager;
import com.jakeythedev.engine.game.arcade.ArcadeGame;
import com.jakeythedev.engine.game.components.GameState;
import com.jakeythedev.engine.utils.Color;
import com.jakeythedev.engine.utils.scoreboard.Scoreboards;

public class GlobalScoreboard
{

	/*/
	 * Method for creating the lobby & in game scoreboards.
	 */
	
	public void createScoreboard(Player player, GameManager manager)
	{
		Scoreboards scoreboard = new Scoreboards(player, Color.translateColor("&b&lLOBBY"));
		
		scoreboard.addScore("   ", 8);
		scoreboard.addScore(Color.translateColor("&f&lGamemode: "), 7); 
		scoreboard.addScore(Color.translateColor("&b» &7" + (manager.getSelectedGame().getName().equalsIgnoreCase("null") ? "None" : manager.getSelectedGame().getName())), 6); 
		scoreboard.addScore("  ", 5);
		scoreboard.addScore(Color.translateColor("&d&lPlayers: "), 4); 
		scoreboard.addScore(Color.translateColor("&b» &7" + Bukkit.getOnlinePlayers().size() + "&f/&7" + Bukkit.getServer().getMaxPlayers()), 3); 
		scoreboard.addScore(" ", 2);
		scoreboard.addScore(Color.translateColor("&a&lState: "), 1); 
		scoreboard.addScore(Color.translateColor("&b» &7" + (manager.getState().name().equalsIgnoreCase("null") ? "None" : manager.getState().name())), 0);

		scoreboard.build();
	}
}
