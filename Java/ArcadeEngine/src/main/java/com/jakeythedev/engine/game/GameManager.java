package com.jakeythedev.engine.game;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.jakeythedev.engine.Engine;
import com.jakeythedev.engine.game.arcade.ArcadeGame;
import com.jakeythedev.engine.game.arcade.IArcade;
import com.jakeythedev.engine.game.components.GameState;
import com.jakeythedev.engine.game.components.MapData;
import com.jakeythedev.engine.game.components.events.StateChangeEvent;
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
	private Set<Player> alivePlayers = new HashSet<>();
	private Set<Player> spectatorPlayers = new HashSet<>();
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
		String mapName = UtilWorld.createRandomMap(getSelectedGame().getType());
		/* Sets the map to the MapData class so you can get locations from the maps mapdata.yml */
		mapData = new MapData(mapName);
		/* Sets state to waiting */
		setState(GameState.WAITING);
		
		UtilServer.log("Game", getSelectedGame().getName());
		UtilServer.log("Game", getSelectedGame().getType().name());
		UtilServer.log("Game", getState().name());
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
					Bukkit.getOnlinePlayers().forEach(all -> addAlivePlayer(all));
					
					UtilServer.broadcast("&2Game", "&a" + getSelectedGame().getName() + " &fhas started!");
					getSelectedGame().initialise();
					setState(GameState.LOADED);
					this.cancel();
				}
				else if (time % 10 == 0 || time <= 5)
				{
					UtilServer.broadcast("&2Game", "&a" + getSelectedGame().getName() + " &fwill start in &a" + time + " &fseconds.");
				}
				else if (time == 15)
				{
					UtilServer.broadcast("&2&m----------------------------------------------");
					UtilServer.broadcast("&bGame&f: &b" + getSelectedGame().getName());

					StringBuilder description = new StringBuilder();
					
					for (String desc : getSelectedGame().getDescription())
						description.append(desc);
					
					UtilServer.broadcast("&bDescription&f: &b" + description.toString().trim());
					UtilServer.broadcast("");
					UtilServer.broadcast("&bMap&f: &b" + getMapData().getWorldName());
					UtilServer.broadcast("&2&m----------------------------------------------");
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

	public Set<Player> getAlivePlayers()
	{
		return alivePlayers;
	}

	/*/
	 * Add, remove and get spectators
	 */

	public void addSpectatorPlayer(Player player)
	{
		player.setGameMode(GameMode.SPECTATOR);
		spectatorPlayers.add(player);
	}
	public void removeSpectatorPlayer(Player player)
	{
		player.setGameMode(GameMode.SURVIVAL);
		spectatorPlayers.remove(player);
	}

	public Set<Player> getSpectatorPlayers()
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
