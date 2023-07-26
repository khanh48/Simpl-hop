package im.limbo.Events;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import im.limbo.Other.Ticket;

public class TransportEvent implements Listener{

	@EventHandler
	public void onGoToNether(PlayerPortalEvent event) {
		if(event.getTo().getWorld().getEnvironment().equals(World.Environment.NETHER)) {
			if(!Ticket.removeTicket(Ticket.NETHER_TICKET, event.getPlayer(), 1)) {
				event.setCancelled(true);
			}
		}
	}
}
