package me.dadogamer13.bansdatabase.addban.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dadogamer13.bansdatabase.main.Main;
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
		String jugador, razon, dateadded;
		ResultSet duracion;
		
		if(p.hasPermission("bandatabase.historial")) {
			
			try {
				
				jugador = args[0].toString();
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Sintáxis inválida, por favor use: " + plugin.getCommand("historial").getUsage());
				return true;
				
			}
			
			//conexión a la base de datos
			
			try {
				
				Connection bbdd = DriverManager.getConnection("jdbc:mysql://198.245.51.96:3306/db_56351", "db_56351", "13f94d20f2");
				
				Statement statement = bbdd.createStatement();
				
				//comandos sql
				ResultSet resultset = statement.executeQuery("SELECT * FROM BansPlugin WHERE JUGADOR='"+jugador+"'");				
				
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
