package com.jakeythedev.engine.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.components.GameState;

public class BlockListener implements Listener
{

	private Manager manager;

	public BlockListener(Manager manager)
	{
		this.manager = manager;
		Bukkit.getPluginManager().registerEvents(this, manager.getEngine());
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event)
	{
		if (manager.getGameManager().getState() != GameState.STARTED)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event)
	{
		if (manager.getGameManager().getState() != GameState.STARTED)
			event.setCancelled(true);
	}
}
