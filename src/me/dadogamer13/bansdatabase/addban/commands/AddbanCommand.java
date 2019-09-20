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

public class AddbanCommand implements CommandExecutor {

	private Main plugin;
	
	public AddbanCommand(Main plugin) {
		
		this.plugin = plugin;
		plugin.getCommand("addban").setExecutor(this);
		
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		
		Player p = (Player) sender;
		String jugador, duracion = "4d", razon;
		
		long millis=System.currentTimeMillis();
		java.sql.Date dateSQL=new java.sql.Date(millis);
		
		
		if(p.hasPermission("bandatabase.addban")) {
			
			try {
				
				jugador = args[0].toString();
				razon = args[1].toString();
				
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Sintáxis inválida, por favor use: " + plugin.getCommand("addban").getUsage());
				return true;
				
			}
			
			String ban = "tempban " + jugador + " " + duracion + " " + razon;
			//conexión con la base de datos
			try {
				
				Connection bbdd = DriverManager.getConnection("jdbc:mysql://198.245.51.96:3306/db_56351", "db_56351", "13f94d20f2");
				
				Statement statement = bbdd.createStatement();
				
				//comandos sql
				ResultSet resultset = statement.executeQuery("SELECT * FROM BansPlugin WHERE JUGADOR='"+jugador+"'");
				
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
				
				statement.executeUpdate("INSERT INTO `BansPlugin`(`JUGADOR`, `RAZON`, `DURACION`, `DATEADDED`) VALUES ('"+jugador+"','"+razon+"','"+duracion+"','"+dateSQL+"')");
				
				
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
