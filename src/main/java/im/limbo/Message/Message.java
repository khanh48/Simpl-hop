package im.limbo.Message;

import java.lang.String;

import im.limbo.Main;

public class Message {
	public static String SETSPAWN = message("setspawn");
	public static String SPAWN = message("spawn");
	public static String NO_SPAWNPOINT = message("no_spawnpoint");
	public static String NO_PERMISSION = message("no_permission");
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
		RELOAD = message("reload");
		CONSOLE = message("console");
	}
}
