package im.limbo.Hook;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import im.limbo.Main;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultHooker {
    private Economy econ;
    private Permission perms;
    private Chat chat;
	private final Main main;
	
	public VaultHooker() {
		this.main = Main.getIntance();
		if (!setupEconomy()) {
			Main.sendMessage(main.getServer().getConsoleSender(), "&cCan't hook into Vault!");
            Bukkit.getPluginManager().disablePlugin(main);
            return;
        }
        this.setupPermissions();
        this.setupChat();
	}
	
	 private boolean setupEconomy() {
	        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
	            return false;
	        }

	        RegisteredServiceProvider<Economy> rsp = main.getServer().getServicesManager().getRegistration(Economy.class);
	        if (rsp == null) {
	            return false;
	        }
	        econ = rsp.getProvider();
	        return econ != null;
	    }

	    private boolean setupChat() {
	        RegisteredServiceProvider<Chat> rsp = main.getServer().getServicesManager().getRegistration(Chat.class);
	        chat = rsp.getProvider();
	        return chat != null;
	    }
	    
	    private boolean setupPermissions() {
	        RegisteredServiceProvider<Permission> rsp = main.getServer().getServicesManager().getRegistration(Permission.class);
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
