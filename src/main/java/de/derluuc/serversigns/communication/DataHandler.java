package de.derluuc.serversigns.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import de.derluuc.serversigns.data.DataStore;
import de.derluuc.serversigns.data.ServerData;

public class DataHandler {
	
	private static DataHandler instance;
	
	public static DataHandler getInstance() {
		if(instance == null)
			instance = new DataHandler();
		return instance;
	}
	
	public ServerData getData(String server) {
		return getData(server, DataStore.getInstance().getServerIp(server), DataStore.getInstance().getServerPort(server));
	}
	
	public ServerData getData(String name, String ip, int port) {
		try {
			Socket sock = new Socket(ip, port);
			 
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			DataInputStream in = new DataInputStream(sock.getInputStream());
			 
			out.write(0xFE);
			 
			int b;
			StringBuffer str = new StringBuffer();
			while ((b = in.read()) != -1) {
				if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
					// Not sure what use the two characters are so I omit them
					str.append((char) b);
					//System.out.println(b + ":" + ((char) b));
				}
			}
			sock.close();
			 
			String[] data = str.toString().split("§");
			if(data.length < 3)
				return new ServerData(name, "", 0, 0, true);
			String serverMotd = data[0];
			int onlinePlayers = Integer.parseInt(data[1]);
			int maxPlayers = Integer.parseInt(data[2]);
			return new ServerData(name, serverMotd, onlinePlayers, maxPlayers, false);
		} catch (UnknownHostException e) {
			return new ServerData(name, "", 0, 0, true);
		} catch (IOException e) {
			return new ServerData(name, "", 0, 0, true);
		}
	}

}
