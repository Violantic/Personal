package com.jakeythedev.engine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.components.GameState;
import com.jakeythedev.engine.utils.UtilPlayer;
import com.jakeythedev.engine.utils.UtilServer;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class GameCommand implements CommandExecutor
{
	private Manager manager;
	
	public GameCommand(Manager manager)
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
			
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("stop"))
				{
					
					if (player.hasPermission("arcade.stop"))
					{
						UtilServer.broadcast("Game", "&f&l" + player.getName() + " &bstopped the game!");
						manager.getGameManager().setState(GameState.ENDING);
					}
				}
			}
			else
			{
				utilPlayer.message("&fHelp", "Game Help -");
				utilPlayer.message("&b- /game stop");	
			}
		}
		return false;
	}
}
