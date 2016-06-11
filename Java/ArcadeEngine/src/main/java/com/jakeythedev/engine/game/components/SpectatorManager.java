package com.jakeythedev.engine.game.components;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.utils.Color;
import com.jakeythedev.engine.utils.UtilSkullBuilder;

public class SpectatorManager implements Listener
{
	private Manager manager;

	public SpectatorManager(Manager manager)
	{
		this.manager = manager;
		Bukkit.getPluginManager().registerEvents(this, manager.getEngine());
	}

	public void build(Player player)
	{
		
		Inventory inventory = Bukkit.createInventory(null, 9*3, "Alive Players");
		
        for (Player all : Bukkit.getOnlinePlayers())
        {
        	if (manager.getGameManager().getAlivePlayers().contains(all))
        	{
        		inventory.addItem(new UtilSkullBuilder().setName(all.getName()).setOwner(all.getName()).setLore(Color.Gray + "Right click - Teleport").build());
        	}
        }
	}
	
	@EventHandler
	public void onSpectatorInteract(PlayerInteractEvent event)
	{
		
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if (event.getItem().getType() == Material.COMPASS && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("Spectator Compass"))
			{
				build(event.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onSpectatorUIInteract(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		
		if (event.getInventory().getTitle().equalsIgnoreCase("Alive Players"))
		{
			String username = event.getCurrentItem().getItemMeta().getDisplayName();
			
			if (Bukkit.getPlayer(username) == null)
				return;
			
			player.teleport(Bukkit.getPlayer(username));
		}
	}
}
