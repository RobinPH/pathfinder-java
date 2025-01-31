package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import data.Cell;
import data.CellType;
import data.Cells;

public class DepthFirstSearch implements Algorithms {
	private boolean allowedDiagonals;
	private Cells cells;
	private Cell start;
	private Cell target;
	private List<Cell> cellsToAnimate;
	
	public List<Cell> start(Cells cells) {
		this.assignVariables(cells);
		return this.pathFind();
	}
	
	public void assignVariables(Cells cells) {
		this.cells = cells;
		Map<String, Cell> _cells = this.cells.get();
		
		for (Cell cell : _cells.values()) {
			if (cell.getCellType() == CellType.STARTING_NODE) this.start = cell;
			if (cell.getCellType() == CellType.TARGET_NODE) this.target = cell;
		}
	}
	
	public List<Cell> pathFind() {
		this.cellsToAnimate = new ArrayList<Cell>();
		DFS(this.start);
		return this.cellsToAnimate;
	}
	
	public boolean DFS(Cell cell) {
		cell.setVisited(true);
		cell.setToCurrentWorker(true);
		changeTypeAndAnimate(cell, CellType.CLOSE);
		
		List<Cell> neighbors = cell.getNeighbors(this.cells, this.allowedDiagonals);
		Collections.shuffle(neighbors);
		
		for (Cell neighbor : neighbors) {
			if (neighbor == this.target) {
				Cell parent = cell;
				
				cell.setToCurrentWorker(false);
				changeTypeAndAnimate(cell, cell.getCellType());
				
				List<Cell> path = new ArrayList<Cell>();
				
				while (parent.getCellType() != CellType.STARTING_NODE) {
					path.add(parent);
					parent = parent.getParent();
				}
				Collections.reverse(path);
				for (Cell c : path) {
					changeTypeAndAnimate(c, CellType.PATH);
				}
				
				return true;
			}
		}
		
		
		if (cell.getParent() != null) {
			cell.getParent().setToCurrentWorker(false);
			changeTypeAndAnimate(cell.getParent(), CellType.CLOSE);
		}
		
		cell.setToCurrentWorker(false);
		changeTypeAndAnimate(cell, CellType.CLOSE);
		
		for (Cell neighbor : neighbors) {
			
			if (neighbor.isVisited()) continue;
			
			neighbor.setParent(cell);
			changeTypeAndAnimate(neighbor, CellType.OPEN);
			
			if (DFS(neighbor)) return true;
		}
		
		return false;
	}
	
	public void changeTypeAndAnimate(Cell cell, CellType cellType) {
		cell.changeType(cellType, false);
		
		try {
			cellsToAnimate.add((Cell) cell.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public void setAllowedDiagonals(boolean b) {
		this.allowedDiagonals = b;
	}

}
