package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.Cell;
import data.CellType;
import data.Cells;
import main.foo;

public class AStar implements Algorithms {
	private Cells cells;
	private Cell start;
	private Cell target;
	private List<Cell> openCells = new ArrayList<Cell>();
	private List<Cell> closedCells = new ArrayList<Cell>();
	private Boolean found = false;
	private boolean allowedDiagonals;
	
	public void start(Cells cells) {
		this.cells = cells;
		assignVariables(cells);
		pathFind();
	}
	
	public void assignVariables(Cells cells) {
		this.cells = cells;
		Map<String, Cell> _cells = this.cells.get();
		
		for (Cell cell : _cells.values()) {
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

			List<Cell> currentNeighbors = checkingCell.getNeighbors(this.cells, target, this.allowedDiagonals);
			
			for (Cell neighbor : currentNeighbors) {
				double newGCost = checkingCell.getGCost() + Math.hypot(checkingCell.getX() - neighbor.getX(), checkingCell.getY() - neighbor.getY());
				double newHCost = this.allowedDiagonals ? Math.hypot(target.getX() - neighbor.getX(), target.getY() - neighbor.getY()) : Math.abs(checkingCell.getX() - target.getX()) + Math.abs(checkingCell.getY() - target.getY()) - 1;
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
						c.changeType(CellType.PATH, false);
					}
					
					break;
				}
				
				if (neighbor.getCellType() == CellType.EMPTY) {
					neighbor.changeType(CellType.OPEN, false);
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
		lowest.changeType(CellType.CLOSE, false);
		return lowest;
	}

	@Override
	public void setAllowedDiagonals(boolean b) {
		this.allowedDiagonals = b;
	}
}
