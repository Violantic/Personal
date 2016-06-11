package com.jakeythedev.engine.game.games;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.ArcadeGame;
import com.jakeythedev.engine.game.GameTypes;
import com.jakeythedev.engine.game.components.GameState;
import com.jakeythedev.engine.utils.UtilServer;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class DodgeBallGame extends ArcadeGame
{
	public DodgeBallGame(Manager manager)
	{
		super(manager, "Dodgeball", new String[] { "Game Test" }, GameTypes.DODGEBALL, 1);

	}

	@Override
	public void initialise()
	{

		for (int i = 0; i < getManager().getGameManager().getAlivePlayers().size(); i++)
			UtilServer.log(getManager().getGameManager().getAlivePlayers().get(i).getName());

		getMapData().getLocations("Spawns", "BLACK").forEach(location -> getMapData().removeLocationBlocks(location, true));
		Bukkit.getWorld(getMapData().getWorldName()).getEntities().forEach(entity -> entity.remove());

		for (Location location : getMapData().getLocations("Data", "LIME"))
		{
			location.getWorld().spawnEntity(location, EntityType.SLIME);
			getMapData().removeLocationBlocks(location, true);
		}
	}

	@EventHandler
	public void onDodgeballHit(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Slime)
		{
			Player player = (Player) event.getDamager();

			event.getEntity().setVelocity(player.getLocation().getDirection().normalize().multiply(4));

		}
		else if (event.getDamager() instanceof Slime && event.getEntity() instanceof Player)
		{

			Player player = (Player) event.getEntity();
			
			player.damage(20D);
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event)
	{
		
		Player player = event.getEntity();
		
		if (getManager().getGameManager().getAlivePlayers().size() <= 1)
		{
			UtilServer.broadcast("Game", "&b" + getManager().getGameManager().getAlivePlayers().get(0).getName() + " &fwon the game!");
			UtilServer.broadcast("Game", "&b" + "Server restarting in &f10 &bseconds..");
			
			Bukkit.getOnlinePlayers().forEach(all -> all.teleport(Bukkit.getWorld("world").getSpawnLocation()));
			
			new BukkitRunnable()
			{
				
				@Override
				public void run()
				{
					getManager().getGameManager().setState(GameState.ENDING);
					
				}
			}.runTaskLater(getManager().getEngine(), 10*20);
		}
		
		getManager().getGameManager().removeAlivePlayer(player);
	}
}
