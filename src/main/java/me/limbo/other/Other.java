package me.limbo.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
//import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jyckosianjaya.easywhitelist.EasyWhiteList;

import me.limbo.Main;
//import ru.beykerykt.minecraft.lightapi.common.LightAPI;

public class Other implements Listener{
	static final long unWL = 86400000 * 7; // 7 days
	static Location old;
	boolean flag = true;
	Main m;
	Location spawnLoc;
	List<String> unWhiteListed = new ArrayList<>();
	public BukkitRunnable firew;
	public static int rad = 60;
	
	public Other(Main m) {
		this.m = m;
		firew = new BukkitRunnable() {
				
				@Override
				public void run() {
					Other.fire();
				}
		};
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPermission("shop.admin") && m.getConfig().getBoolean("maintenance")) {
			p.kickPlayer("Server đang bảo trì, vui lòng quay lại sau.");
		}
		if(m.getConfig().getBoolean("spawn-on-join"))
			spawn(p);
//		if(!p.hasPlayedBefore()) {
//			spawn(p);
//			ItemStack newMem = new ItemStack(Material.WRITTEN_BOOK);
//			BookMeta newMeta = (BookMeta) newMem.getItemMeta();
//			newMeta.setDisplayName("Hướng dẫn new member");
//			newMeta.setTitle("Note book");
//			newMeta.setPages(m.getDataConfig().getConfig().getStringList("newbie"));
//			newMeta.setAuthor("Limbo");
//			newMem.setItemMeta(newMeta);
//			p.getInventory().setItemInMainHand(newMem);
//			ItemStack cm = new ItemStack(Material.WRITTEN_BOOK);
//			BookMeta cmMeta = (BookMeta) cm.getItemMeta();
//			cmMeta.setDisplayName("Các lệnh cơ bản");
//			cmMeta.setTitle("Commands");
//			cmMeta.setPages(m.getDataConfig().getConfig().getStringList("commands"));
//			cmMeta.setAuthor("Limbo");
//			cm.setItemMeta(cmMeta);
//			p.getInventory().addItem(cm);
//		}
	}

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if(e.getEntityType().equals(EntityType.ENDER_DRAGON)) {
			
			e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2048);;
			
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(inside(e.getEntity().getLocation(), p.getLocation(), 200) && p.getWorld().getName().equals("world_the_end")) {
					p.giveExpLevels(10);
					p.sendTitle(Main.format("&6CHÚC MỪNG " + e.getEntity().getKiller().getName().toUpperCase()), Main.format("&6Đã hạ Rồng Ender"), 1, 100, 1);
					Random rd = new Random();
					new BukkitRunnable() {
						int tmp = 0;
						@Override
						public void run() {
							spawnRandomFirework(p.getLocation());
							tmp++;
							if(tmp == 15) {
								this.cancel();
							}
						}
					}.runTaskTimer(m, rd.nextInt(60), 60);
					
					if(e.getEntity().getKiller().equals(p)) {
						m.getEco().getEconomy().depositPlayer(p, 50000);
						Main.sendMsg(p, "Chúc mừng bạn đã giết rồng và nhận được $50000");
					}
					else {
						m.getEco().getEconomy().depositPlayer(p, 20000);
						Main.sendMsg(p, "Xin chúc mừng bạn đã tham gia giết rồng và nhận được $20000");
					}
				}
			}
		}
		if(e.getEntityType().equals(EntityType.SHULKER)) {
			e.getDrops().clear();
		}
	}
	
	@EventHandler
	public void spawnDragon(CreatureSpawnEvent e) {
		if(e.getEntityType().equals(EntityType.ENDER_DRAGON)) {
			e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2048);
			e.getEntity().setHealth(2048);
		}
	}
	
	public void spawn(Player p) {
		if(spawnLoc != null) {
			p.teleport(spawnLoc);
		}
	}
	
	public void setSpawn(Location loc) {
		this.spawnLoc = loc;
	}
	
	public static void spawnRandomFirework(final Location loc) {
		final Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		final FireworkMeta fireworkMeta = firework.getFireworkMeta();
		final Random random = new Random();
		final FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(getColor(random.nextInt(17) + 1)).withFade(getColor(random.nextInt(17) + 1)).with(Type.values()[random.nextInt(Type.values().length)]).trail(random.nextBoolean()).build();
		fireworkMeta.addEffect(effect);
		fireworkMeta.setPower(random.nextInt(2) + 1);
		firework.setFireworkMeta(fireworkMeta);
	}

	public static void fire() {
		spawnRandomFirework(randomLoc(Main.getIntance().getOther().spawnLoc, rad, 1));
		
	}
	
	public static Location randomLoc(Location loc, int radius, int mode) {
		Location tmp = loc.clone();
			int x = (int) loc.getX();
			int y = (int) loc.getY();
			int z = (int) loc.getZ();
		if(mode > 0) {
			tmp.setX(rand(x - radius, x + radius));
			tmp.setY(rand(y, y + 100));
			tmp.setZ(rand(z - radius, z + radius));
		}else {
			tmp.setX(rand(x - radius, x + radius));
			tmp.setY(rand(y - radius, y + radius));
			tmp.setZ(rand(z - radius, z + radius));
		}
		return tmp;
	}
	
	private static Color getColor(final int i) {
		switch (i) {
		case 1:
			return Color.AQUA;
		case 2:
			return Color.BLACK;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.FUCHSIA;
		case 5:
			return Color.GRAY;
		case 6:
			return Color.GREEN;
		case 7:
			return Color.LIME;
		case 8:
			return Color.MAROON;
		case 9:
			return Color.NAVY;
		case 10:
			return Color.OLIVE;
		case 11:
			return Color.ORANGE;
		case 12:
			return Color.PURPLE;
		case 13:
			return Color.RED;
		case 14:
			return Color.SILVER;
		case 15:
			return Color.TEAL;
		case 16:
			return Color.WHITE;
		case 17:
			return Color.YELLOW;
		}
		return null;
	}

	public static boolean inside(Location newP, Location oldP, int rad) {
		double a = newP.getX() - oldP.getX() * newP.getX() - oldP.getX();
		double b = newP.getY() - oldP.getY() * newP.getY() - oldP.getY();
		return Math.sqrt(a + b) > rad? false : true;
	}
	
	public static int rand(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}
	
	public void unWhiteList() {
		List<String> wl;
		wl = m.getConfig().getStringList("whitelist");
		OfflinePlayer[] listPLayer = Bukkit.getServer().getOfflinePlayers();
		for (OfflinePlayer offlinePlayer : listPLayer) {
			if(System.currentTimeMillis() - offlinePlayer.getLastPlayed() > unWL && !wl.contains(offlinePlayer.getName().toLowerCase())) {
				EasyWhiteList.getInstance().getStorage().removeWhitelist(offlinePlayer.getName());
				unWhiteListed.add(offlinePlayer.getName());
			}
		}
		m.getDataConfig().getConfig().set("unWhiteListed", unWhiteListed);
		m.getDataConfig().saveConfig();
	}
	
	
	
}	
