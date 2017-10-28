package data;

public enum TileType {
	
	Land("Land", true),
	Road("Road", false),
	NULL("Land", false);
	
	String tileName;
	boolean buildable;
	
	TileType(String name, boolean build) {
		this.tileName = name;
		buildable = build;
	}
}
