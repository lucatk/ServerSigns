package de.derluuc.serversigns.signs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import de.derluuc.serversigns.communication.CommunicationHandler;
import de.derluuc.serversigns.data.ServerData;
import de.derluuc.serversigns.data.ServerData.GameState;

public class SignUtils {

	public static String replaceVars(String line, String server) {
		ServerData data = CommunicationHandler.getInstance().getServerData(server);
		if(data == null) {
			line = line.replaceAll("&servername", server);
			line = line.replaceAll("&state", ChatColor.RED + "• • • • • •");
			line = line.replaceAll("&online", "-");
			line = line.replaceAll("&max", "-");
			line = ChatColor.translateAlternateColorCodes("&".charAt(0), line);
			return line;
		}
		line = line.replaceAll("&servername", data.getServer());
		if(data.getGameState().equals(GameState.WAITING)) {
			line = line.replaceAll("&state", ChatColor.GREEN + "• • • • • •");
		} else if(data.getGameState().equals(GameState.RUNNING)) {
			line = line.replaceAll("&state", ChatColor.GOLD + "• • • • • •");
		} else {
			line = line.replaceAll("&state", ChatColor.RED + "• • • • • •");
		}
		if(data.getGameState().equals(GameState.RESTARTING)) {
			line = line.replaceAll("&online", "-");
			line = line.replaceAll("&max", "-");
		} else {
			line = line.replaceAll("&online", Integer.toString(data.getOnlinePlayers()));
			line = line.replaceAll("&max", Integer.toString(data.getMaxPlayers()));
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
		//credits to Yonas for this neat util :)
		
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
