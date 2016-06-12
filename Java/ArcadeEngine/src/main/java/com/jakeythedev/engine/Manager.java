package com.jakeythedev.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.jakeythedev.engine.commands.GameCommand;
import com.jakeythedev.engine.commands.ListCommand;
import com.jakeythedev.engine.game.GameManager;
import com.jakeythedev.engine.game.arcade.ArcadeGame;
import com.jakeythedev.engine.game.games.PVPGame;
import com.jakeythedev.engine.game.games.SpleefGame;
import com.jakeythedev.engine.listeners.BlockListener;
import com.jakeythedev.engine.listeners.ChatListener;
import com.jakeythedev.engine.listeners.ConnectionListener;
import com.jakeythedev.engine.listeners.DeathListener;
import com.jakeythedev.engine.listeners.HungerListener;
import com.jakeythedev.engine.listeners.StateListener;
import com.jakeythedev.engine.utils.UtilWorld;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class Manager implements Listener
{
	
	private Engine engine;
	
	public Manager(Engine engine)
	{
		this.engine = engine;
	}
	
	private GameManager manager;
	public List<ArcadeGame> games;
	
	public void enable()
	{
		getEngine().getCommand("list").setExecutor(new ListCommand(this));
		getEngine().getCommand("game").setExecutor(new GameCommand(this));
		
		new UtilWorld(this);
		
		games = new ArrayList<>();
		
		manager = new GameManager(engine);

		registerListeners();
		registerGames();
		
		manager.initialiseGame(getRandomGame(games));

		
	}
	
	public void disable()
	{
		UtilWorld.unloadWorld(manager.getMapData().getWorldName(), false);

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				UtilWorld.removeFile(new File(Bukkit.getWorldContainer().getAbsolutePath() + "/" + manager.getMapData().getWorldName()));
			}
		}.runTaskLater(engine, 20);
	}
	
	private void registerListeners()
	{
		new ChatListener(this);
		new ConnectionListener(this);
		new DeathListener(this);
		new StateListener(this);
		new BlockListener(this);
		new HungerListener(this);
	}
	
	private void registerGames()
	{
		games.add(new SpleefGame(this));
		games.add(new PVPGame(this));
	}
	
	public ArcadeGame getRandomGame(List<ArcadeGame> games)
	{
		int random = new Random().nextInt(games.size());
		return games.get(random);
	}
	
	public GameManager getGameManager() { return manager; }
	
	public Engine getEngine() { return engine; }
}
