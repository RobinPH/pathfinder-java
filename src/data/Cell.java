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
	private Cell parent;
	
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
		this.hCost = Math.hypot(targetNode.x - 1 - this.x, targetNode.y - 1 - this.y);
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
	
	public List<Cell> getNeighbors(Cell targetNode) {
		List<Cell> neighbors = new ArrayList<Cell>();
		
		for (int i = this.y - 1; i <= this.y + 1; i++) {
			for (int j = this.x - 1; j <= this.x + 1; j++) {
				if (i == this.y && j == this.x) continue;
				if (i < 0 || i >= 10) continue; // 10 NEEDS CHANGE
				if (j < 0 || j >= 10) continue; // 10 NEEDS CHANGE
				Cell neighbor = foo.cells.get(foo.positionToKey(j, i));
				if (neighbor.getCellType() == CellType.WALL) continue;
				
				neighbors.add(neighbor);
				
				double newGCost = this.getGCost() + Math.hypot(this.x - neighbor.getX(), this.y - neighbor.getY());
				
				if (neighbor.getParent() == null) {
					neighbor.setParent(this);
					neighbor.setHCost(targetNode);
				} else {
					double gScore = neighbor.getGCost();
					double hScore = neighbor.getHCost();
					double fScore = neighbor.getFCost();
					if (fScore > hScore + this.getGCost()) {
						neighbor.setParent(this);
					} else if (fScore == hScore + this.getGCost()) {
						if (gScore > newGCost) {
							neighbor.setParent(this);
						}
					}
				}
			}
		}
		
		this.neighbors = neighbors;
		return this.neighbors;
	}
	
	public void setParent(Cell parent) {
		double newGCost = parent.getGCost() + Math.hypot(parent.getX() - this.getX(), parent.getY() - this.getY());
		
		this.setGCost(newGCost);
		this.parent = parent;
	}
	
	public Cell getParent() {
		return this.parent;
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
		if (cellType == CellType.WALL ) {
			this.setGCost(0);
			this.hCost = Double.MAX_VALUE;
		}
		this.cellType = cellType;
	}
}
