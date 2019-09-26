package me.dadogamer13.nightmareutils.bans.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dadogamer13.nightmareutils.main.Main;
import net.md_5.bungee.api.ChatColor;

public class AddbanCommand implements CommandExecutor {

	private Main plugin;
	
	public AddbanCommand(Main plugin) {
		
		this.plugin = plugin;
		plugin.getCommand("addban").setExecutor(this);
		
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		
		Player p = (Player) sender;
		String jugador, duracion = "4d", razon = "";
		
		long millis=System.currentTimeMillis();
		java.sql.Date dateSQL=new java.sql.Date(millis);
		
		
		if(p.hasPermission("nightmareutils.addban")) {
			
			try {
				
				jugador = args[0].toString();
				
				for(int i = 1; i < args.length; i++) {
					
					razon += args[i] + " ";
					
				}
				
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Sintáxis inválida, por favor use: " + plugin.getCommand("addban").getUsage());
				return true;
				
			}
			
			String ban = "tempban " + jugador + " " + duracion + " " + razon;
			//conexión con la base de datos
			try {
				
				String conextion_host = "jdbc:mysql://" + plugin.getConfig().getString("database_host") + ":" + plugin.getConfig().getString("database_port") + "/" + plugin.getConfig().getString("database_username");
				
				Connection bbdd = DriverManager.getConnection(conextion_host, plugin.getConfig().getString("database_username"), plugin.getConfig().getString("database_password"));
				
				Statement statement = bbdd.createStatement();
				
				//comandos sql
				String res = "SELECT * FROM " + plugin.getConfig().getString("database_table_name_bans") + " WHERE JUGADOR='"+jugador+"'";
				ResultSet resultset = statement.executeQuery(res);
				
				try {
					resultset.first();		
					if(resultset.getString("JUGADOR").equals(jugador)) {
						
						duracion = "Permanente";
						p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "BansDB" + ChatColor.WHITE + " > " + ChatColor.AQUA + "Este fue el segundo ban del jugador, por lo tanto fue baneado " + ChatColor.RED + "permanentemente" + ChatColor.AQUA + ".");
						
						ban = "ban "+jugador+" "+razon+"";
						
					} else {
						
						ban = "tempban " + jugador + " " + duracion + " " + razon;
						
					}
					
				} catch(Exception e) {
					ban = "tempban " + jugador + " " + duracion + " " + razon;
				}
				
				String update = "INSERT INTO `" + plugin.getConfig().getString("database_table_name_bans") + "`(`JUGADOR`, `RAZON`, `DURACION`, `DATEADDED`) VALUES ('"+jugador+"','"+razon+"','"+duracion+"','"+dateSQL+"')";
				statement.executeUpdate(update);
				
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Error al conectar con la base de datos :(");
				e.printStackTrace();
				
			}
			
			
			p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "BansDB" + ChatColor.WHITE + " > " + ChatColor.AQUA + "Baneo añadido a la base de datos. (ign:" + jugador + ", t:" + duracion + ", r:" + razon + ")");
			
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), ban);
			
			return true;
			
		} else {
			
			p.sendMessage("No tenes permiso para ejecutar este comando.");
			
		}
		
		return false;
	}
	
	
}
