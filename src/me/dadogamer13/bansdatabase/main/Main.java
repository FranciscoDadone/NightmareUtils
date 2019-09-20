package me.dadogamer13.bansdatabase.main;

import org.bukkit.plugin.java.JavaPlugin;

import me.dadogamer13.bansdatabase.addban.commands.AddbanCommand;
import me.dadogamer13.bansdatabase.addban.commands.HistorialCommand;
import me.dadogamer13.bansdatabase.addban.commands.RemoveBanCommand;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		
		new AddbanCommand(this);
		new HistorialCommand(this);
		new RemoveBanCommand(this);
		
	}
	
}
