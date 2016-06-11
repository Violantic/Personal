package com.jakeythedev.engine.utils;

import org.bukkit.Bukkit;

import java.util.logging.Level;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class UtilServer
{

    public static void log(String log)
    {
        System.out.println(log);
    }

    public static void log(String level, String log)
    {
        System.out.println("[" + level + "]: " + log);
    }

    public static void log(Level level, String log)
    {
        System.out.println("[" + level + "]: " + log);
    }

    public static void broadcast(String prefix, String broadcast)
    {
        Bukkit.broadcastMessage(Color.Gray + "[" + Color.translateColor(prefix) + Color.Gray + "]: " + Color.translateColor(broadcast));
    }

    public static void broadcast(String broadcast)
    {
        Bukkit.broadcastMessage(Color.Gray + broadcast);
    }
}
