package me.dadogamer13.bansdatabase.addban.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dadogamer13.bansdatabase.main.Main;
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
		
		if(p.hasPermission("bandatabase.removeban")) {
			
			try {
				
				jugador = args[0].toString();
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Sintáxis inválida, por favor use: /removeban jugador");
				return true;
				
			}
			
			try {
				
				Connection bbdd = DriverManager.getConnection("jdbc:mysql://198.245.51.96:3306/db_56351", "db_56351", "13f94d20f2");
				
				Statement statement = bbdd.createStatement();
				//comandos sql
				statement.executeUpdate("DELETE FROM `BansPlugin` WHERE JUGADOR='"+jugador+"'");
				
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
