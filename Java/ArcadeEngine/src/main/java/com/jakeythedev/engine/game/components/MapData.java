package com.jakeythedev.engine.game.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.jakeythedev.engine.utils.UtilLocation;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class MapData
{

    private String world;
    private File worldFile;
    private FileConfiguration worldFileConfiguration;

    public MapData(String world)
    {
        this.world = world;
        worldFile = new File(Bukkit.getWorldContainer().getAbsolutePath() + "/" + world + "/mapdata.yml");
        worldFileConfiguration = YamlConfiguration.loadConfiguration(worldFile);
    }

    public String getWorldName()
    {
        return world;
    }

    public List<Location> getLocations(String dataName, String color)
    {
    	
    	
        if(worldFileConfiguration == null)
            return null;

        if(!worldFileConfiguration.contains(dataName + "." + color))
            return null;

        List<Location> locations = new ArrayList<Location>();
        worldFileConfiguration.getStringList(dataName + "." + color).forEach(locs -> locations.add(UtilLocation.stringToLocation(locs)));

        return locations;
    }
    
    public void removeLocationBlocks(Location location, boolean plate)
    {
    	
		if(plate) location.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
		location.getBlock().setType(Material.AIR);
    }
}
