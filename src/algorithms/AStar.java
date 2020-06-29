package algorithms;

import java.util.List;
import java.util.Map;

import data.Cell;
import data.CellType;

public class AStar {
	private Cell start;
	private Cell target;
	private List<Cell> openCells;
	
	public AStar(Map<String, Cell> cells) {
		for (Cell cell : cells.values()) {
			if (cell.getCellType() == CellType.STARTING_NODE) start = cell;
			if (cell.getCellType() == CellType.TARGET_NODE) target = cell;
		}
		
		start.setGCost(0);
		start.setHCost(target);
		
		target.setGCost(Double.POSITIVE_INFINITY);
		target.setHCost(target);
		
		List<Cell> currentNeighbors = start.getNeighbors();
		
		for (Cell neighbor : currentNeighbors) {
			if (neighbor.getCellType() == CellType.EMPTY) {
				neighbor.changeType(CellType.OPEN);
			}
		}
	}
}
