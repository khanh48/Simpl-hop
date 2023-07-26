package im.limbo.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import im.limbo.Main;
import im.limbo.Message.Message;

public class ReloadCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		if (sender instanceof Player) {
			if (!sender.hasPermission("other.admin")) {
				Main.sendMessage(sender, Message.NO_PERMISSION);
				return false;
			}
		}
		Main.getIntance().reload();
		Main.sendMessage(sender, Message.RELOAD);
		return true;
	}

}
