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
	private boolean debug = false;

	public Pathfinder(int width, int height, int cellSize, boolean debug) throws InterruptedException {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.CELL_SIZE = cellSize;
		this.debug = debug;
		
		Cells cells = new Cells(width, height);
		this.cells = cells;
		
		PathfindingGUI gui = new PathfindingGUI(this);
	}
	
	public List<Cell> algoStart() {
		Algorithms algo = new AStar();
		algo.setAllowedDiagonals(false);
		return algo.start(this.cells);
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
	
	public boolean isOnDebug() {
		return this.debug;
	}
}
