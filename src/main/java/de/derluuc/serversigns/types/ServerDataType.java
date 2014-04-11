package de.derluuc.serversigns.types;

public enum ServerDataType {

	IP("ip"),
	PORT("port");
	
	String node;
	
	ServerDataType(String node) {
		this.node = node;
	}
	
	public String node() {
		return this.node;
	}
	
}