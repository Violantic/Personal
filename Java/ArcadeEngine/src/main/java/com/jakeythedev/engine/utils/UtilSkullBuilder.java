package com.jakeythedev.engine.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

/**
 * C R E A T E D
 * B Y
 * J A K E Y T H E D E V
 * O N
 * 11/06/2016
 */
public class UtilSkullBuilder
{
	private ItemStack itemStack;
	private SkullMeta itemMeta;

	public UtilSkullBuilder()
	{
		itemStack = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		itemMeta = (SkullMeta) itemStack.getItemMeta();
	}

	public UtilSkullBuilder setName(String name)
	{
		itemMeta.setDisplayName(name);
		return this;
	}

	public UtilSkullBuilder setOwner(String name)
	{
		itemMeta.setOwner(name);
		return this;
	}
	
	public UtilSkullBuilder setLore(String... lore)
	{
		itemMeta.setLore(Arrays.asList(lore));
		return this;
	}

	public UtilSkullBuilder setAmount(int amount)
	{
		itemStack.setAmount(amount);
		return this;
	}

	public UtilSkullBuilder addEnchant(Enchantment enchant, int level)
	{
		itemMeta.addEnchant(enchant, level, true);
		return this;
	}

	public ItemStack build()
	{
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}
