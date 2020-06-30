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
				if (i == 2 && j == 2) temp.changeType(CellType.STARTING_NODE);
				if (i == 12 && j == 13) temp.changeType(CellType.TARGET_NODE);
				cells.put(positionToKey(j, i), temp);
			}
		}
		return cells;
	}
	
	public static String positionToKey(int x, int y) {
		String _x = Integer.toString(x);
		String _y = Integer.toString(y);
		
		return _x.concat("-").concat(_y);
	}
	
	public Map<String, Cell> get() {
		return this.cells;
	}
}
