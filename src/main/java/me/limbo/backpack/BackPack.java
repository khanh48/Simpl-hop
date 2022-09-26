package me.limbo.backpack;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.limbo.Main;

public class BackPack implements Listener{
	public static HashMap<String, Inventory> listInv;
	HashMap<String, Integer> maxsLen;
	Main m;
	static boolean isSneaking;

	public BackPack(Main m) {
		this.m = m; 
		listInv = new HashMap<String, Inventory>();
		maxsLen = new HashMap<String, Integer>();
		isSneaking = false;
	}
	
	@EventHandler
	public void isSneak(PlayerToggleSneakEvent e) {
		if(e.isSneaking())
			isSneaking = true;
		else
			isSneaking = false;
	}

	@EventHandler
	public void interactEvent(PlayerInteractEvent e) {
		if(isSneaking && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			isSneaking = false;
			ItemStack is = e.getPlayer().getInventory().getItem(EquipmentSlot.HEAD);
			if(is == null) return;
			if(is.getType().equals(Material.EMERALD))
				e.getPlayer().openInventory(listInv.get(e.getPlayer().getName()));
			return;
		}
		String name = e.getPlayer().getName();
		ItemStack tmp = e.getItem();
		if(tmp == null) return;
		List<String> meta = tmp.getItemMeta().getLore();
		if(meta == null) return;
		if((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && meta.get(meta.size() - 1).contains("Tối đa 54 ô")) {
			if(maxsLen.get(name) > 45) {
				Main.sendMsg(e.getPlayer(), "&cBalo của bạn đã đạt giới hạn!");
				return;
			}
			if(tmp.getAmount() > 1) {
				tmp.setAmount(tmp.getAmount() - 1);
				e.getPlayer().getInventory().setItemInMainHand(tmp);
			}else if(tmp.getAmount() == 1)
				e.getPlayer().getInventory().setItemInMainHand(null);
			maxsLen.put(name, maxsLen.get(name) + 9);
			save(name);
			load(name);
		}
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		load(e.getPlayer().getName());
	}
	
	@EventHandler
	public void invEvent(InventoryClickEvent e) {
			Player p = (Player) e.getWhoClicked();
			if(e.getInventory().equals(listInv.get(p.getName()))) {
				Bukkit.getScheduler().runTaskLater(m, () -> {
					save(p.getName());
				}, 10);
			}
	}
	
	public void load(String name) {
		String mon;
		int maxLen;
		ItemStack is;
		
		Player p = Bukkit.getPlayer(name);
		mon = "Money: $" + m.getEco().getEconomy().getBalance(p);
		m.backPackConfig().reloadConfig();
		maxLen = m.backPackConfig().getConfig().getInt(name + ".maxLen") == 0? 27 : m.backPackConfig().getConfig().getInt(name + ".maxLen");
		maxsLen.put(name, maxLen);
		listInv.put(name, Bukkit.createInventory(p, maxLen, mon));
		
		ConfigurationSection items = m.backPackConfig().getConfig().getConfigurationSection(name + ".items");
		if(items == null) return;
		for (String sl : items.getKeys(false)) {
			is = (ItemStack) m.backPackConfig().getConfig().get(name + ".items." + sl);
			if(is == null) continue;
			listInv.get(name).setItem(Integer.parseInt(sl), is);
		}
	}
	
	public void save(String name) {
		try {
			m.backPackConfig().getConfig().set(name, null);
			m.backPackConfig().getConfig().set(name + ".maxLen", maxsLen.get(name));
			for (int i = 0; i < listInv.get(name).getSize(); i++) {
				ItemStack is = listInv.get(name).getItem(i);
				if(is == null) continue;
				m.backPackConfig().getConfig().set(name + ".items." + i, is);
			}
			m.backPackConfig().saveConfig();
		}catch(Exception ex) {
			Main.sendMsg(Bukkit.getConsoleSender(), "Can't save " + name);
		}
	}
	
}
