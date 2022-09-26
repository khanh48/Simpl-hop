package me.limbo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.limbo.Config.CustomConfig;
import me.limbo.backpack.BackPack;
import me.limbo.commands.Commands;
import me.limbo.hooker.VaultHooker; 
import me.limbo.kit.Kit;
import me.limbo.other.Other;
import me.limbo.regEvents.RegEvents;

public class Main extends JavaPlugin{
	private static Main intance;
	VaultHooker v;
	BackPack backPack;
	CustomConfig backPackConfig, dataConfig, kitConfig;
	Other other;
	Kit kit;
	RegEvents regEvent;
	
	@Override
	public void onEnable() {
		intance = this;
		dataConfig = new CustomConfig(this, "data");
		backPackConfig = new CustomConfig(this, "backpack");
		kitConfig = new CustomConfig(this, "kit");
		this.saveDefaultConfig();
		this.v = new VaultHooker(this);
		this.other = new Other(this);
		this.backPack = new BackPack(this);
		this.kit = new Kit(this);
		this.regEvent = new RegEvents(this);
		other.setSpawn((Location) dataConfig.getConfig().get("spawnpoint"));
		this.getCommand("other").setExecutor(new Commands(this));
		this.getCommand("sopen").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					if(args.length == 0 || args == null) {
						sender.sendMessage("Bạn chưa nhập tên người chơi!");
						return false;
					}
						if(p.hasPermission("shop.admin"))
							p.openInventory(BackPack.listInv.get(Bukkit.getPlayer(args[0]).getName()));
						else
							sendMsg(p, "&cBạn cần có quyền để thực hiện");
						return false;
				}
				sendMsg(sender, "&cKhông thể sử dụng lệnh này!");
				return false;
			}
			
		});
		this.getCommand("setspawn").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
						if(p.hasPermission("shop.admin")) {
								other.setSpawn(p.getLocation());
								dataConfig.getConfig().set("spawnpoint", p.getLocation());
								saveConfig();
								sendMsg(p, "&cĐã đặt điểm spawn");
							}
						else
							sendMsg(p, "&cBạn cần có quyền để thực hiện");
						return false;
				}
				sendMsg(sender, "&cKhông thể sử dụng lệnh này!");
				return false;
			}
			
		});
		this.getCommand("spawn").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					other.spawn(p);
					return false;
				}
				sendMsg(sender, "&cKhông thể sử dụng lệnh này!");
				return false;
			}
			
		});
		other.unWhiteList();
	}
	
	public static Main getIntance() {
		return intance;
	}
	
	@Override
	public void onDisable() {
	}
	
	public CustomConfig getDataConfig() {
		return this.dataConfig;
	}
	
	public VaultHooker getEco() {
		return this.v;
	}
	public Other getOther() {
		return other;
	}
	public Kit getKit() {
		return this.kit;
	}
	public BackPack getBackPack() {
		return this.backPack;
	}
	public FileConfiguration config() {
		return this.getConfig();
	}
	public CustomConfig backPackConfig() {
		return this.backPackConfig;
	}
	public CustomConfig kitConfig() {
		return this.kitConfig;
	}
	
	public static void sendMsg(CommandSender sender ,String string) {
		sender.sendMessage(format(string));
	}
	
	public static String format(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
}
