package me.dadogamer13.nightmareutils.bans.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dadogamer13.nightmareutils.main.Main;
import net.md_5.bungee.api.ChatColor;

public class HistorialCommand implements CommandExecutor {

	private Main plugin;
	
	public HistorialCommand(Main plugin) {
		
		this.plugin = plugin;
		plugin.getCommand("historial").setExecutor(this);
		
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		Player p = (Player) sender;
		String jugador;
		
		if(p.hasPermission("nightmareutils.historial")) {
			
			try {
				
				jugador = args[0].toString();
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Sintáxis inválida, por favor use: " + plugin.getCommand("historial").getUsage());
				return true;
				
			}
			
			//conexión a la base de datos
			
			try {
				
				String conextion_host = "jdbc:mysql://" + plugin.getConfig().getString("database_host") + ":" + plugin.getConfig().getString("database_port") + "/" + plugin.getConfig().getString("database_username");
				
				Connection bbdd = DriverManager.getConnection(conextion_host, plugin.getConfig().getString("database_username"), plugin.getConfig().getString("database_password"));
				
				Statement statement = bbdd.createStatement();
				
				//comandos sql
				String res = "SELECT * FROM " + plugin.getConfig().getString("database_table_name_bans") + " WHERE JUGADOR='"+jugador+"'";
				ResultSet resultset = statement.executeQuery(res);				
				
				p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "BansDB" + ChatColor.WHITE + " > " + "Historial del jugador " + jugador + ":");
				
				
				resultset.first();
				
				p.sendMessage("   - " + ChatColor.GRAY + resultset.getString("DATEADDED") + ChatColor.WHITE + ": Razón: " + resultset.getString("RAZON") + " | Duración: " + resultset.getString("DURACION"));
				
				while(resultset.next()) {
					
					p.sendMessage("   - " + ChatColor.GRAY + resultset.getString("DATEADDED") + ChatColor.WHITE + ": Razón: " + resultset.getString("RAZON") + " | Duración: " + resultset.getString("DURACION"));
					
				}
				
				return true;
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Jugador no encontrado.");

			}
			
		}
		
		return false;
	}
	
	
}
