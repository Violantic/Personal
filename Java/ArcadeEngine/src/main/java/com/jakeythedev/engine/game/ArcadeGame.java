package com.jakeythedev.engine.game;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.components.MapData;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public abstract class ArcadeGame implements Listener
{
    protected Manager manager;
    
    private GameTypes gameTypes;
    private String gameName;
    private String[] gameDescription;
    private int min;

    public ArcadeGame(Manager manager, String gameName, String[] gameDescription, GameTypes gameTypes, int min)
    {
        this.manager = manager;
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gameTypes = gameTypes;
        this.min = min;
    }

    public abstract void initialise();
    
    public void unregisterListeners() { HandlerList.unregisterAll(this); }

    public Manager getManager()
    {
        return manager;
    }
    
    public MapData getMapData()
    {
    	return getManager().getGameManager().getMapData();
    }

    public String getGameName()
    {
        return gameName;
    }

    public String[] getGameDescription()
    {
        return gameDescription;
    }

    public GameTypes getGameTypes()
    {
        return gameTypes;
    }
    
    public int getMinimumPlayers()
	{
		return min;
	}
}
