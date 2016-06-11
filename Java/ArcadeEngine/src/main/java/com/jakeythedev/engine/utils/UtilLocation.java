package com.jakeythedev.engine.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class UtilLocation
{

    public static Location stringToLocation(String location)
    {
        String[] locs = location.split(";");
        return new Location(Bukkit.getWorld(locs[0]), Double.parseDouble(locs[1]), Double.parseDouble(locs[2]), Double.parseDouble(locs[3]));
    }

    public static String locationToString(Location location)
    {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }
}
