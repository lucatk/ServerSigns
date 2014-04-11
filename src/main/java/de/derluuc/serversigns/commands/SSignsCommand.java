package de.derluuc.serversigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.derluuc.serversigns.ServerSigns;

public class SSignsCommand implements CommandExecutor {

	public boolean onCommand(CommandSender cs, Command cmd, String lbl, String[] args) {
		if(args.length == 0) {
			cs.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + "Plugin version: " + ChatColor.DARK_AQUA + ServerSigns.getInstance().getDescription().getVersion());
			cs.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + "Coded by DerLuuc. Web: derluuc.de Email: dev@derluuc.de");
		} else {
			if(args[0].equalsIgnoreCase("reload")) {
				if(!cs.isOp() && !cs.hasPermission("serversigns.cmd.update")) {
					cs.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + "You don't have permission to perform this command!");
					return true;
				}
				ServerSigns.getInstance().reloadConfig();
				cs.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + "Successfully reloaded config.");
			}
		}
		return true;
	}

}
