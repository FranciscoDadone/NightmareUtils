package me.dadogamer13.nightmareutils.color.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.dadogamer13.nightmareutils.utils.Utils;

public class UI {

	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 2 * 9;
	
	public static void initialize() {
		
		inventory_name = Utils.chat("&c&lChange nickname color");
		
		inv = Bukkit.createInventory(null, inv_rows);
		
	}
	
	public static Inventory GUI (Player p) {
		
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		
		Utils.createItem(inv, Material.RED_WOOL, 1, 1, "&4Rojo", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.RED_WOOL, 1, 2, "&cRojo claro", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.ORANGE_WOOL, 1, 3, "&6Naranja", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.YELLOW_WOOL, 1, 4, "&eAmarillo", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.GREEN_WOOL, 1, 5, "&2Verde", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.LIME_WOOL, 1, 6, "&aVerde claro", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.LIGHT_BLUE_WOOL, 1, 7, "&bCeleste", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.CYAN_WOOL, 1, 8, "&3Aqua", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.BLUE_WOOL, 1, 9, "&1Azul", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.BLUE_WOOL, 1, 10, "&9Azul claro", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.PINK_WOOL, 1, 11, "&dRosa", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.PURPLE_WOOL, 1, 12, "&5Violeta", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.WHITE_WOOL, 1, 13, "&fBlanco", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.LIGHT_GRAY_WOOL, 1, 14, "&7Gris claro", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.GRAY_WOOL, 1, 15, "&8Gris", "&5Click para cambiar el color del nick");
		Utils.createItem(inv, Material.BLACK_WOOL, 1, 16, "&0Negro", "&5Click para cambiar el color del nick");
		
		toReturn.setContents(inv.getContents());
		return toReturn;
		
	}
	
	public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv) {
		
		String jugador = p.getName();		
		String nick = "nick " + jugador + " &7" + jugador;
		
		if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&4Rojo"))) {
			
			nick = "nick " + jugador + " &4" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&cRojo claro"))) {
			
			nick = "nick " + jugador + " &c" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&6Naranja"))) {
					
			nick = "nick " + jugador + " &6" + jugador;
					
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&eAmarillo"))) {
			
			nick = "nick " + jugador + " &e" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&2Verde"))) {
			
			nick = "nick " + jugador + " &2" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&aVerde claro"))) {
			
			nick = "nick " + jugador + " &a" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&bCeleste"))) {
			
			nick = "nick " + jugador + " &b" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&3Aqua"))) {
			
			nick = "nick " + jugador + " &3" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&1Azul"))) {
			
			nick = "nick " + jugador + " &1" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&9Azul claro"))) {
			
			nick = "nick " + jugador + " &9" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&dRosa"))) {
			
			nick = "nick " + jugador + " &d" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&5Violeta"))) {
			
			nick = "nick " + jugador + " &5" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&fBlanco"))) {
			
			nick = "nick " + jugador + " &f" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&7Gris claro"))) {
			
			nick = "nick " + jugador + " &7" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&8Gris"))) {
			
			nick = "nick " + jugador + " &8" + jugador;
			
		}
		else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&0Negro"))) {
			
			nick = "nick " + jugador + " &0" + jugador;
			
		}
		
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), nick);
		
	}
	
}
