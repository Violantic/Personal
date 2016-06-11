package com.jakeythedev.engine.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilItemBuilder
{
	private ItemStack itemStack;
	private ItemMeta itemMeta;

	public UtilItemBuilder(Material material)
	{
		itemStack = new ItemStack(material);
		itemMeta = itemStack.getItemMeta();
	}

	public UtilItemBuilder(Material material, byte data)
	{
		itemStack = new ItemStack(material, 1, data);
		itemMeta = itemStack.getItemMeta();
	}

	public UtilItemBuilder setName(String name)
	{
		itemMeta.setDisplayName(name);
		return this;
	}

	public UtilItemBuilder setLore(String... lore)
	{
		itemMeta.setLore(Arrays.asList(lore));
		return this;
	}

	public UtilItemBuilder setAmount(int amount)
	{
		itemStack.setAmount(amount);
		return this;
	}

	public ItemStack build()
	{
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}
