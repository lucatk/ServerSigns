package de.derluuc.serversigns.listeners;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import de.derluuc.serversigns.ServerSigns;
import de.derluuc.serversigns.data.DataStore;
import de.derluuc.serversigns.signs.ServerSign;

public class SignListener implements Listener {

	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(e.isCancelled())
			return;
		if(e.getLine(0).equals("[serversign]")) {
			if(!p.isOp() && !p.hasPermission("serversigns.create"))
				return;
			String server = e.getLine(1);
			String layout = e.getLine(2);
			Location loc = e.getBlock().getLocation();
			if(!DataStore.getInstance().existsLayout(layout)) {
				p.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + "Layout nicht gefunden.");
				return;
			}
			List<ServerSign> signs = DataStore.getInstance().getSigns();
			signs.add(new ServerSign(server, layout, loc));
			DataStore.getInstance().setSigns(signs);
			ServerSigns.getInstance().reloadSigns();
			p.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + "Sign erstellt.");
		}
	}
	
	@EventHandler
	public void onSignDestroy(BlockBreakEvent e) {
		if(e.isCancelled())
			return;
		Player p = e.getPlayer();
		for(ServerSign rs : ServerSigns.getInstance().getSigns()) {
			if(rs.getSignLocation().getBlockX() == e.getBlock().getLocation().getBlockX()) {
				if(rs.getSignLocation().getBlockY() == e.getBlock().getLocation().getBlockY()) {
					if(rs.getSignLocation().getBlockZ() == e.getBlock().getLocation().getBlockZ()) {
						List<ServerSign> signs = DataStore.getInstance().getSigns();
						int index = 0;
						for(int i = 0; i < signs.size(); i++) {
							ServerSign sign = signs.get(i);
							if(sign.equals(rs)) {
								index = i;
								break;
							}
						}
						signs.remove(index);
						DataStore.getInstance().setSigns(signs);
						ServerSigns.getInstance().reloadSigns();
						p.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + "Sign zerstört.");
						break;
					}
				}
			}
		}
	}
	
}
