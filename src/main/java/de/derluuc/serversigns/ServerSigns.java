package de.derluuc.serversigns;

import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

import de.derluuc.serversigns.commands.SSignsCommand;
import de.derluuc.serversigns.communication.DataHandler;
import de.derluuc.serversigns.data.DataStore;
import de.derluuc.serversigns.data.ServerData;
import de.derluuc.serversigns.listeners.PlayerListener;
import de.derluuc.serversigns.listeners.SignListener;
import de.derluuc.serversigns.signs.ServerSign;

public class ServerSigns extends JavaPlugin {

	private static ServerSigns instance;
	private List<ServerSign> signs;
	
	private int signupdateid;
	
	@Override
	public void onEnable() {
		instance = this;
		DataStore.getInstance();
		signs = DataStore.getInstance().getSigns();
		DataHandler.getInstance();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getPluginManager().registerEvents(new SignListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		getCommand("serversigns").setExecutor(new SSignsCommand());
		
		scheduleSignUpdating();
		
		try {
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} catch (IOException e) {
		}
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTask(signupdateid);
		DataHandler.getInstance().disable();
	}
	
	public static ServerSigns getInstance() {
		return instance;
	}
	
	public void reloadSigns() {
		signs.clear();
		signs = DataStore.getInstance().getSigns();
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
