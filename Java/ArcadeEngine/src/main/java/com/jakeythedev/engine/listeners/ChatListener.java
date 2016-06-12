package com.jakeythedev.engine.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.utils.Color;

public class ChatListener implements Listener
{

	public ChatListener()
	{
		Bukkit.getPluginManager().registerEvents(this, manager.getEngine());
	}
	
	@EventHandler
	public void onTalk(AsyncPlayerChatEvent event)
	{

		event.setFormat(Color.Gray + event.getPlayer().getName() + ": " + Color.White + event.getMessage());
	}
}
