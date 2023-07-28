package im.limbo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import im.limbo.Commands.RegisterCommands;
import im.limbo.Events.RegisterEvent;
import im.limbo.Hook.VaultHooker;
import im.limbo.Message.Message;

public class Main extends JavaPlugin{
	private static Main intance;
	private RegisterCommands commands;
	private RegisterEvent events;
	public static VaultHooker vault;
	
	public Main() {
		intance = this;
	}
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		vault = new VaultHooker();
		commands = new RegisterCommands();
		events = new RegisterEvent();
	}
	
	public static Main getIntance() {
		return intance;
	}
	
	public static double getBalance(Player player) {
		return vault.getEconomy().getBalance(player);
	}
	
	public RegisterCommands getRegisterCommands() {
		return commands;
	}
	
	public void reload() {
		reloadConfig();
		saveDefaultConfig();
		Message.reload();
	}
	
}
