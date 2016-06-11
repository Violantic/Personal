package com.jakeythedev.engine.utils;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.jakeythedev.engine.game.GameTypes;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class UtilWorld
{

    public static void copyFiles(File previousLocation, File toLocation)
    {

        if (previousLocation == null || toLocation == null)
        {
            toLocation.mkdir();
            UtilServer.log("Directory", "Created directory at: " + toLocation.getAbsolutePath());
            return;
        }

        try
        {
            FileUtils.copyDirectory(previousLocation, toLocation);
            UtilServer.log("Directory", "Copied file to new location!");
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
            UtilServer.log("Directory", "Could not copy files to new location.");
        }
    }

    public static void removeFile(File file)
    {
        try
        {
            FileUtils.deleteDirectory(file);
            UtilServer.log("Directory", "Removed: " + file.getAbsolutePath());
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
            UtilServer.log("Directory", "Could not copy files to new location.");
        }
    }

    public static void renameFile(File file, File rename)
    {
        file.renameTo(rename);
    }

    public static World generateWorld(String worldName)
    {
        World world = Bukkit.getWorld(worldName);

        if (world == null)
        {
            WorldCreator creator = new WorldCreator(worldName);
            creator.environment(World.Environment.NORMAL);
            creator.generateStructures(false);
            world = creator.createWorld();
            world.setAutoSave(false);
            world.setTime(0);
            return world;
        }
        return null;
    }

    public static void unloadWorld(String worldName, boolean save)
    {
        World world = Bukkit.getWorld(worldName);

        if (world == null)
            return;

        Bukkit.unloadWorld(world, save);
    }

	public static String createRandomMap(GameTypes type)
	{
		File[] main = new File(Bukkit.getWorldContainer().getAbsolutePath() + "/../maps/" + type.getGameName()).listFiles();

		if (main == null)
		{
			UtilServer.log("Directory", "Directory does not exist.");
			return null;
		}

		final File random = main[new Random().nextInt(main.length)];

		File to = new File(Bukkit.getWorldContainer().getAbsolutePath() + "/" + random.getName());
		if(!to.exists()) to.mkdir();
		UtilWorld.copyFiles(random, to);
		UtilWorld.generateWorld(random.getName());

		return random.getName();
	}
}
