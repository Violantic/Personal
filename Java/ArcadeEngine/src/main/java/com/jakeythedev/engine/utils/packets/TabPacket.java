package com.jakeythedev.engine.utils.packets;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.jakeythedev.engine.utils.packets.reflection.Reflection;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class TabPacket
{

	public static void sendTab(Player player, String tabTop, String tabBottom)
	{
		try
		{
			if (tabTop == null)
				tabTop = "";
			tabTop = ChatColor.translateAlternateColorCodes('&', tabTop);

			if (tabBottom == null)
				tabBottom = "";
			tabBottom = ChatColor.translateAlternateColorCodes('&', tabBottom);

			tabTop = tabTop.replaceAll("<player>", player.getDisplayName());
			tabBottom = tabBottom.replaceAll("<player>", player.getDisplayName());

			Object title = Reflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + tabTop + "\"}");
			Object subTitle = Reflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + tabBottom + "\"}");

			Constructor<?> titleConstructor = Reflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(Reflection.getNMSClass("IChatBaseComponent"));

			Object packet = titleConstructor.newInstance(title);
			
			
			try
			{
				Field field = packet.getClass().getDeclaredField("b");
				field.setAccessible(true);
				field.set(packet, subTitle);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally 
			{
				Reflection.sendPackets(player, packet);
			}
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
	}
}