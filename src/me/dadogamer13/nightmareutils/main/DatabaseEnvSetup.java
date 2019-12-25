package me.dadogamer13.nightmareutils.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import net.md_5.bungee.api.ChatColor;

public class DatabaseEnvSetup {
	
	private Main plugin;
	
	public DatabaseEnvSetup(Main plugin) {

		this.plugin = plugin;
		
		try {
			
			String conextion_host = "jdbc:mysql://" + plugin.getConfig().getString("database_host") + ":" + plugin.getConfig().getString("database_port") + "/" + plugin.getConfig().getString("database_username");
			Connection bbdd = DriverManager.getConnection(conextion_host, plugin.getConfig().getString("database_username"), plugin.getConfig().getString("database_password"));
			Statement statement = bbdd.createStatement();
			
			//Bans database
			String table = "CREATE TABLE " + plugin.getConfig().getString("database_table_name_bans") + " (JUGADOR VARCHAR(16), RAZON varchar(1000), DURACION VARCHAR(10), DATEADDED DATE)";
			statement.executeUpdate(table);
			
		} catch(Exception e) {
			
			System.out.println(ChatColor.RED + "BansBD: Base de datos ya creada / error al crearla: BansBD");
			
		}
		
		try {
			
			String conextion_host = "jdbc:mysql://" + plugin.getConfig().getString("database_host") + ":" + plugin.getConfig().getString("database_port") + "/" + plugin.getConfig().getString("database_username");
			Connection bbdd = DriverManager.getConnection(conextion_host, plugin.getConfig().getString("database_username"), plugin.getConfig().getString("database_password"));
			Statement statement = bbdd.createStatement();
			
			truncateTable();

			//Online players database
			String tableOnline = "CREATE TABLE " + plugin.getConfig().getString("database_table_name_join_quit") + " (JUGADOR VARCHAR(16))";
			statement.executeUpdate(tableOnline);
						
		} catch(Exception e) {
			
			System.out.println(ChatColor.RED + "BansBD: Base de datos ya creada / error al crearla: Online players");
			
		}
		
		
	}
	
	private void truncateTable() {
		
		try {
			
			String conextion_host = "jdbc:mysql://" + plugin.getConfig().getString("database_host") + ":" + plugin.getConfig().getString("database_port") + "/" + plugin.getConfig().getString("database_username");
			Connection bbdd = DriverManager.getConnection(conextion_host, plugin.getConfig().getString("database_username"), plugin.getConfig().getString("database_password"));
			Statement statement = bbdd.createStatement();
			
			//Truncate table
			String truncate = "TRUNCATE TABLE " + plugin.getConfig().getString("database_table_name_join_quit");
			statement.executeUpdate(truncate);
			
		} catch(Exception e) {
			
			System.out.println(ChatColor.RED + "Error al truncar la lista de jugadores online.");
			
		}
		
	}
	
}
