package data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.foo;

public class Cell {
	private int x;
	private int y;
	private CellType cellType;
	private double gCost;
	private double hCost;
	private List<Cell> neighbors;
	
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
	
	public void setGCost(double gCost) {
		this.gCost = gCost;
	}
	
	public void setHCost(Cell targetNode) {
		this.hCost = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	public double getGCost() {
		return this.gCost;
	}
	
	public double getHCost() {
		return this.hCost;
	}
	
	public double getFCost() {
		return this.gCost + this.hCost;
	}
	
	public Color getColor() {
		return getColor(getHexColor());
	}
	
	public List<Cell> getNeighbors() {
		List<Cell> neighbors = new ArrayList<Cell>();
		
		for (int i = this.y - 1; i <= this.y + 1; i++) {
			for (int j = this.x - 1; j <= this.x + 1; j++) {
				if (i == this.y && j == this.x) continue;
				neighbors.add(foo.cells.get(foo.positionToKey(j, i)));
			}
		}
		
		this.neighbors = neighbors;
		return this.neighbors;
	}
	
	public CellType getCellType() {
		return this.cellType;
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
		if (this.cellType == CellType.STARTING_NODE) return;
		if (this.cellType == CellType.TARGET_NODE) return;
		
		this.cellType = cellType;
	}
}
