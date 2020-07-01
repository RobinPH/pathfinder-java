package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private List<Cell> cellsToAnimate;
	
	public List<Cell> start(Cells cells) {
		assignVariables(cells);
		return pathFind();
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
	
	public List<Cell> pathFind() {
		cellsToAnimate = new ArrayList<Cell>();
		while (!found) {
			Cell checkingCell = getLowestFScore();
			if (checkingCell != null) changeTypeAndAnimate(checkingCell, CellType.CLOSE);

			List<Cell> currentNeighbors = checkingCell.getNeighbors(this.cells, this.allowedDiagonals);
			
			for (Cell neighbor : currentNeighbors) {
				double newGCost = checkingCell.getGCost() + Math.hypot(checkingCell.getX() - neighbor.getX(), checkingCell.getY() - neighbor.getY());
				double newHCost = this.allowedDiagonals ? Math.hypot(target.getX() - neighbor.getX(), target.getY() - neighbor.getY()) : Math.abs(neighbor.getX() - target.getX()) + Math.abs(neighbor.getY() - target.getY());
				
				if (neighbor.getParent() == null) {
					neighbor.setParent(checkingCell);
					neighbor.setHCost(newHCost);
					neighbor.setGCost(newGCost);
					
				} else {
					double gCost = neighbor.getGCost();
					double hCost = neighbor.getHCost();
					double fCost = neighbor.getFCost();
					double newFCost = hCost + newGCost;
					
					if (fCost > newFCost) {
						neighbor.setParent(checkingCell);
						neighbor.setGCost(newGCost);
						
					} else if (fCost == newFCost) {
						if (gCost > newGCost)
						{
							neighbor.setParent(checkingCell);
							neighbor.setGCost(newGCost);
						}
					}
				}
			}
			
			Collections.sort(currentNeighbors, new Comparator<Cell>() {
				@Override
				public int compare(Cell c1, Cell c2) {
					if (c1.getHCost() > c2.getHCost()) return 1;
					if (c2.getHCost() > c1.getHCost()) return -1;
					return 0;
				}
			});
			
			for (Cell neighbor : currentNeighbors) {
				
				if (neighbor.getCellType() == CellType.TARGET_NODE) {
					found = true;
					Cell parent = neighbor.getParent();
					
					List<Cell> path = new ArrayList<Cell>();
					
					while (parent.getCellType() != CellType.STARTING_NODE) {
						path.add(parent);
						parent = parent.getParent();
					}
					
					Collections.reverse(path);
					
					for (Cell c : path) {
						changeTypeAndAnimate(c, CellType.PATH);
					}
					
					break;
				}
				
				if (neighbor.getCellType() == CellType.EMPTY) {
					changeTypeAndAnimate(neighbor, CellType.OPEN);
					openCells.add(neighbor);
				}
			}
		}
		return this.cellsToAnimate;
	}
	
	public void changeTypeAndAnimate(Cell cell, CellType cellType) {
		cell.changeType(cellType, false);
		
		try {
			cellsToAnimate.add((Cell) cell.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
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
		return lowest;
	}

	@Override
	public void setAllowedDiagonals(boolean b) {
		this.allowedDiagonals = b;
	}
}
