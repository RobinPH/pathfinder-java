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

public class BreadthFirstSearch implements Algorithms {
	private Cells cells;
	private Cell start;
	private Cell target;
	private List<Cell> queue = new ArrayList<Cell>();
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
		queue.add(this.start);
	}
	
	public List<Cell> pathFind() {
		cellsToAnimate = new ArrayList<Cell>();
		
		while (!queue.isEmpty()) {
			Cell checkingCell = queue.remove(0);
			
			checkingCell.setToCurrentWorker(true);
			changeTypeAndAnimate(checkingCell, CellType.CLOSE);
			checkingCell.setVisited(true);

			List<Cell> currentNeighbors = checkingCell.getNeighbors(this.cells, this.allowedDiagonals);
			
			for (Cell neighbor : currentNeighbors) {
				if (neighbor.isVisited()) continue;
				
				if (neighbor.getParent() == null) {
					neighbor.setParent(checkingCell);
				}
   
				if (neighbor == target) {
					List<Cell> path = new ArrayList<Cell>();
					
					neighbor.getParent().setToCurrentWorker(false);
					changeTypeAndAnimate(neighbor.getParent(), neighbor.getParent().getCellType());
					
					for (Cell parent = neighbor; parent.getCellType() != CellType.STARTING_NODE; parent = parent.getParent()) 
						path.add(parent);
					
					Collections.reverse(path);
					
					for (Cell c : path) changeTypeAndAnimate(c, CellType.PATH);
					
					queue.clear();
					break;
				}
				
				if (neighbor.getCellType() == CellType.EMPTY) {
					changeTypeAndAnimate(neighbor, CellType.OPEN);
					queue.add(neighbor);
				}
			}
			
			checkingCell.setToCurrentWorker(false);
			if (checkingCell != null && !queue.isEmpty()) changeTypeAndAnimate(checkingCell, CellType.CLOSE);
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

	@Override
	public void setAllowedDiagonals(boolean b) {
		this.allowedDiagonals = b;
	}
}
