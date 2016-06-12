package com.jakeythedev.engine;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
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