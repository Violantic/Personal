package com.jakeythedev.engine.game.arcade;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.jakeythedev.engine.Manager;
import com.jakeythedev.engine.game.GameTypes;
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

    public ArcadeGame(Manager manager)
    {
        this.manager = manager;
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
    
    public String getName() { return this.getClass().getAnnotation(IArcade.class).gameName(); }
    public String[] getDescription() { return this.getClass().getAnnotation(IArcade.class).gameDescription(); }
    public GameTypes getType() { return this.getClass().getAnnotation(IArcade.class).gameTypes(); }
    public int getMinimumPlayers() { return this.getClass().getAnnotation(IArcade.class).minimumPlayers(); }
}
