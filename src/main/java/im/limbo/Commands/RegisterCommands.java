package im.limbo.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import im.limbo.Main;
import im.limbo.Config.DefaultConfig;
import im.limbo.Message.Message;
import im.limbo.Other.Spawn;
import im.limbo.Other.Ticket;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class RegisterCommands {
	private Spawn spawn;
	private Main main;

	public RegisterCommands() {
		main = Main.getIntance();
		spawn = new Spawn();
		main.getCommand("spawn").setExecutor(new SpawnCommand());
		main.getCommand("setspawn").setExecutor(new SetSpawnCommand());
		main.getCommand("balance").setExecutor(new BalanceCommand());
		main.getCommand("ticket").setExecutor(new TicketCommand());
		main.getCommand("lreload").setExecutor(new ReloadCommand());

	}

	private class TicketCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(args.length > 0) {
					if(args[0].equalsIgnoreCase("buynether")) {
						Main.vault.getEconomy().withdrawPlayer(player, DefaultConfig.getTicketPrice());
						Ticket.addTicket(Ticket.NETHER_TICKET, player, 1);
						Message.sendMessage(sender, "Bạn vừa mua vé nether với giá $" + DefaultConfig.getTicketPrice());
						return true;
					}
					else if(args[0].equalsIgnoreCase("buytheend")) {
						Main.vault.getEconomy().withdrawPlayer(player, DefaultConfig.getTicketPrice());
						Ticket.addTicket(Ticket.THE_END_TICKET, player, 1);
						Message.sendMessage(sender, "Bạn vừa mua vé nether với giá $" + DefaultConfig.getTicketPrice());
						return true;
					}
					return false;
				}else {
					TextComponent shop = new TextComponent("Shop ticket:\n");
					TextComponent nether = new TextComponent("Vé Nether (giá $10000): ");
					TextComponent yNether = new TextComponent("&a>mua<\n");
					yNether.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/ticket buynether"));
					TextComponent theEnd = new TextComponent("Vé The End (giá $15000): ");
					TextComponent yTheEnd = new TextComponent("&a>mua<\n");
					yTheEnd.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/ticket buytheend"));
					shop.addExtra(nether);
					shop.addExtra(yNether);
					shop.addExtra(theEnd);
					shop.addExtra(yTheEnd);
					player.spigot().sendMessage(shop);
				}
				return true;
			}
			return false;
			
		}
	}
	
	
	private class BalanceCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				Message.sendMessage(sender, "&6Tiền: &r$" + Main.getBalance(player));
				Message.sendMessage(sender, "&6Vé đi Nether: &r" + Ticket.getTicket(Ticket.NETHER_TICKET, player));
				Message.sendMessage(sender, "&6Vé đi The End: &r" + Ticket.getTicket(Ticket.THE_END_TICKET, player));
				return true;
			}
			if(sender.hasPermission("other.admin") && args.length > 0) {
				Player player = main.getServer().getPlayer(args[0]);
				if(player != null) {
					Message.sendMessage(sender, "&6Tiền: &r$" + Main.getBalance(player));
					Message.sendMessage(sender, "&6Vé đi Nether: &r" + Ticket.getTicket(Ticket.NETHER_TICKET, player));
					Message.sendMessage(sender, "&6Vé đi The End: &r" + Ticket.getTicket(Ticket.THE_END_TICKET, player));
				}
			}
			return false;
		}
	}
	

	private class SpawnCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("other.spawn")) {
					spawn.spawn(player);
					return true;
				}
				Message.sendMessage(player, Message.NO_PERMISSION);
				return false;
			}
			Message.sendMessage(sender, Message.CONSOLE);
			return false;
		}

	}

	private class SetSpawnCommand implements CommandExecutor {

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("other.setspawn")) {
					spawn.setSpawn(player);
					return true;
				}
				Message.sendMessage(player, Message.NO_PERMISSION);
				return false;
			}
			Message.sendMessage(sender, Message.CONSOLE);
			return false;
		}

	}
}
