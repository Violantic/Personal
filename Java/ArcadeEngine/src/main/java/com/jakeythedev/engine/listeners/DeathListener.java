package com.jakeythedev.engine.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.components.GameState;
import com.jakeythedev.engine.utils.Color;
import com.jakeythedev.engine.utils.UtilPlayer;
import com.jakeythedev.engine.utils.UtilServer;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class DeathListener implements Listener
{
	
	private Manager manager;

	public DeathListener(Manager manager)
	{
		this.manager = manager;
		Bukkit.getPluginManager().registerEvents(this, manager.getEngine());
	}
	
	@EventHandler
	public void onVoidDeath(EntityDamageEvent event)
	{

		if (event.getCause() == DamageCause.VOID && event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();

			switch (manager.getGameManager().getState())
			{
			case STARTED:

				if (!manager.getGameManager().getSpectatorPlayers().contains(player))
				{
					manager.getGameManager().addSpectatorPlayer(player);
					player.teleport(Bukkit.getWorld(manager.getGameManager().getMapData().getWorldName()).getSpawnLocation());
				}
				
				if (manager.getGameManager().getAlivePlayers().contains(player))
					manager.getGameManager().removeAlivePlayer(player);
				
				if (manager.getGameManager().getAlivePlayers().size() <= 1)
				{
					for (Player winner : manager.getGameManager().getAlivePlayers())
						UtilServer.broadcast("&fGame", Color.translateColor("&b" + winner.getName() + "&f won the game!"));
					
					manager.getGameManager().setState(GameState.ENDING);
					return;
				}
				
				UtilServer.broadcast("&fGame", Color.translateColor(
						"&b" + player.getName() + " &fdied.. There are &b" +
								manager.getGameManager().getAlivePlayers().size() + "&f/&b" + Bukkit.getOnlinePlayers().size() + "&f alive players left.."));

			default:
				break;
			}
		}
		else
		{
			if (manager.getGameManager().getState() != GameState.STARTED)
				event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		UtilPlayer utilPlayer = new UtilPlayer(player);
		
		switch (manager.getGameManager().getState())
		{
		
		case WAITING:
			player.teleport(Bukkit.getWorld("world").getSpawnLocation());
			utilPlayer.message("Game", "You died before the game started! Sending you to spawn..");
		case COUNTDOWN:
			player.teleport(Bukkit.getWorld("world").getSpawnLocation());
			utilPlayer.message("Game", "You died before the game started! Sending you to spawn..");	
		case STARTED:
			player.teleport(Bukkit.getWorld(manager.getGameManager().getMapData().getWorldName()).getSpawnLocation());
			utilPlayer.message("Game", "You died! Please wait for the next game to start..");
		default:
			if (manager.getGameManager().getAlivePlayers().size() <= 1 && manager.getGameManager().getState() == GameState.STARTED)
			{
				UtilServer.broadcast("Game", "&f" + player.getName() + " &bwon the game!");
				manager.getGameManager().setState(GameState.ENDING);
				return;
			}
			break;
		}
	}
}
