package com.jakeythedev.engine.game.components.events;

import com.jakeythedev.engine.game.components.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class StateChangeEvent extends Event
{

    private GameState previousState;
    private GameState toState;

    public StateChangeEvent(GameState previousState, GameState toState)
    {
        this.previousState = previousState;
        this.toState = toState;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    public GameState getPreviousState()
    {
        return previousState;
    }

    public GameState getToState()
    {
        return toState;
    }
}
