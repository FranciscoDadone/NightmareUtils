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
import me.dadogamer13.nightmareutils.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class RemoveBanCommand implements CommandExecutor{

	private Main plugin;
	
	public RemoveBanCommand(Main plugin) {
		
		this.plugin = plugin;
		plugin.getCommand("unban").setExecutor(this);;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		Player p = null;
		try {
			p = (Player) sender;
		} catch(Exception e) {
			System.out.println(Utils.chat("&cSolo los usuarios pueden ejecutar este comando! Si lo necesitas ejecutar de urgencia usa /pardon, pero luego en el servidor como usuario ejecutá apropiadamente el /unban para eliminar al jugador de la base de datos."));
			return true;
		}
		
		String jugador;
		
		if(p.hasPermission("Nightmare.unban")) {
			
			try {
				
				jugador = args[0].toString();
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Sintáxis inválida, por favor use: /unban jugador");
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
				
				p.sendMessage(Utils.chat(plugin.getConfig().getString("server_name") + " &f> &7" + jugador + " &7ha sido desbaneado."));
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Error al conectar con la base de datos :(");
				e.printStackTrace();
				
			}
			
		}
		
		return false;
	}
	
}
