package data;

import java.awt.Color;

public class Cell {
	private int x;
	private int y;
	private CellType cellType;
	
	public Cell(int x, int y, CellType cellType) {
		this.x = x;
		this.y = y;
		this.cellType = cellType;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Color getColor() {
		return getColor(getHexColor());
	}
	
	public String getHexColor() {
		return getHexColor(this.cellType);
	}
	
	public static String getHexColor(CellType cellType) {
		switch(cellType) {
			case EMPTY:
				return "#fffffc";
			case OPEN:
				return "#66ff77";
			case CLOSE:
				return "#ff66bb";
			case STARTING_NODE:
				return "#66ccff";
			case TARGET_NODE:
				return "#66ccff";
			case WALL:
				return "#262728";
			case PATH:
				return "#f5f562";
			default:
				break;
		}
		return "#fffffc"; // EMPTY Color
	}
	
	public static Color getColor(String hex) {
		return Color.decode(hex);
	}
	
	public void changeType(CellType cellType) {
		this.cellType = cellType;
	}
}
