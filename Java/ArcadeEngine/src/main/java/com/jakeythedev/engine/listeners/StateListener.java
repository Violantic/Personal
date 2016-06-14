package com.jakeythedev.engine.listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.components.GameState;
import com.jakeythedev.engine.game.components.events.StateChangeEvent;
import com.jakeythedev.engine.utils.UtilServer;
import com.jakeythedev.engine.utils.UtilWorld;
import com.jakeythedev.engine.utils.scoreboard.types.GlobalScoreboard;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class StateListener implements Listener
{
	private Manager manager;

	public StateListener(Manager manager)
	{
		this.manager = manager;
		Bukkit.getPluginManager().registerEvents(this, manager.getEngine());
	}

	@EventHandler
	public void gameStateChangeEvent(StateChangeEvent event)
	{

		switch (event.getToState())
		{
		case WAITING:

			UtilServer.broadcast("&fGame", "A new game will start shortly.");
			
			if (Bukkit.getOnlinePlayers().size() >= manager.getGameManager().getSelectedGame().getMinimumPlayers() && manager.getGameManager().getState() == GameState.WAITING)
			{
				/* Begins countdown phase..  */
				manager.getGameManager().setState(GameState.COUNTDOWN);
			}
			
			break;
		case COUNTDOWN:
			Bukkit.getOnlinePlayers().stream().forEach(all -> new GlobalScoreboard().createScoreboard(all, manager.getGameManager()));
			manager.getGameManager().startCountdown();
			break;

		case LOADED:

			int i = 0;
			Location loc = null;

			for (Player all : Bukkit.getOnlinePlayers())
			{
				/*
				 * / Basically checks the mapdata.yml for
				 * 
				 * Spawns: BLACK:
				 * 
				 * then teleports each player to a different spawn point.
				 */

				manager.getGameManager().getMapData().getLocations("Spawns", "BLACK").forEach(
						location -> manager.getGameManager().getMapData().removeLocationBlocks(location, true));

				if (i >= manager.getGameManager().getMapData().getLocations("Spawns", "BLACK").size()) i = 0;
				loc = manager.getGameManager().getMapData().getLocations("Spawns", "BLACK").get(i++);
				all.teleport(loc);
				
			}

			/*
			 * Once the spawn point code finishes it then sets the game to
			 * STARTED
			 */
			
			Bukkit.getOnlinePlayers().stream().forEach(all -> new GlobalScoreboard().createScoreboard(all, manager.getGameManager()));
			manager.getGameManager().setState(GameState.STARTED);
			break;

		case STARTED:
			/* Tells all online users that the game is started.. */
			Bukkit.getOnlinePlayers().stream().forEach(all -> new GlobalScoreboard().createScoreboard(all, manager.getGameManager()));
			Bukkit.getPluginManager().registerEvents(manager.getGameManager().getSelectedGame(), manager.getEngine());
			UtilServer.log("Listeners", "Registerd " + manager.getGameManager().getSelectedGame().getName() + "'s Listeners!");
			break;

		case ENDING:
			Bukkit.getOnlinePlayers().stream().forEach(all -> new GlobalScoreboard().createScoreboard(all, manager.getGameManager()));
			manager.getGameManager().getSelectedGame().unregisterListeners();
			Bukkit.getOnlinePlayers().forEach(all -> all.setGameMode(GameMode.SPECTATOR));
			Bukkit.getOnlinePlayers().forEach(all -> all.getInventory().clear());
			Bukkit.getOnlinePlayers().forEach(all -> all.setHealth(20.0));

			for (Player all : Bukkit.getOnlinePlayers())
			{
				if (manager.getGameManager().getAlivePlayers().contains(all))
					manager.getGameManager().getAlivePlayers().remove(all);
			}

			UtilServer.broadcast("&2Game", "&fYou'll be returning to the lobby for a new game shortly.");
			
			new BukkitRunnable()
			{
				
				@Override
				public void run()
				{
					/* Returns all players to spawn*/
					Bukkit.getOnlinePlayers().forEach(all -> all.teleport(Bukkit.getWorld("world").getSpawnLocation()));
					Bukkit.getOnlinePlayers().forEach(all -> all.setGameMode(GameMode.SURVIVAL));
					/* Unregisters all that games listeners */
					manager.getGameManager().getSelectedGame().unregisterListeners();
					/* Unloads the world currently played in */
					UtilWorld.unloadWorld(manager.getGameManager().getMapData().getWorldName(), false);

					new BukkitRunnable()
					{
						@Override
						public void run()
						{
							UtilWorld.removeFile(new File(Bukkit.getWorldContainer().getAbsolutePath() + "/" + manager.getGameManager().getMapData().getWorldName()));

							while (new File(Bukkit.getWorldContainer().getAbsolutePath() + "/" + manager.getGameManager().getMapData().getWorldName()).exists())
								return;

							manager.getGameManager().initialiseGame(manager.getRandomGame(manager.games));
						}
					}.runTaskLater(manager.getEngine(), 5 * 20);
					
				}
			}.runTaskLater(manager.getEngine(), 5 * 20);
			break;

		default:
			break;
		}
	}
}
