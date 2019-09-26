package me.dadogamer13.nightmareutils.color.ui.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.dadogamer13.nightmareutils.color.ui.UI;
import me.dadogamer13.nightmareutils.main.Main;

public class InventoryClickListener implements Listener {
	
	private Main plugin;
	public InventoryClickListener(Main plugin) {
		
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		String title = e.getInventory().getTitle();
		
		if(title.equals(UI.inventory_name)) {
			
			e.setCancelled(true);
			
			if(e.getCurrentItem() == null) {
				
				return;
				
			}
			if(title.equals(UI.inventory_name)) {
				
				UI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory());
				
			}
			
		}
		
	}
	
}
