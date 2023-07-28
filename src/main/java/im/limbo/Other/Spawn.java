package im.limbo.Other;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import im.limbo.Main;
import im.limbo.Message.Message;

public class Spawn {
	private static Location spawnPoint;

	public Spawn() {
		loadSpawnPoint();
	}

	public void spawn(Player player) {
		if (spawnPoint == null) {
			Message.sendMessage(player, Message.NO_SPAWNPOINT);
			return;
		}
		player.teleport(spawnPoint);
		Message.sendMessage(player, Message.SPAWN);
	}

	public void setSpawn(Player player) {
		setSpawn(player.getLocation());
		Message.sendMessage(player, Message.SETSPAWN);
	}

	public void setSpawn(Location location) {
		spawnPoint = location;
		saveSpawnPoint();
	}

	public void saveSpawnPoint() {
		Main.getIntance().getConfig().set("data.spawnpoint", spawnPoint);
		Main.getIntance().saveConfig();
	}

	public void loadSpawnPoint() {
		spawnPoint = Main.getIntance().getConfig().getLocation("data.spawnpoint");
	}

	public Location getSpawnPoint() {
		return spawnPoint;
	}
}
