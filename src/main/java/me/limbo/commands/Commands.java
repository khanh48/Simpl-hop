package me.limbo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.limbo.Main;
import me.limbo.other.Other;

public class Commands implements CommandExecutor {
	final Main m;
	boolean toggle;
	
	public Commands(Main m) {
		this.m = m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length > 0)
				if(args[0].equalsIgnoreCase("fire")) {
					if(p.hasPermission("shop.admin"))
						if(!toggle) {
							toggle = true;
							int time = 10;
							if(args.length > 1)
								Other.rad = Integer.parseInt(args[1]);
							if(args.length > 2)
								time = Integer.parseInt(args[2]);
							m.getOther().firew.runTaskTimer(m, 20, time);
						}else {
							toggle = false;
							m.getOther().firew.cancel();
						}
					else
						Main.sendMsg(p, "&cBạn cần có quyền để thực hiện");
					return false;
			    }
				else if(args[0].equalsIgnoreCase("reload")) {
					if(!p.hasPermission("shop.admin")) {
						Main.sendMsg(p, "&cBạn cần có quyền để thực hiện");
						return false;
					}
			    }
		}
		if(args[0].equalsIgnoreCase("reload") || label.equalsIgnoreCase("sreload")) {
			reload();
			Main.sendMsg(sender, "&cReloaded!");
			return false;
		}
		Main.sendMsg(sender,"&cKhông thể sử dụng lệnh này!");
		return false;
	}
	
	public void reload() {
		m.reloadConfig();
		m.getDataConfig().reloadConfig();
		m.backPackConfig().reloadConfig();
	}

}
