package com.jakeythedev.engine.game;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public enum GameTypes
{

    SPLEEF("Spleef"),
    PVP("PVP");

    private String gameName;

    GameTypes(String gameName)
    {
        this.gameName = gameName;
    }

    public String getGameName()
    {
        return gameName;
    }
}
