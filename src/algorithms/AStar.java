package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.Cell;
import data.CellType;
import main.foo;

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
		start.setHCost(Math.hypot(target.getX() - start.getX(), target.getY() - start.getY()));
		target.setGCost(Double.POSITIVE_INFINITY);
		target.setHCost(0);
		
		openCells.add(start);
		closedCells.add(target);
	}
	
	public void pathFind() {
		while (!found) {
			Cell checkingCell = getLowestFScore();

			List<Cell> currentNeighbors = checkingCell.getNeighbors(target);
			
			for (Cell neighbor : currentNeighbors) {
				double newGCost = checkingCell.getGCost() + Math.hypot(checkingCell.getX() - neighbor.getX(), checkingCell.getY() - neighbor.getY());
				double newHCost = foo.allowedDiagonals ? Math.hypot(target.getX() - neighbor.getX(), target.getY() - neighbor.getY()) : Math.abs(checkingCell.getX() - neighbor.getX()) + Math.abs(checkingCell.getY() - neighbor.getY()) - 1;
				if (neighbor.getParent() == null) {
					neighbor.setParent(checkingCell);
					neighbor.setHCost(newHCost);
					neighbor.setGCost(newGCost);
				} else {
					double gCost = neighbor.getGCost();
					double hCost = neighbor.getHCost();
					double fCost = neighbor.getFCost();
					if (fCost > hCost + checkingCell.getGCost()) {
						neighbor.setParent(checkingCell);
						neighbor.setGCost(newGCost);
						
					} else if (fCost == hCost + checkingCell.getGCost()) {
						if (gCost > newGCost)
						{
							neighbor.setParent(checkingCell);
							neighbor.setGCost(newGCost);
						}
					}
				}
				
				if (neighbor.getCellType() == CellType.TARGET_NODE) {
					found = true;
					Cell parent = neighbor.getParent();
					
					List<Cell> path = new ArrayList<Cell>();
					
					while (parent.getCellType() != CellType.STARTING_NODE) {
						path.add(parent);
						parent = parent.getParent();
					}
					
					for (Cell c : path) {
						c.changeType(CellType.PATH);
					}
					
					break;
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
