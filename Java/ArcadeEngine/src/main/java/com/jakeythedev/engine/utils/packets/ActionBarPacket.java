package com.jakeythedev.engine.utils.packets;

import java.lang.reflect.Constructor;

import com.jakeythedev.engine.utils.packets.reflection.Reflection;
import org.bukkit.entity.Player;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class ActionBarPacket
{
	public static void sendActionbar(Player player, String arguments)
	{

		try 
		{
			Constructor<?> constructor = Reflection.getNMSClass("PacketPlayOutChat").getConstructor(Reflection.getNMSClass("IChatBaseComponent"), byte.class);

			Object chat = Reflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + arguments + "\"}");

			Object packet = constructor.newInstance(chat, (byte) 2);

			Reflection.sendPackets(player, packet);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
