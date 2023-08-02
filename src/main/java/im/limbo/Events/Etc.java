package im.limbo.Events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import im.limbo.Main;
import im.limbo.Message.Message;

public class Etc implements Listener{
	
	private Main main;
	public Etc() {
		main = Main.getIntance();
	}

	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		if(e.getEntityType().equals(EntityType.ENDERMITE) && e.getLocation().getWorld().getEnvironment().equals(Environment.THE_END)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if(e.getEntityType().equals(EntityType.ENDER_DRAGON)) {

			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(inside(e.getEntity().getLocation(), p.getLocation(), 200) && p.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
					p.giveExpLevels(10);
					p.sendTitle(Message.format("&6CHÚC MỪNG " + e.getEntity().getKiller().getName().toUpperCase()), Message.format("&6Đã hạ Rồng Ender"), 1, 100, 1);
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
					}.runTaskTimer(main, rd.nextInt(60), 60);

					if(e.getEntity().getKiller().equals(p)) {
						Main.vault.getEconomy().depositPlayer(p, 50000);
						Message.sendMessage(p, "Chúc mừng bạn đã giết rồng và nhận được $50000");
					}
					else {
						Main.vault.getEconomy().depositPlayer(p, 20000);
						Message.sendMessage(p, "Xin chúc mừng bạn đã tham gia giết rồng và nhận được $20000");
					}
				}
			}
		}
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
}
