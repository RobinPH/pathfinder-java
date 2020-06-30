package main;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import algorithms.AStar;
import data.Cell;
import data.CellType;
import visuals.PathfindingGUI;

public class foo {
	public static int HEIGHT = 15;
	public static int WIDTH = 15;
	public static int CELL_SIZE = 45;
	public static Map<String, Cell> cells;
	private static JFrame frame;
	public static AStar aStar;
	public static boolean allowedDiagonals = false;
	
	public static void main(String[] args) {
		cells = generateCells();
		aStar = new AStar(cells);
		
		
		try {
			new PathfindingGUI(cells);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Cell> generateCells() {
		Map<String, Cell> cells = new HashMap<>();
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				Cell temp = new Cell(j, i, CellType.EMPTY);
				if (i == 2 && j == 2) temp.changeType(CellType.STARTING_NODE);
				if (i == 12 && j == 13) temp.changeType(CellType.TARGET_NODE);
				cells.put(positionToKey(j, i), temp);
			}
		}
		return cells;
	}
	
	public static String positionToKey(int x, int y) {
		String _x = Integer.toString(x);
		String _y = Integer.toString(y);
		
		return _x.concat("-").concat(_y);
	}
}
