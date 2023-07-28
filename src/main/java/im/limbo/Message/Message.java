package im.limbo.Message;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import im.limbo.Main;

public class Message {
	public static String SETSPAWN = message("setspawn");
	public static String SPAWN = message("spawn");
	public static String NO_SPAWNPOINT = message("no_spawnpoint");
	public static String NO_PERMISSION = message("no_permission");
	public static String NO_TICKET = message("no_ticket");
	public static String GO_TO = message("go_to");
	public static String RELOAD = message("reload");
	public static String CONSOLE = message("console");

	public static boolean contains(String src, String string) {
		return src.toLowerCase().contains(string.toLowerCase());
	}

	private static String message(String name) {
		return Main.getIntance().getConfig().getString("message." + name);
	}

	public static String replace(String src, String target, String replacement) {
		return src.replaceAll("(?i)" + target, replacement);
	}

	public static void reload() {
		SETSPAWN = message("setspawn");
		SPAWN = message("spawn");
		NO_SPAWNPOINT = message("no_spawnpoint");
		NO_PERMISSION = message("no_permission");
		NO_TICKET = message("no_ticket");
		GO_TO = message("go_to");
		RELOAD = message("reload");
		CONSOLE = message("console");
	}
	

	public static String format(String msg) {
		return ChatColor.translateAlternateColorCodes('&', "&3&l[Other]&r " + msg);
	}
	
	public static String format(String... msg) {
		String tmp = "";
		for (String string : msg)
			tmp += string;
		return ChatColor.translateAlternateColorCodes('&', tmp);
	}
	
	public static List<String> format(List<String> msg) {
		List<String> tmp = new ArrayList<>();
		for (String string : msg)
			tmp.add(nonFormat(string));
		return tmp;
	}
	
	public static String nonFormat(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(nonFormat(message));
	}
	
	public static void sendMessage(CommandSender sender, String message, String world, int exp) {
		String tmp = "";
		tmp = Message.replace(Message.replace(message, "%world%", world), "%exp%", String.valueOf(exp));
		sender.sendMessage(format(tmp));
	}
}
