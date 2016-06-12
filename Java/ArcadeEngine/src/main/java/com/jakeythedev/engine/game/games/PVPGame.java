package com.jakeythedev.engine.game.games;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.GameTypes;
import com.jakeythedev.engine.game.arcade.ArcadeGame;
import com.jakeythedev.engine.game.arcade.IArcade;
import com.jakeythedev.engine.utils.Color;
import com.jakeythedev.engine.utils.UtilItemBuilder;
import com.jakeythedev.engine.utils.UtilServer;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 12/06/2016
 */
@IArcade(gameName = "PVP", gameDescription = { "Fight to the death against players ", "last person alive wins!" }, gameTypes = GameTypes.PVP, minimumPlayers = 2)
public class PVPGame extends ArcadeGame
{

	public PVPGame(Manager manager) { super(manager); }

	@Override
	public void initialise() 
	{  
		
		Bukkit.getOnlinePlayers().forEach(all -> all.getInventory().addItem(new UtilItemBuilder(Material.IRON_SWORD)
				.setName(Color.translateColor("&8PVP &7Sword"))
				.setLore(Color.translateColor("&ePVP to the death you must win!"))
				.build()));
		
		UtilServer.log("Game", "IRON_SWORD added to all players..");
	}
}
