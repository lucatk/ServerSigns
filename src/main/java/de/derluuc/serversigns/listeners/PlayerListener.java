package de.derluuc.serversigns.listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.derluuc.serversigns.ServerSigns;
import de.derluuc.serversigns.data.DataStore;
import de.derluuc.serversigns.signs.ServerSign;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.isCancelled())
			return;
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getClickedBlock().getType() != Material.SIGN && e.getClickedBlock().getType() != Material.SIGN_POST && e.getClickedBlock().getType() != Material.WALL_SIGN)
			return;
		for(ServerSign rs : ServerSigns.getInstance().getSigns()) {
			if(rs.getSignLocation().getBlockX() == e.getClickedBlock().getLocation().getBlockX()) {
				if(rs.getSignLocation().getBlockY() == e.getClickedBlock().getLocation().getBlockY()) {
					if(rs.getSignLocation().getBlockZ() == e.getClickedBlock().getLocation().getBlockZ()) {
						rs.join(e.getPlayer());
					}
				}
			}
		}
	}
	
}
