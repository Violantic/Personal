package com.jakeythedev.engine.utils;

import com.jakeythedev.engine.utils.packets.ActionBarPacket;
import com.jakeythedev.engine.utils.packets.TabPacket;
import com.jakeythedev.engine.utils.packets.TitlePacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class UtilPlayer
{

    private Player player;

    public UtilPlayer(Player player)
    {
        this.player = player;
    }

    public void message(String message)
    {
        player.sendMessage(Color.translateColor(message));
    }

    public void message(String prefix, String message) { player.sendMessage(Color.Gray + "[" + Color.translateColor(prefix) + Color.Gray + "]: " + Color.translateColor(message)); }

    public void sendTitle(String title, String subtitle) { TitlePacket.sendTitle(player, Color.translateColor(title), Color.translateColor(subtitle));  }

    public void sendTab(String header, String subHeader) { TabPacket.sendTab(player, Color.translateColor(header), Color.translateColor(subHeader)); }
    
    public void sendActionbar(String arguments) { ActionBarPacket.sendActionbar(player, arguments); }
}
