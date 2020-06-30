package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import algorithms.AStar;
import algorithms.Algorithms;
import data.Cell;
import data.Cells;
import visuals.PathfindingGUI;

public class Pathfinder {
	private int WIDTH;
	private int HEIGHT;
	private int CELL_SIZE;
	private Cells cells;
	private boolean allowedDiagonals = false;
	private List<Algorithms> algorithms = new ArrayList<Algorithms>();
	
	public Pathfinder(int width, int height, int cellSize) throws InterruptedException {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.CELL_SIZE = cellSize;
		
		Cells cells = new Cells(width, height);
		this.cells = cells;
		
		PathfindingGUI gui = new PathfindingGUI(this);
	}
	
	public void algoStart() {
		algorithms.add(new AStar());
		algorithms.get(0).setAllowedDiagonals(false);
		algorithms.get(0).start(this.cells.get());
	}
	
	public Cells getCells() {
		return this.cells;
	}
	
	public int getWidth() {
		return this.WIDTH;
	}
	
	public int getHeight() {
		return this.HEIGHT;
	}

	public boolean isAllowedDiagonals() {
		return allowedDiagonals;
	}

	public void setAllowedDiagonals(boolean allowedDiagonals) {
		this.allowedDiagonals = allowedDiagonals;
	}
	
	public int getCellSize() {
		return this.CELL_SIZE;
	}
}
