package com.jakeythedev.engine.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.jakeythedev.engine.Engine;
import com.jakeythedev.engine.game.components.GameState;
import com.jakeythedev.engine.game.components.MapData;
import com.jakeythedev.engine.game.components.events.StateChangeEvent;
import com.jakeythedev.engine.utils.Color;
import com.jakeythedev.engine.utils.UtilItemBuilder;
import com.jakeythedev.engine.utils.UtilServer;
import com.jakeythedev.engine.utils.UtilWorld;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class GameManager implements Listener
{

	private final Engine engine;

	public GameManager(Engine engine)
	{
		this.engine = engine;
		Bukkit.getPluginManager().registerEvents(this, engine);
	}

	/*/
	 * Objects for different things such as mapdata alive players array time and others.
	 */

	private ArcadeGame selectedGame;
	private MapData mapData;
	private List<Player> alivePlayers = new ArrayList<>();
	private List<Player> spectatorPlayers = new ArrayList<>();
	private GameState state;
	private int time = 30;

	/*/
	 * INITIALISE GAMES
	 */

	public void initialiseGame(ArcadeGame arcadeGame)
	{
		/* Sets current selected game to the initialised game.. */
		selectedGame = arcadeGame;
		/* Creates the map */
		String mapName = UtilWorld.createRandomMap(getSelectedGame().getGameTypes());
		/* Sets the map to the MapData class so you can get locations from the maps mapdata.yml */
		mapData = new MapData(mapName);
		/* Sets state to waiting */
		setState(GameState.WAITING);
		/* Initialises the selected game */
		getSelectedGame().initialise();
	}

	@EventHandler
	public void onConnect(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		switch (state)
		{
		case WAITING:

			player.teleport(Bukkit.getWorld("world").getSpawnLocation());

			if (Bukkit.getOnlinePlayers().size() >= getSelectedGame().getMinimumPlayers())
			{
				/* Adds all online players to alive players if the online count is over or = to the games min players. */
				Bukkit.getOnlinePlayers().forEach(all -> addAlivePlayer(all));
				/* Begins countdown phase..  */
				setState(GameState.COUNTDOWN);
			}
			else
			{
				new BukkitRunnable()
				{

					@Override
					public void run()
					{
						if (state != GameState.WAITING)
						{
							this.cancel();
							return;
						}

						UtilServer.broadcast("Waiting", "&eWe're still waiting for players..");
					}
				}.runTaskTimer(engine, 0L, 30*20);
			}
			break;

		case LOADED:
			addSpectatorPlayer(player);
			player.teleport(Bukkit.getWorld(mapData.getWorldName()).getSpawnLocation());

		case STARTED:
			addSpectatorPlayer(player);
			player.teleport(Bukkit.getWorld(mapData.getWorldName()).getSpawnLocation());
		default:
			break;
		}
	}

	@EventHandler
	public void onDisconnect(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();

		switch (state)
		{

		default:
			if (Bukkit.getOnlinePlayers().size() < getSelectedGame().getMinimumPlayers()) 
				setState(GameState.WAITING);

			removeAlivePlayer(player);
			break;
		}
	}

	@EventHandler
	public void gameStateChangeEvent(StateChangeEvent event)
	{

		switch (event.getToState())
		{

		case COUNTDOWN:
			startCountdown();
			break;

		case LOADED:

			int i = 0;
			Location loc = null;

			for (Player all : Bukkit.getOnlinePlayers())
			{
				/*/
				 * Basically checks the mapdata.yml for 
				 * 
				 * Spawns:
				 *   BLACK:
				 *   
				 *   then teleports each player to a different spawn point.
				 */

				if (i >= getMapData().getLocations("Spawns", "BLACK").size()) i = 0;
				loc = getMapData().getLocations("Spawns", "BLACK").get(i++);
				all.teleport(loc);
			}

			/* Once the spawn point code finishes it then sets the game to STARTED */

			setState(GameState.STARTED);
			break;

		case STARTED:
			/* Tells all online users that the game is started.. */
			UtilServer.broadcast("Game", "&b" + getSelectedGame().getGameName() + " has started..");
			Bukkit.getPluginManager().registerEvents(getSelectedGame(), engine);

			new BukkitRunnable()
			{

				@Override
				public void run()
				{
					setState(GameState.ENDING);

				}
			}.runTaskLater(engine, 10*20);
			break;

		case ENDING:
			/* Kicks all players for a server restart */
			Bukkit.getOnlinePlayers().forEach(all -> all.kickPlayer(Color.Blue + "Game Restarting"));
			/* Unregisters all that games listeners */
			getSelectedGame().unregisterListeners();
			/* Unloads the world currently played in */
			UtilWorld.unloadWorld(mapData.getWorldName(), false);

			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					UtilWorld.removeFile(new File(Bukkit.getWorldContainer().getAbsolutePath() + "/" + mapData.getWorldName()));

					while (new File(Bukkit.getWorldContainer().getAbsolutePath() + "/" + mapData.getWorldName()).exists())
						return;

					Bukkit.shutdown();
				}
			}.runTaskLater(engine, 20);
			break;

		default:
			break;
		}
	}

	public void startCountdown()
	{
		/* Sets timer to 30 */
		time = 30;

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				/* If timer equals 0 set gamestate to loaded and initialises the game.. */
				if (time == 0)
				{
					UtilServer.broadcast("&2Game", "&a" + selectedGame.getGameName() + " &fhas started!");
					selectedGame.initialise();
					setState(GameState.LOADED);
					this.cancel();
				}
				else if (time % 10 == 0 || time <= 5)
				{
					UtilServer.broadcast("&2Game", "&a" + selectedGame.getGameName() + " &fwill start in &a" + time + " &fseconds.");
				}

				time--;
			}
		}.runTaskTimer(engine, 0L, 20);
	}

	/*/
	 * Allows gamestates to be set and updates the custom gamestate event
	 */

	public void setState(GameState state)
	{
		StateChangeEvent event = new StateChangeEvent((this.state == null ? GameState.ENDING : this.state), state);
		this.state = state;
		Bukkit.getPluginManager().callEvent(event);
	}

	/*/
	 * Add, remove and get alive players
	 */

	public void addAlivePlayer(Player player)
	{
		alivePlayers.add(player);
	}
	public void removeAlivePlayer(Player player)
	{
		alivePlayers.remove(player);
	}

	public List<Player> getAlivePlayers()
	{
		return alivePlayers;
	}

	/*/
	 * Add, remove and get spectators
	 */

	public void addSpectatorPlayer(Player player)
	{
		player.getInventory().setItem(0, new UtilItemBuilder(Material.COMPASS).setName("Spectator Compass").build());
		player.setGameMode(GameMode.SPECTATOR);
		spectatorPlayers.add(player);
	}
	public void removeSpectatorPlayer(Player player)
	{
		player.getInventory().setItem(0, new ItemStack(Material.AIR));
		player.setGameMode(GameMode.SURVIVAL);
		spectatorPlayers.remove(player);
	}

	public List<Player> getSpectatorPlayers()
	{
		return spectatorPlayers;
	}

	/* Returns map data (mapdata.yml) */

	public MapData getMapData()
	{
		return mapData;
	}

	/* Returns current state of game */

	public GameState getState()
	{
		return state;
	}

	/* Returns current selected game.. */

	public ArcadeGame getSelectedGame()
	{
		return selectedGame;
	}
}
