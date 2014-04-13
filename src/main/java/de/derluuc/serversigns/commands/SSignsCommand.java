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
			cs.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + "Coded by DerLuuc. Email: dev@derluuc.de");
			cs.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + "Credits to: YourSky (MultiMap), Codebucket (LocationSerialiser).");
		} else {
			if(args[0].equalsIgnoreCase("reload")) {
				if(!cs.isOp() && !cs.hasPermission("serversigns.cmd.reload")) {
					cs.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + ServerSigns.getLangManager().getLocaleMessage("lang.global.nopermission"));
					return true;
				}
				ServerSigns.getInstance().reloadConfig();
				ServerSigns.getInstance().reloadSigns();
				ServerSigns.getInstance().reloadLang();
				cs.sendMessage(ChatColor.GREEN + "[ServerSigns] " + ChatColor.GOLD + ServerSigns.getLangManager().getLocaleMessage("lang.cmd.reloaded"));
			}
		}
		return true;
	}

}
