package im.limbo.Config;

import im.limbo.Main;

public class DefaultConfig {
	
	public static boolean isEnableNetherTicket() {
		return Main.getIntance().getConfig().getBoolean("enable-nether-ticket");
	}
	public static boolean isEnableTheEndTicket() {
		return Main.getIntance().getConfig().getBoolean("enable-the-end-ticket");
	}
	public static void setEnableNetherTicket(boolean flag) {
		Main.getIntance().getConfig().set("enable-nether-ticket", flag);
		Main.getIntance().saveConfig();
	}
	public static void setEnableTheEndTicket(boolean flag) {
		Main.getIntance().getConfig().set("enable-the-end-ticket", flag);
		Main.getIntance().saveConfig();
	}
	
	public static double getTheEndTicketPrice() {
		return Main.getIntance().getConfig().getDouble("the-end-ticket-price");
	}

	public static double getNetherTicketPrice() {
		return Main.getIntance().getConfig().getDouble("nether-ticket-price");
	}
	public static double getExpDrop() {
		return Main.getIntance().getConfig().getDouble("exp-drop");
	}
}
