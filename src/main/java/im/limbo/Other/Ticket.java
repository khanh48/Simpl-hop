package im.limbo.Other;

import org.bukkit.entity.Player;
import im.limbo.Main;

public class Ticket {
	public static final String NETHER_TICKET = "nether_ticket";
	public static final String THE_END_TICKET = "the_end_ticket";
	
	public static int getTicket(String type, Player player){
		return Main.getIntance().getConfig().getInt("data.ticket." + player.getName() + "." + type);
	}
	
	public static boolean addTicket(String type, Player player, int num) {
		if(num > 0) {
			Main.getIntance().getConfig().set("data.ticket." + player.getName() + "." + type, getTicket(type, player) + num);
			Main.getIntance().saveConfig();
			return true;
		}
		return false;
	}
	
	public static boolean removeTicket(String type, Player player, int num) {
		int ticket = getTicket(type, player);
		if(num > 0 && ticket >= num) {
			Main.getIntance().getConfig().set("data.ticket." + player.getName() + "." + type,  ticket - num);
			Main.getIntance().saveConfig();
			return true;
		}
		return false;
	}
	public static boolean buyTicket(String type, Player player, int num) {
		return false;
	}
}
