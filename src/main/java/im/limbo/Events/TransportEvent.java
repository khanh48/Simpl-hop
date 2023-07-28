package im.limbo.Events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import im.limbo.Config.DefaultConfig;
import im.limbo.Message.Message;
import im.limbo.Other.Ticket;

public class TransportEvent implements Listener{

	@EventHandler
	public void onGoToNether(PlayerPortalEvent event) {
		Player player = event.getPlayer();
		if(event.getTo().getWorld().getEnvironment().equals(World.Environment.NETHER) && DefaultConfig.isEnableNetherTicket()) {
			if(!Ticket.removeTicket(Ticket.NETHER_TICKET, event.getPlayer(), 1)) {
				Message.sendMessage(player, Message.NO_TICKET);
				event.setCancelled(true);
			}else {
				Message.sendMessage(player, Message.GO_TO, event.getTo().getWorld().getName(), (int)(DefaultConfig.getExpSave() * 100));
			}
		}
		if(event.getTo().getWorld().getEnvironment().equals(World.Environment.THE_END) && DefaultConfig.isEnableTheEndTicket()) {
			if(!Ticket.removeTicket(Ticket.THE_END_TICKET, event.getPlayer(), 1)) {
				Message.sendMessage(player, Message.NO_TICKET);
				event.setCancelled(true);
			}else {
				Message.sendMessage(player, Message.GO_TO, event.getTo().getWorld().getName(), (int)(DefaultConfig.getExpSave() * 100));
			}
		}
	}
	
	@EventHandler
	public void onDie(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if(player.getWorld().getEnvironment().equals(World.Environment.NETHER) && DefaultConfig.isEnableNetherTicket()) {
			int exp = player.getTotalExperience();
			event.setNewTotalExp((int)(DefaultConfig.getExpSave() * exp));
			event.setKeepInventory(true);
		}
		if(player.getWorld().getEnvironment().equals(World.Environment.THE_END) && DefaultConfig.isEnableTheEndTicket()) {
			int exp = player.getTotalExperience();
			event.setNewTotalExp((int)(DefaultConfig.getExpSave() * exp));
			event.setKeepInventory(true);
		}
	}
}
