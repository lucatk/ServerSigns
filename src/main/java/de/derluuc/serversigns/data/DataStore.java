package de.derluuc.serversigns.data;

import java.util.ArrayList;
import java.util.List;

import de.derluuc.serversigns.ServerSigns;
import de.derluuc.serversigns.signs.ServerSign;
import de.derluuc.serversigns.signs.SignUtils;
import de.derluuc.serversigns.types.LayoutDataType;
import de.derluuc.serversigns.types.ServerDataType;

public class DataStore {
	
	private ServerSigns pl;
	private static DataStore instance;

	public static String NODE_SIGNLIST = "serversigns.signsdata";

	public static String NODE_SERVERS = "serversigns.servers.";
	public static String NODE_SERVER(String server) { return NODE_SERVERS + server; }
	public static String NODE_SERVERS_DATA(String server, ServerDataType type) { return NODE_SERVER(server) + "." + type.node(); }

	public static String NODE_LAYOUTS = "serversigns.layouts.";
	public static String NODE_LAYOUT(String layout) { return NODE_LAYOUTS + layout; }
	public static String NODE_LAYOUT_DATA(String layout, LayoutDataType type) { return NODE_LAYOUT(layout) + "." + type.node(); }

	public static String NODE_CYCLE_UPDATE = "serversigns.cycleupdate";
	public static String NODE_LANGUAGE = "serversigns.language";
	
	public DataStore() {
		this.pl = ServerSigns.getInstance();
		
		
		ArrayList<ServerSign> signdata = new ArrayList<ServerSign>();
		signdata.add(new ServerSign("exampleserver", "examplelayout", "world", 0, 0, 0));
		this.pl.getConfig().addDefault(NODE_SIGNLIST, SignUtils.serialize(signdata));
		
		this.pl.getConfig().addDefault(NODE_LAYOUT_DATA("examplelayout", LayoutDataType.LINE1), "line1");
		this.pl.getConfig().addDefault(NODE_LAYOUT_DATA("examplelayout", LayoutDataType.LINE2), "line2");
		this.pl.getConfig().addDefault(NODE_LAYOUT_DATA("examplelayout", LayoutDataType.LINE3), "line3");
		this.pl.getConfig().addDefault(NODE_LAYOUT_DATA("examplelayout", LayoutDataType.LINE4), "line4");

		this.pl.getConfig().addDefault(NODE_SERVERS_DATA("exampleserver", ServerDataType.IP), "localhost");
		this.pl.getConfig().addDefault(NODE_SERVERS_DATA("exampleserver", ServerDataType.PORT), 25565);
		
		this.pl.getConfig().addDefault(NODE_CYCLE_UPDATE, 20L);
		
		this.pl.getConfig().options().copyDefaults(true);
		this.pl.saveConfig();
		this.pl.saveDefaultConfig();
	}
	
	public void put(String node, Object o) {
		this.pl.getConfig().set(node, o);
	}
	
	public static DataStore getInstance() {
		if(instance == null) {
			instance = new DataStore();
		}
		return instance;
	}

	public List<ServerSign> getSigns() {
		return SignUtils.deserialize(this.pl.getConfig().getStringList(NODE_SIGNLIST));
	}
	
	public void setSigns(List<ServerSign> signs) {
		this.pl.getConfig().set(NODE_SIGNLIST, SignUtils.serialize(signs));
		this.pl.saveConfig();
		this.pl.reloadConfig();
		ServerSigns.getInstance().reloadSigns();
	}
	
	public String[] getLayout(String layout) {
		return new String[] { this.pl.getConfig().getString(NODE_LAYOUT_DATA(layout, LayoutDataType.LINE1)), this.pl.getConfig().getString(NODE_LAYOUT_DATA(layout, LayoutDataType.LINE2)), this.pl.getConfig().getString(NODE_LAYOUT_DATA(layout, LayoutDataType.LINE3)), this.pl.getConfig().getString(NODE_LAYOUT_DATA(layout, LayoutDataType.LINE4)) };
	}
	
	public boolean existsLayout(String layout) {
		return this.pl.getConfig().contains(NODE_LAYOUT(layout));
	}

	public String getServerIp(String server) {
		return this.pl.getConfig().getString(NODE_SERVERS_DATA(server, ServerDataType.IP));
	}

	public int getServerPort(String server) {
		return this.pl.getConfig().getInt(NODE_SERVERS_DATA(server, ServerDataType.PORT));
	}
	
	public boolean existsServer(String server) {
		return this.pl.getConfig().contains(NODE_SERVER(server));
	}
	
	public long getSignUpdateCycle() {
		return this.pl.getConfig().getLong(NODE_CYCLE_UPDATE);
	}
	
	public String getLanguageId() {
		return this.pl.getConfig().getString(NODE_LANGUAGE);
	}
	
	public void setLanguageId(String id) {
		this.pl.getConfig().set(NODE_LANGUAGE, id);
		this.pl.reloadConfig();
	}
	
}
