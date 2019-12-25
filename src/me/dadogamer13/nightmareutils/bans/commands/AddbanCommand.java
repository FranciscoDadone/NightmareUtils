package me.dadogamer13.nightmareutils.bans.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dadogamer13.nightmareutils.main.Main;
import me.dadogamer13.nightmareutils.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class AddbanCommand implements CommandExecutor {

	private Main plugin;
	
	public AddbanCommand(Main plugin) {
		
		this.plugin = plugin;
		plugin.getCommand("ban").setExecutor(this);
		plugin.getCommand("tempban").setExecutor(this);
		
	}
	
	public void tempban(String jugador, String razon, Player p) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		int year = localDate.getYear();
		
		if((day + Integer.parseInt(plugin.getConfig().getString("first_tempban"))) > localDate.lengthOfMonth()) {
			month++;
			int m = localDate.lengthOfMonth() - (day + Integer.parseInt(plugin.getConfig().getString("first_tempban")));
			day = m - Integer.parseInt(plugin.getConfig().getString("first_tempban"));
		} else {
			day = day + Integer.parseInt(plugin.getConfig().getString("first_tempban"));
		}
		
		Bukkit.getBanList(Type.NAME).addBan(jugador, razon, sdf.parse(day + "/" + month + "/" + year), p.getName());
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player p = null;
		try {
			p = (Player) sender;
		} catch(Exception e) {
			System.out.println(Utils.chat("&cSolo los usuarios pueden ejecutar este comando!"));
			return true;
		}
		
		String jugador, duracion = plugin.getConfig().getString("first_tempban"), razon = "";
		boolean silent = false, tempban = false;
		
		long millis=System.currentTimeMillis();
		java.sql.Date dateSQL=new java.sql.Date(millis);
		
		
		if(p.hasPermission("Nightmare.ban") && p instanceof Player) {
			
			try {
				
				jugador = args[0].toString();
				
				for(int i = 1; i < args.length; i++) {
					
					if(args[i].equals("-s")) {
							
						silent = true;
						break;
					}
					
					razon += args[i] + " ";
					
				}
				
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Sintáxis inválida, por favor use: " + plugin.getCommand("ban").getUsage());
				return true;
				
			}
			
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
						p.sendMessage(Utils.chat(plugin.getConfig().getString("server_name") + " &f> &7Este fue el segundo ban del jugador " + jugador + "&7, por lo tanto fue baneado &cpermanentemente&7."));
						
						Bukkit.getBanList(Type.NAME).addBan(jugador, razon, null, p.getName());
						
						
					} else {
						
						tempban(jugador, razon, p);
						tempban = true;
						
					}
					
				} catch(Exception e) {
					tempban(jugador, razon, p);
					tempban = true;
				}
				
				String update = "INSERT INTO `" + plugin.getConfig().getString("database_table_name_bans") + "`(`JUGADOR`, `RAZON`, `DURACION`, `DATEADDED`) VALUES ('"+jugador+"','"+razon+"','"+duracion+"','"+dateSQL+"')";
				statement.executeUpdate(update);
				
				
			} catch(Exception e) {
				
				p.sendMessage(ChatColor.RED + "Error al conectar con la base de datos :(");
				e.printStackTrace();
				
			}
			
			
			p.sendMessage(Utils.chat(plugin.getConfig().getString("server_name") + " &f> &7Baneo añadido a la base de datos. (ign:" + jugador + "&7, t:" + duracion + "&7, r:" + razon + "&7)"));
			
			Player p2 = Bukkit.getServer().getPlayer(jugador);
			p2.kickPlayer(Utils.chat("&cFuiste baneado de " + plugin.getConfig().getString("server_name") + " &r&cpor: " + razon + " &c&l("+ duracion +"  días)"));
			
			if(silent) {
				
				if(tempban) {
					
					Bukkit.broadcast(Utils.chat(plugin.getConfig().getString("server_name") + " &f> &7(Silencioso) " + p.getName() + " &7baneó temporalmente a " + jugador + " &7por " + razon), "Nightmare.see");
					
				} else {
					
					Bukkit.broadcast(Utils.chat(plugin.getConfig().getString("server_name") + " &f> &7(Silencioso) " + p.getName() + " &7baneó permanentemente a " + jugador + " &7por " + razon), "Nightmare.see");
					
				}
				
				
			} else {
				
				if(tempban) {
					
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("server_name") + " &f> &7" + p.getName() + " &7baneó temporalmente a " + jugador + " &7por " + razon));
					
				} else {
					
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("server_name") + " &f> &7" + p.getName() + " &7baneó permanentemente a " + jugador + " &7por " + razon));
					
				}
				
			}
			
			return true;
			
		} else {
			p.sendMessage(Utils.chat("&cNo tenes permiso para ejecutar este comando."));
		}
		
		return false;
		
	}
	
}
