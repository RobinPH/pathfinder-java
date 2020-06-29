package main;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import algorithms.AStar;
import data.Cell;
import data.CellType;
import visuals.PathfindingGUI;

public class foo {
	private static int HEIGHT = 10;
	private static int WIDTH = 10;
	private static int CELL_SIZE = 10;
	public static Map<String, Cell> cells;
	private static JFrame frame;
	public static AStar aStar;
	
	public static void main(String[] args) {
		cells = generateCells(WIDTH, HEIGHT);
		aStar = new AStar(cells);
		
		
		try {
			new PathfindingGUI(cells, 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Cell> generateCells(int width, int height) {
		Map<String, Cell> cells = new HashMap<>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell temp = new Cell(j, i, CellType.EMPTY);
				if (i == 2 && j == 2) temp.changeType(CellType.STARTING_NODE);
				if (i == 8 && j == 8) temp.changeType(CellType.TARGET_NODE);
				cells.put(positionToKey(j, i), temp);
			}
		}
		return cells;
	}
	
	public static String positionToKey(int x, int y) {
		String _x = Integer.toString(x);
		String _y = Integer.toString(y);
		
		return _x.concat(_y);
	}
}
