package de.derluuc.serversigns;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.derluuc.serversigns.commands.SSignsCommand;
import de.derluuc.serversigns.communication.DataHandler;
import de.derluuc.serversigns.data.DataStore;
import de.derluuc.serversigns.data.ServerData;
import de.derluuc.serversigns.lang.LanguageManager;
import de.derluuc.serversigns.listeners.PlayerListener;
import de.derluuc.serversigns.listeners.SignListener;
import de.derluuc.serversigns.signs.ServerSign;

public class ServerSigns extends JavaPlugin {

	private static ServerSigns instance;
	private static LanguageManager langManager;
	private List<ServerSign> signs;
	
	private int signupdateid;
	
	@Override
	public void onEnable() {
		instance = this;
		
		getLogger().log(Level.INFO, "Loading ServerSigns signs and languages...");
		
		DataStore.getInstance();
		signs = DataStore.getInstance().getSigns();
		DataHandler.getInstance();
		
		saveResource("en.yml", true);
		saveResource("de.yml", true);
		langManager = new LanguageManager(DataStore.getInstance().getLanguageId());

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getPluginManager().registerEvents(new SignListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		getCommand("serversigns").setExecutor(new SSignsCommand());
		
		scheduleSignUpdating();
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTask(signupdateid);
		DataHandler.getInstance().disable();
	}
	
	public static ServerSigns getInstance() {
		return instance;
	}
	
	public static LanguageManager getLangManager() {
		return langManager;
	}
	
	public void reloadSigns() {
		signs.clear();
		signs = DataStore.getInstance().getSigns();
	}
	
	public void reloadLang() {
		saveResource("en.yml", true);
		saveResource("de.yml", true);
		langManager = new LanguageManager(DataStore.getInstance().getLanguageId());
	}
	
	public List<ServerSign> getSigns() {
		return signs;
	}
	
	public void scheduleSignUpdating() {
		signupdateid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			public void run() {
				for(ServerSign rs : signs) {
					rs.update();
				}
			}
			
		}, 0L, DataStore.getInstance().getSignUpdateCycle());
	}
	
}
