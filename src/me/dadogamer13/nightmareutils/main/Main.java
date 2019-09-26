package me.dadogamer13.nightmareutils.main;

import org.bukkit.plugin.java.JavaPlugin;

import me.dadogamer13.nightmareutils.bans.commands.AddbanCommand;
import me.dadogamer13.nightmareutils.bans.commands.HistorialCommand;
import me.dadogamer13.nightmareutils.bans.commands.RemoveBanCommand;
import me.dadogamer13.nightmareutils.color.commands.ColorCommand;
import me.dadogamer13.nightmareutils.color.ui.UI;
import me.dadogamer13.nightmareutils.color.ui.listeners.InventoryClickListener;
import me.dadogamer13.webconnect.com.netfily.nightmarefactions.PlayerList.OnlinePlayers;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		
		saveDefaultConfig();
		
		new DatabaseEnvSetup(this);
		
		new ColorCommand(this);
		new InventoryClickListener(this);
		UI.initialize();
		new AddbanCommand(this);
		new HistorialCommand(this);
		new RemoveBanCommand(this);
		new OnlinePlayers(this);
		
	}
	
}
