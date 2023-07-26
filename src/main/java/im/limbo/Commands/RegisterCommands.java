package im.limbo.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import im.limbo.Main;
import im.limbo.Message.Message;
import im.limbo.Other.Spawn;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class RegisterCommands {
	private Spawn spawn;

	public RegisterCommands() {
		Main main = Main.getIntance();
		spawn = new Spawn();
		main.getCommand("spawn").setExecutor(new SpawnCommand());
		main.getCommand("balance").setExecutor(new SpawnCommand());
		main.getCommand("setspawn").setExecutor(new SetSpawnCommand());
		main.getCommand("lreload").setExecutor(new ReloadCommand());

	}

	private class SpawnCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("other.spawn")) {
					spawn.spawn(player);
					BaseComponent[] baseComponent = TextComponent.fromLegacyText("");
					
					return true;
				}
				Main.sendMessage(player, Message.NO_PERMISSION);
				return false;
			}
			Main.sendMessage(sender, Message.CONSOLE);
			return false;
		}

	}

	private class SetSpawnCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("other.setspawn")) {
					spawn.setSpawn(player);
					return true;
				}
				Main.sendMessage(player, Message.NO_PERMISSION);
				return false;
			}
			Main.sendMessage(sender, Message.CONSOLE);
			return false;
		}

	}
}
