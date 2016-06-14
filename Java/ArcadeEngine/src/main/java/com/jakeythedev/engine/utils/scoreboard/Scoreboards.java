package com.jakeythedev.engine.utils.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.jakeythedev.engine.utils.UtilServer;

public class Scoreboards
{
	
	private Player player;
	private Scoreboard scoreboard;
	private Objective objective;
	
	private Map<String, Integer> scores;
	private String title;

	public Scoreboards(Player player, String title)
	{
		this.player = player;
		this.title = title;
		
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		scores = new HashMap<>();
		
		objective = scoreboard.registerNewObjective((title.length() <= 16 ? title : "Scoreboard"), "dummy");
		objective.setDisplayName((title.length() <= 16 ? title : "TITLE TOO LONG!"));
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public void setTitle(String title)
	{
		if (objective == null)
			return;
		
		this.title = title;
	}

	public void addScore(String text, int slot)
	{
		if (scores.containsKey(text)) 
			return;
		
		scores.put(text, slot);
	}
	
	public void build()
	{
		
		if (objective == null)
			return;
		
		if (scoreboard != null)
			objective.setDisplayName((title.length() <= 16 ? title : "TITLE TOO LONG!"));
		
		for (Entry<String, Integer> text : scores.entrySet())
		{
			objective.getScore(text.getKey()).setScore(text.getValue());
		}
		
		player.setScoreboard(scoreboard);
		UtilServer.log("Scoreboard", "Scoreboard built!");
	}
	
	public String getTitle() { return title; }
}
