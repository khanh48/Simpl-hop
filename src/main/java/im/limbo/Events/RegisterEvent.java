package im.limbo.Events;

import org.bukkit.plugin.PluginManager;

import im.limbo.Main;

public class RegisterEvent {
	private Main main;

	public RegisterEvent() {
		main = Main.getIntance();
		PluginManager manager = main.getServer().getPluginManager();

		manager.registerEvents(new TransportEvent(), main);
	}
}
