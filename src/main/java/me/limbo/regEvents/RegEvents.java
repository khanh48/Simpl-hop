package me.limbo.regEvents;

import org.bukkit.plugin.PluginManager;

import me.limbo.Main;

public class RegEvents{
	public RegEvents(Main m) {
		PluginManager pl = m.getServer().getPluginManager();
		pl.registerEvents(m.getBackPack(), m);
		pl.registerEvents(m.getOther(), m);
	}
}
