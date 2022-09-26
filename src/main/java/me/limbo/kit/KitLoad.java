package me.limbo.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import me.limbo.Main;

public class KitLoad {
	Main m;
	String name, type;
	List<String> lore, enc;
	int amount, durability;
	ArrayList<KitLoad> map = new ArrayList<KitLoad>();
	
	public KitLoad(Main m) {
		this.m = m;
		loadConfig();
	}
	public KitLoad(String name, String type, int durability, int amount, List<String> enchant, List<String> lore) {
		this.name = name;
		this.durability = durability;
		this.amount = amount;
		this.type = type;
		this.enc = enchant;
		this.lore = lore;
	}
	
	
	public void loadConfig() {
		ConfigurationSection kitList = m.kitConfig().getConfig().getConfigurationSection("items");
		if(kitList == null) return;
		for(String lb: kitList.getKeys(false)) {
			//name
			name = m.kitConfig().getConfig().getString("items."+ lb + ".name");
			//type
			type = m.kitConfig().getConfig().getString("items."+ lb + ".type");
			
			amount = m.kitConfig().getConfig().getInt("items."+ lb + ".amount");
			
			durability = m.kitConfig().getConfig().getInt("items."+ lb + ".durability");
			
			lore = m.kitConfig().getConfig().getStringList("items."+ lb + ".lore");
			//id-enchant
			enc = m.kitConfig().getConfig().getStringList("items."+ lb + ".enchants");
			map.add(new KitLoad(name, type, durability, amount, enc, lore));
		}
	}

	public void reload() {
		map.clear();
		loadConfig();
	}

	public ArrayList<String> getID() {
		ArrayList<String> tmp = new ArrayList<>();
		for (String s : enc) {
			tmp.add(s.split("-")[0].toLowerCase());
		}
		return tmp;
	}
	public boolean isItemsAdder() {
		return type.contains(":");
	}
	public ArrayList<Integer> getLevel() {
		ArrayList<Integer> tmp = new ArrayList<>();
		for (String s : enc) {
			tmp.add(Integer.parseInt(s.split("-")[1]));
		}
		return tmp;
	}
	
	public ArrayList<KitLoad> getMap(){
		return this.map;
	}

}
