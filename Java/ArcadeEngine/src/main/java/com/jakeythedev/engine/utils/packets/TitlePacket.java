package com.jakeythedev.engine.utils.packets;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.jakeythedev.engine.utils.packets.reflection.Reflection;
import org.bukkit.entity.Player;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class TitlePacket
{

	public static void sendTitle(Player player, String titleArgs, String subTitleArgs)
	{
		try
		{

			Object enumTitle = Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
			Object enumSubTitle = Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);

			Object title = Reflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + titleArgs + "\"}");
			Object subTitle = Reflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + subTitleArgs + "\"}");

			Constructor<?> titleConstructor = Reflection.getNMSClass("PacketPlayOutTitle").getConstructor(
					Reflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
					Reflection.getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);

			Object packet1 = titleConstructor.newInstance(enumTitle, title, 20, 40, 20);
			Object packet2 = titleConstructor.newInstance(enumSubTitle, subTitle, 20, 40, 20);

			Reflection.sendPackets(player, packet1);
			Reflection.sendPackets(player, packet2);
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
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
	}
}
