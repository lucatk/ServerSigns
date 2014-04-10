package de.derluuc.serversigns.data;

public class ServerData {

	private String server;
	private String motd;
	private int onlinePlayers = 0;
	private int maxPlayers = 20;
	
	public ServerData(String server, String motd, int online, int max) {
		this.server = server;
		this.motd = motd;
		this.onlinePlayers = online;
		this.maxPlayers = max;
	}
	
	public String getServer() {
		return server;
	}
	
	public String getMotd() {
		return motd;
	}
	
	public int getOnlinePlayers() {
		return this.onlinePlayers;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
}
