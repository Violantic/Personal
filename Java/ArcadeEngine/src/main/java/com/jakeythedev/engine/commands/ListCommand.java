package com.jakeythedev.engine.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.utils.Color;
import com.jakeythedev.engine.utils.UtilPlayer;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class ListCommand implements CommandExecutor
{
	private Manager manager;
	
	public ListCommand(Manager manager)
	{
		this.manager = manager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			
			Player player = (Player) sender;
			UtilPlayer utilPlayer = new UtilPlayer(player);
			
			if (player.hasPermission("arcade.list"))
			{
				StringBuilder alive = new StringBuilder();
				
				utilPlayer.message("&fArcade", "&bList -");
				
				for (Player all : Bukkit.getOnlinePlayers())
				{
					if (manager.getGameManager().getAlivePlayers().contains(all))
						alive.append(Color.Aqua + all.getName()).append(Color.White + ", ");
				}
				
				if (alive.toString().contains(","))
					alive.deleteCharAt(alive.length() - 2);
				
				utilPlayer.message("Alive", alive.length() <= 1 ? Color.Yellow + "None" : alive.toString().trim() + Color.White + ".");
			}
		}
		return false;
	}
}
