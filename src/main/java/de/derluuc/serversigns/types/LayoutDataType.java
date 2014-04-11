package de.derluuc.serversigns.types;

public enum LayoutDataType {

	LINE1("line1"),
	LINE2("line2"),
	LINE3("line3"),
	LINE4("line4");
	
	String node;
	
	LayoutDataType(String node) {
		this.node = node;
	}
	
	public String node() {
		return this.node;
	}
	
}
