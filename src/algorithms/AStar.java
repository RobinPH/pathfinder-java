package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.Cell;
import data.CellType;

public class AStar {
	private Map<String, Cell> cells;
	private Cell start;
	private Cell target;
	private List<Cell> openCells = new ArrayList<Cell>();
	private List<Cell> closedCells = new ArrayList<Cell>();
	private Boolean found = false;
	
	public AStar(Map<String, Cell> cells) {
		this.cells = cells;
		
		for (Cell cell : this.cells.values()) {
			if (cell.getCellType() == CellType.STARTING_NODE) start = cell;
			if (cell.getCellType() == CellType.TARGET_NODE) target = cell;
		}
		
		start.setGCost(0);
		start.setHCost(target);
		target.setGCost(Double.POSITIVE_INFINITY);
		target.setHCost(target);
		
		openCells.add(start);
		closedCells.add(target);
	}
	
	public void pathFind() {
		while (!found) {
			Cell checkingCell = getLowestFScore();

			List<Cell> currentNeighbors = checkingCell.getNeighbors(target);
			
			for (Cell neighbor : currentNeighbors) {
				if (neighbor.getCellType() == CellType.TARGET_NODE) {
					Cell parent = neighbor.getParent();
					found = true;
					while (parent.getCellType() != CellType.STARTING_NODE) {
						parent.changeType(CellType.PATH);
						parent = parent.getParent();
					}
					break;
//					while (backtracking.getParent().getCellType() != CellType.STARTING_NODE) {
//						backtracking.changeType(CellType.PATH);
//						backtracking = backtracking.getParent();
//					}
				}
				if (neighbor.getCellType() == CellType.EMPTY) {
					neighbor.changeType(CellType.OPEN);
					openCells.add(neighbor);
				}
			}
		}
	}
	
	public Cell getLowestFScore() {
		Cell lowest = null;
		for (Cell cell : this.openCells) {
			if (lowest == null) {
				lowest = cell;
				continue;
			}
			
			if (cell.getFCost() < lowest.getFCost()) {
				lowest = cell;
			}
			if (cell.getFCost() == lowest.getFCost()) {
				if (cell.getHCost() < lowest.getHCost()) {
					lowest = cell;
				}
			}
		}
		
		this.openCells.remove(lowest);
		this.closedCells.add(lowest);
		lowest.changeType(CellType.CLOSE);
		return lowest;
	}
}
