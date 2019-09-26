package me.dadogamer13.webconnect.com.netfily.nightmarefactions.PlayerList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.dadogamer13.nightmareutils.main.Main;

public class OnlinePlayers implements Listener {
	
	private Main plugin;
	
	public OnlinePlayers(Main plugin) {
		
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {		
		
		try {
			
			String conextion_host = "jdbc:mysql://" + plugin.getConfig().getString("database_host") + ":" + plugin.getConfig().getString("database_port") + "/" + plugin.getConfig().getString("database_username");
			Connection bbdd = DriverManager.getConnection(conextion_host, plugin.getConfig().getString("database_username"), plugin.getConfig().getString("database_password"));
			Statement statement = bbdd.createStatement();
			
			String update = "INSERT INTO `" + plugin.getConfig().getString("database_table_name_join_quit") + "`(`JUGADOR`) VALUES ('"+e.getPlayer().getName()+"')";
			statement.executeUpdate(update);
			
		} catch(Exception e1) {
			
			e1.printStackTrace();
			
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		try {
			
			String conextion_host = "jdbc:mysql://" + plugin.getConfig().getString("database_host") + ":" + plugin.getConfig().getString("database_port") + "/" + plugin.getConfig().getString("database_username");
			Connection bbdd = DriverManager.getConnection(conextion_host, plugin.getConfig().getString("database_username"), plugin.getConfig().getString("database_password"));
			Statement statement = bbdd.createStatement();
			
			String update = "DELETE FROM `" + plugin.getConfig().getString("database_table_name_join_quit") + "` WHERE JUGADOR='" + e.getPlayer().getName() + "'";
			statement.executeUpdate(update);
			
		} catch(Exception e1) {
			
			e1.printStackTrace();
			
		}
		
	}
	
}
