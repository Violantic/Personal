package com.jakeythedev.engine.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.jakeythedev.engine.Manager;

public class HungerListener implements Listener
{
	private Manager manager;

	public HungerListener(Manager manager)
	{
		this.manager = manager;
		Bukkit.getPluginManager().registerEvents(this, manager.getEngine());
	}
	
	@EventHandler
	public void onFoodDecrease(FoodLevelChangeEvent event)
	{
		event.setFoodLevel(20);
	}
}
