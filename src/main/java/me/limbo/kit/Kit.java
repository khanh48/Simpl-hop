package me.limbo.kit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scheduler.BukkitRunnable;

import dev.lone.itemsadder.api.CustomStack;
import me.limbo.Main;
import me.limbo.shop.Enchantments;

public class Kit {
	static final long newbie = 86400000 * 3; // 3 days
	static final long eight = 3600000 * 8; // 8 hours
	final Main m;
	KitLoad load;
	Enchantments enc;
	public static BukkitRunnable run;
	
	public Kit(Main m) {
		this.m = m;
		load = new KitLoad(m);
		enc = new Enchantments();
		run = new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					giveKit(p);
				}
			}
		};
		run.runTaskTimer(m, 300, 200);
	}
	
	public void giveKit(Player p) {
		long lastLogin = m.kitConfig().getConfig().getLong("data." + p.getName());
		if(System.currentTimeMillis() - p.getFirstPlayed() < newbie) {
			if(System.currentTimeMillis() - lastLogin >= eight && login(p)) {
				for (KitLoad kit : load.getMap()) {
					ItemStack item;
					if(kit.isItemsAdder()) {
						CustomStack cus = CustomStack.getInstance(kit.type.toLowerCase());
						item = cus.getItemStack();
						item.setAmount(kit.amount);
					}else
						item = new ItemStack(Material.getMaterial(kit.type), kit.amount);
					
					Damageable meta = (Damageable) item.getItemMeta();
					meta.setDisplayName(kit.name);
					meta.setDamage(kit.durability);
					for(int i = 0; i < kit.getID().size(); i++)
						meta.addEnchant(enc.getEnchantment(kit.getID().get(i)), kit.getLevel().get(i), true);
					meta.setLore(kit.lore);
					item.setItemMeta(meta);
					p.getInventory().addItem(item);
				}
				m.kitConfig().getConfig().set("data." + p.getName(), System.currentTimeMillis());
				m.kitConfig().saveConfig();
				Main.sendMsg(p, "&eBạn vừa nhận được đồ người mới.");
			}
		}
	}
	
	boolean login(Player p) {
		ItemStack one = p.getInventory().getItem(19);
		ItemStack two = p.getInventory().getItem(20);
		if(one == null || two == null) return true;
		if(one.getType().equals(Material.NAME_TAG) && two.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
			return false;
		}
		return true;
	}
}
