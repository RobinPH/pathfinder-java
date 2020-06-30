package data;

import java.util.HashMap;
import java.util.Map;

import algorithms.Algorithms;
import visuals.PathfindingGUI;

public class Cells {
	private Map<String, Cell> cells;
	private int width;
	private int height;
	private int cellSize;
	
	public Cells(int width, int height) throws InterruptedException {
		this.width = width;
		this.height = height;
		
		this.cells = generateCells();
	}
	
	private Map<String, Cell> generateCells() {
		Map<String, Cell> cells = new HashMap<>();
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				Cell temp = new Cell(j, i, CellType.EMPTY);
				if (i == 0 && j == 0) temp.changeType(CellType.STARTING_NODE, false);
				if (i == 2 && j == 2) temp.changeType(CellType.TARGET_NODE, false);
				cells.put(positionToKey(j, i), temp);
			}
		}
		return cells;
	}
	
	public boolean changeStartingNode(int x, int y) {
		if (this.getCell(x, y).getCellType() == CellType.TARGET_NODE)
			return false;
		
		for (Cell cell : this.cells.values()) {
			CellType cellType = cell.getCellType();
			if (cellType != CellType.TARGET_NODE && cellType != CellType.WALL)
				cell.changeType(CellType.EMPTY, true);
			
			if (x == cell.getX() && y == cell.getY())
				cell.changeType(CellType.STARTING_NODE, false);
		}
		
		return true;
	}
	
	public boolean changeTargetNode(int x, int y) {
		if (this.getCell(x, y).getCellType() == CellType.STARTING_NODE)
			return false;
		
		for (Cell cell : this.cells.values()) {
			CellType cellType = cell.getCellType();
			
			if (cellType != CellType.STARTING_NODE && cellType != CellType.WALL)
				cell.changeType(CellType.EMPTY, true);
			
			if (x == cell.getX() && y == cell.getY())
				cell.changeType(CellType.TARGET_NODE, false);
		}
		
		return true;
	}
	
	public static String positionToKey(int x, int y) {
		String _x = Integer.toString(x);
		String _y = Integer.toString(y);
		
		return _x.concat("-").concat(_y);
	}
	
	public Map<String, Cell> get() {
		return this.cells;
	}
	
	public Cell getCell(int x, int y) {
		return cells.get(positionToKey(x, y));
	}
	
	public Cell getCell(String key) {
		return cells.get(key);
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}
