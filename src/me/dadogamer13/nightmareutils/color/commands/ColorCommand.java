package me.dadogamer13.nightmareutils.color.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dadogamer13.nightmareutils.color.ui.UI;
import me.dadogamer13.nightmareutils.main.Main;
import net.md_5.bungee.api.ChatColor;

public class ColorCommand implements CommandExecutor {
	
	private Main plugin;
	
	public ColorCommand(Main plugin) {
		
		this.plugin = plugin;
		plugin.getCommand("color").setExecutor(this);
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		Player p = (Player) sender;
		
		if(p.hasPermission("nightmareutils.color")) {
				
			p.openInventory(UI.GUI(p));
			
		} else {
			
			p.sendMessage(ChatColor.RED + "No tienes permiso para ejecutar este comando. Solo VIPs. /store");
			
		}
		
		
		return false;
	}
	
}
