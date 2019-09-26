package me.dadogamer13.nightmareutils.bans.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dadogamer13.nightmareutils.main.Main;
import net.md_5.bungee.api.ChatColor;

public class RemoveBanCommand implements CommandExecutor{

	private Main plugin;
	
	public RemoveBanCommand(Main plugin) {
		
		this.plugin = plugin;
		plugin.getCommand("removeban").setExecutor(this);;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		Player p = (Player) sender;
		String jugador;
		
		if(p.hasPermission("nightmareutils.removeban")) {
			
			try {
				
				jugador = args[0].toString();
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Sintáxis inválida, por favor use: /removeban jugador");
				return true;
				
			}
			
			try {
				
				String conextion_host = "jdbc:mysql://" + plugin.getConfig().getString("database_host") + ":" + plugin.getConfig().getString("database_port") + "/" + plugin.getConfig().getString("database_username");
				
				Connection bbdd = DriverManager.getConnection(conextion_host, plugin.getConfig().getString("database_username"), plugin.getConfig().getString("database_password"));
				
				Statement statement = bbdd.createStatement();
				//comandos sql
				String update = "DELETE FROM `" + plugin.getConfig().getString("database_table_name_bans") + "` WHERE JUGADOR='"+jugador+"'";
				statement.executeUpdate(update);
				
				String unban = "unban " + jugador;
				
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), unban);
				
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "BansDB" + ChatColor.WHITE + " > " + ChatColor.AQUA + jugador + " ha sido desbaneado.");
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Error al conectar con la base de datos :(");
				e.printStackTrace();
				
			}
			
		}
		
		return false;
	}
	
}
