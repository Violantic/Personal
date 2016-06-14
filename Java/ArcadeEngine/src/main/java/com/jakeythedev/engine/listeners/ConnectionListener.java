package com.jakeythedev.engine.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.components.GameState;
import com.jakeythedev.engine.utils.Color;
import com.jakeythedev.engine.utils.UtilPlayer;
import com.jakeythedev.engine.utils.UtilServer;
import com.jakeythedev.engine.utils.scoreboard.types.GlobalScoreboard;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class ConnectionListener implements Listener
{
	
	private Manager manager;

	public ConnectionListener(Manager manager)
	{
		this.manager = manager;
		Bukkit.getPluginManager().registerEvents(this, manager.getEngine());
	}

	@EventHandler
	public void onConnect(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		UtilPlayer utilPlayer = new UtilPlayer(player);
		
		/* JOIN MESSAGE*/
		
		event.setJoinMessage(Color.translateColor("&f" + player.getName() + " &bjoined the game."));
		
		/*/
		 * UPDATES TAB TO CONTAIN MESSAGES
		 */
		
		utilPlayer.sendTab(Color.translateColor("&bArcade"), Color.translateColor("&aWelcome back&f: &7" + player.getName()));

		/*/
		 * CREATES A SCOREBOARD
		 */
		
		Bukkit.getOnlinePlayers().forEach(all -> new GlobalScoreboard().createScoreboard(all, manager.getGameManager()));

		switch (manager.getGameManager().getState())
		{
		case WAITING:
			
			player.teleport(Bukkit.getWorld("world").getSpawnLocation());
			player.setGameMode(GameMode.SURVIVAL);
			
			if (Bukkit.getOnlinePlayers().size() >= manager.getGameManager().getSelectedGame().getMinimumPlayers())
			{
				/* Adds all online players to alive players if the online count is over or = to the games min players. */
				Bukkit.getOnlinePlayers().forEach(all -> manager.getGameManager().addAlivePlayer(all));
				/* Begins countdown phase..  */
				manager.getGameManager().setState(GameState.COUNTDOWN);
			}
			else
			{
				/*/
				 * WAITING MESSAGE RUNNABLE (Runs every 60 seconds)
				 */
				
				new BukkitRunnable()
				{

					@Override
					public void run()
					{
						if (manager.getGameManager().getState() != GameState.WAITING)
						{
							this.cancel();
							return;
						}

						UtilServer.broadcast("Waiting", "&eWe're still waiting for players..");
					}
				}.runTaskTimer(manager.getEngine(), 0L, 60*20);
			}
			
			break;
			
		case COUNTDOWN:
			manager.getGameManager().addAlivePlayer(player);
			break;
			
		case LOADED:
			manager.getGameManager().addSpectatorPlayer(player);
			player.teleport(Bukkit.getWorld(manager.getGameManager().getMapData().getWorldName()).getSpawnLocation());

		case STARTED:
			manager.getGameManager().addSpectatorPlayer(player);
			player.teleport(Bukkit.getWorld(manager.getGameManager().getMapData().getWorldName()).getSpawnLocation());
			
		default:
			player.setHealth(20.0);
			break;
		}
	}

	@EventHandler
	public void onDisconnect(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		
		/* Updates users quiz message.. */
		event.setQuitMessage(Color.translateColor("&f" + player.getName() + " &bleft the game."));

		switch (manager.getGameManager().getState())
		{
		
		case WAITING:
			if (Bukkit.getOnlinePlayers().size() < manager.getGameManager().getSelectedGame().getMinimumPlayers()) 
				manager.getGameManager().setState(GameState.WAITING);
			
		case LOADED:
			if (Bukkit.getOnlinePlayers().size() < manager.getGameManager().getSelectedGame().getMinimumPlayers()) 
				manager.getGameManager().setState(GameState.WAITING);
			
		case STARTED:
			
			if (manager.getGameManager().getAlivePlayers().size() <= 1)
			{
				for (Player winner : manager.getGameManager().getAlivePlayers())
					UtilServer.broadcast("&fGame", Color.translateColor("&b" + winner.getName() + "&f won the game!"));
				
				manager.getGameManager().setState(GameState.ENDING);
				return;
			}
			
		default:
			manager.getGameManager().removeAlivePlayer(player);
			break;
		}
	}
}
