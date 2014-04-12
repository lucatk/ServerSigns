package de.derluuc.serversigns.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.bukkit.Bukkit;

import de.derluuc.serversigns.ServerSigns;
import de.derluuc.serversigns.data.DataStore;
import de.derluuc.serversigns.data.ServerData;
import de.derluuc.serversigns.signs.ServerSign;

public class DataHandler {
	
	private static DataHandler instance;
	private int threadid;
	
	private HashMap<String, ServerData> serverData = new HashMap<String, ServerData>();
	
	@SuppressWarnings("deprecation")
	public DataHandler() {
		threadid = Bukkit.getScheduler().scheduleAsyncRepeatingTask(ServerSigns.getInstance(), new Runnable() {

			public void run() {
				for(ServerSign rs : ServerSigns.getInstance().getSigns()) {
					String server = rs.getServer();
					String ip = DataStore.getInstance().getServerIp(server);
					int port = DataStore.getInstance().getServerPort(server);
					fetchData(server, ip, port);
				}
			}
			
		}, 0L, DataStore.getInstance().getSignUpdateCycle());
	}
	
	public static DataHandler getInstance() {
		if(instance == null)
			instance = new DataHandler();
		return instance;
	}
	
	public void disable() {
		Bukkit.getScheduler().cancelTask(threadid);
	}
	
	public ServerData getData(String server) {
		if(!serverData.containsKey(server))
			return new ServerData(server, "", 0, 0, true);
		return serverData.get(server);
	}
	
	public void fetchData(String name, String ip, int port) {
		ServerData sdat = null;
		/*try {
			Socket sock = new Socket();
			sock.connect(new InetSocketAddress(ip, port), 5000);
			 
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			DataInputStream in = new DataInputStream(sock.getInputStream());
			
			out.write(0xFE);
			 
			int b;
			StringBuffer str = new StringBuffer();
			while ((b = in.read()) != -1) {
				if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
					// Not sure what use the two characters are so I omit them
					str.append((char) b);
				}
			}
			sock.close();
			 
			String[] data = str.toString().split("§");
			if(data.length < 3)
				throw new IOException();
			String serverMotd = data[0];
			int onlinePlayers = Integer.parseInt(data[1]);
			int maxPlayers = Integer.parseInt(data[2]);
			sdat = new ServerData(name, serverMotd, onlinePlayers, maxPlayers, false);
		} catch (IOException e) {
			sdat = new ServerData(name, "", 0, 0, true);
		}*/
		
		
		try {
			Socket socket = new Socket();
			OutputStream outputStream;
			DataOutputStream dataOutputStream;
			InputStream inputStream;
			InputStreamReader inputStreamReader;

			socket.setSoTimeout(5000);

			socket.connect(new InetSocketAddress(
				ip,
				port
			), 5000);

			outputStream = socket.getOutputStream();
			dataOutputStream = new DataOutputStream(outputStream);

			inputStream = socket.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,Charset.forName("UTF-16BE"));

			dataOutputStream.write(new byte[]{
				(byte) 0xFE,
				(byte) 0x01
			});

			int packetId = inputStream.read();

			if (packetId == -1) {
				dataOutputStream.close();
				outputStream.close();

				inputStreamReader.close();
				inputStream.close();

				socket.close();
				throw new IOException("Premature end of stream.");
			}

			if (packetId != 0xFF) {
				dataOutputStream.close();
				outputStream.close();

				inputStreamReader.close();
				inputStream.close();

				socket.close();
				throw new IOException("Invalid packet ID (" + packetId + ").");
			}

			int length = inputStreamReader.read();

			if (length == -1) {
				dataOutputStream.close();
				outputStream.close();

				inputStreamReader.close();
				inputStream.close();

				socket.close();
				throw new IOException("Premature end of stream.");
			}

			if (length == 0) {
				dataOutputStream.close();
				outputStream.close();

				inputStreamReader.close();
				inputStream.close();

				socket.close();
				throw new IOException("Invalid string length.");
			}

			char[] chars = new char[length];

			if (inputStreamReader.read(chars,0,length) != length) {
				dataOutputStream.close();
				outputStream.close();

				inputStreamReader.close();
				inputStream.close();

				socket.close();
				throw new IOException("Premature end of stream.");
			}

			String string = new String(chars);
			
			String serverMotd = "OFFLINE";
			int onlinePlayers = 0;
			int maxPlayers = 0;

			if (string.startsWith("§")) {
				String[] data = string.split("\0");

				serverMotd = data[3];
				onlinePlayers = Integer.parseInt(data[4]);
				maxPlayers = Integer.parseInt(data[5]);
			} else {
				String[] data = string.split("§");

				serverMotd = data[0];
				onlinePlayers = Integer.parseInt(data[1]);
				maxPlayers = Integer.parseInt(data[2]);
			}

			dataOutputStream.close();
			outputStream.close();

			inputStreamReader.close();
			inputStream.close();

			socket.close();
			sdat = new ServerData(name, serverMotd, onlinePlayers, maxPlayers, false);
		} catch (SocketException exception) {
			sdat = new ServerData(name, "", 0, 0, true);
		} catch (IOException exception) {
			sdat = new ServerData(name, "", 0, 0, true);
		}
		
		if(sdat != null) {
			if(serverData.containsKey(sdat.getServer()))
				serverData.remove(sdat.getServer());
			serverData.put(sdat.getServer(), sdat);
		}
	}

}
