package com.jakeythedev.engine.game.games;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.GameTypes;
import com.jakeythedev.engine.game.arcade.ArcadeGame;
import com.jakeythedev.engine.game.arcade.IArcade;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 12/06/2016
 */
@IArcade(gameName = "Spleef", gameDescription = { "Punch blocks to cause them to fall ", "last person wins!" }, gameTypes = GameTypes.SPLEEF, minimumPlayers = 3)
public class SpleefGame extends ArcadeGame
{

	public SpleefGame(Manager manager) { super(manager); }

	@Override
	public void initialise() { /* Normally use initialise for spawning different data points such as walls etc.. */ }

	@EventHandler
	public void onBreak(BlockDamageEvent event) 
	{ 
		Block block = event.getBlock();
		block.getLocation().getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
		event.getBlock().getDrops().clear();
		event.getBlock().setType(Material.AIR);	
	}
	
	@EventHandler
	public void onChange(EntityChangeBlockEvent event)
	{
		event.getBlock().setType(Material.AIR);
	}
}
