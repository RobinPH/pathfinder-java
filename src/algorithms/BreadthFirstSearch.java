package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import data.Cell;
import data.CellType;
import data.Cells;

public class BreadthFirstSearch implements Algorithms {
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
		BFS(this.start);
		return this.cellsToAnimate;
	}
	
	public void BFS(Cell cell) {
		Cell currentWorkingNode;
		List<Cell> queue = new ArrayList<Cell>();
		
		queue.add(cell);
		
		while (!queue.isEmpty()) {
			currentWorkingNode = queue.remove(0);
			changeTypeAndAnimate(currentWorkingNode, CellType.CLOSE);
			
			List<Cell> neighbors = currentWorkingNode.getNeighbors(this.cells, this.allowedDiagonals);
			
			for (Cell neighbor : neighbors) {
				if (neighbor.isVisited()) continue;
				
				queue.add(neighbor);
				
				if (neighbor == this.target) {
					Cell parent = currentWorkingNode;
					List<Cell> path = new ArrayList<Cell>();
					
					while (parent.getCellType() != CellType.STARTING_NODE) {
						path.add(parent);
						parent = parent.getParent();
					}
					Collections.reverse(path);
					for (Cell c : path) {
						changeTypeAndAnimate(c, CellType.PATH);
					}
					return;
				}
				
				neighbor.setParent(currentWorkingNode);
				neighbor.setVisited(true);
				changeTypeAndAnimate(neighbor, CellType.OPEN);
			}
		}
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
