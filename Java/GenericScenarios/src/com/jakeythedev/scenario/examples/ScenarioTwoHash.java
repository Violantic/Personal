package com.jakeythedev.scenario.examples;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class ScenarioTwoHash<K, V>
{
	
	private HashMap<K, V> scenarioHash = new HashMap<>();
	
	
	public V get(K key)
	{
		return scenarioHash.get(key);
	}
	
	public V remove(K key)
	{
	
		return scenarioHash.remove(key);
	}
	
	public V put(K key, V value)
	{
		return scenarioHash.put(key, value);
	}
	
	public void clear()
	{
		if (scenarioHash.isEmpty())
		{
			System.out.println("Hash map is already empty.");
			return;
		}
		
		scenarioHash.clear();
	}
	
	public int size()
	{
		return scenarioHash.size();
	}

	public boolean isEmpty()
	{
		return scenarioHash.isEmpty();
	}
	
	public Set<Entry<K, V>> entrySet()
	{
		return scenarioHash.entrySet();
	}
	
	public Set<K> keySet()
	{
		return scenarioHash.keySet();
	}
	
	public Collection<V> values()
	{
		return scenarioHash.values();
	}

	public boolean containsKey(K key)
	{
		return scenarioHash.containsKey(key);
	}
	
	public boolean containsValue(V value)
	{
		return scenarioHash.containsValue(value);
	}
}
