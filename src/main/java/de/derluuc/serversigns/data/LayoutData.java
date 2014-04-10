package de.derluuc.serversigns.data;

public enum LayoutData {

	LINE1("line1"),
	LINE2("line2"),
	LINE3("line3"),
	LINE4("line4");
	
	String node;
	
	LayoutData(String node) {
		this.node = node;
	}
	
	public String node() {
		return this.node;
	}
	
}
