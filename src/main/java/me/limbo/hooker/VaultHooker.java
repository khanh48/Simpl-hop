package me.limbo.hooker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.limbo.Main;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultHooker {
    private Economy econ;
    private Permission perms;
    private Chat chat;
	Main m;
	
	public VaultHooker(Main m) {
		this.m = m;
		if (!setupEconomy()) {
            Bukkit.getPluginManager().disablePlugin(m);
            return;
        }
        this.setupPermissions();
        this.setupChat();
	}
	
	 private boolean setupEconomy() {
	        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
	            return false;
	        }

	        RegisteredServiceProvider<Economy> rsp = m.getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	        econ = rsp.getProvider();
	        return econ != null;
	    }

	    private boolean setupChat() {
	        RegisteredServiceProvider<Chat> rsp = m.getServer().getServicesManager().getRegistration(Chat.class);
	        chat = rsp.getProvider();
	        return chat != null;
	    }
	    
	    private boolean setupPermissions() {
	        RegisteredServiceProvider<Permission> rsp = m.getServer().getServicesManager().getRegistration(Permission.class);
	        perms = rsp.getProvider();
	        return perms != null;
	    } 
	    public Economy getEconomy() {
	        return econ;
	    }

	    public Permission getPermissions() {
	        return perms;
	    }

	    public Chat getChat() {
	        return chat;
	    }

}
