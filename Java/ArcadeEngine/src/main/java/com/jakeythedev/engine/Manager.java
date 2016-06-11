package com.jakeythedev.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.jakeythedev.engine.game.ArcadeGame;
import com.jakeythedev.engine.game.GameManager;
import com.jakeythedev.engine.game.components.SpectatorManager;
import com.jakeythedev.engine.game.games.DodgeBallGame;
import com.jakeythedev.engine.utils.UtilServer;
import com.jakeythedev.engine.utils.UtilWorld;

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
		games = new ArrayList<>();
		
		manager = new GameManager(engine);
		new SpectatorManager(this);
		
		registerGames();
		
		manager.initialiseGame(getRandomGame(games));
		UtilServer.log(manager.getState().name());
		
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
	
	public void registerGames()
	{
		games.add(new DodgeBallGame(this));
//		games.add(new TestGame(this));
	}
	
	public ArcadeGame getRandomGame(List<ArcadeGame> games)
	{
		int random = new Random().nextInt(games.size());
		return games.get(random);
	}
	
	public GameManager getGameManager()
	{
		return manager;
	}
	
	public Engine getEngine() { return engine; }
}
