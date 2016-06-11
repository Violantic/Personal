package com.jakeythedev.engine;

import org.bukkit.plugin.java.JavaPlugin;

public class Engine extends JavaPlugin
{
	
	private Manager manager;
	
	@Override
	public void onEnable()
	{
		manager = new Manager(this);
		manager.enable();

	}
	
	@Override
	public void onDisable()
	{
		manager.disable();
	}
}