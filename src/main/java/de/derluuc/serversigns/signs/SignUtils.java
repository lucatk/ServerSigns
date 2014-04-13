package de.derluuc.serversigns.signs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import de.derluuc.serversigns.data.ServerData;

public class SignUtils {

	public static String replaceVars(String line, ServerData data) {
		if(data == null) {
			line = line.replace("$servername", "null");
			line = line.replace("$motd", ChatColor.RED + "OFFLINE");
			line = line.replace("$online", "-");
			line = line.replace("$max", "-");
			line = ChatColor.translateAlternateColorCodes("&".charAt(0), line);
			return line;
		}
		line = line.replace("$servername", data.getServer());
		if(data.isOffline()) {
			line = line.replace("$online", "-");
			line = line.replace("$max", "-");
			line = line.replace("$motd", ChatColor.RED + "OFFLINE");
		} else {
			line = line.replace("$online", Integer.toString(data.getOnlinePlayers()));
			line = line.replace("$max", Integer.toString(data.getMaxPlayers()));
			line = line.replace("$motd", data.getMotd());
		}
		line = ChatColor.translateAlternateColorCodes("&".charAt(0), line);
		return line;
	}
	
	public static List<String> serialize(List<ServerSign> signs) {
		List<String> signsList = new ArrayList<String>();
		for(ServerSign rs : signs) {
			signsList.add(rs.getServer() + ";" + rs.getLayout() + ";" + LocationSerialiser.locationEntityToString(rs.getSignLocation()));
		}
		
		return signsList;
	}
	
	public static List<ServerSign> deserialize(List<String> signsList) {
		List<ServerSign> signs = new ArrayList<ServerSign>();
		for(String s : signsList) {
			String[] splitted = s.split(";");
			String server = splitted[0];
			String layout = splitted[1];
			Location loc = LocationSerialiser.stringToLocationEntity(splitted[2]);
			signs.add(new ServerSign(server, layout, loc));
		}
		
		return signs;
	}
	
	private static class LocationSerialiser
	{
		//credits to Codebucket for this neat util :)
		
		public static String locationPlayerToString(Location loc)
		{
			String returnString = loc.getWorld() .getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
			return returnString;
		}
		
		public static Location stringToLocationPlayer(String loc)
		{
			String[] splited = loc.split(",");
			String world = splited[0];
			double x = Double.parseDouble(splited[1]);
			double y = Double.parseDouble(splited[2]);
			double z = Double.parseDouble(splited[3]);
			double ya = Double.parseDouble(splited[4]);
			double pi = Double.parseDouble(splited[5]);
			
			Location returnLocation = new Location(Bukkit.getWorld(world), x, y, z);
			returnLocation.setYaw((float)ya);
			returnLocation.setPitch((float)pi);
			
			return returnLocation;
		}
		
		public static String locationEntityToString(Location loc)
		{
			String returnString = loc.getWorld() .getName()+ "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
			return returnString;
		}
		
		public static Location stringToLocationEntity(String loc)
		{
			String[] splited = loc.split(",");
			String world = splited[0];
			double x = Double.parseDouble(splited[1]);
			double y = Double.parseDouble(splited[2]);
			double z = Double.parseDouble(splited[3]);
			
			Location returnLocation = new Location(Bukkit.getWorld(world), x, y, z);
			
			return returnLocation;
		}
	}
	
}
