package main;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import algorithms.AStar;
import data.Cell;
import data.CellType;
import data.Cells;
import visuals.PathfindingGUI;

public class foo {
	public static int HEIGHT = 15;
	public static int WIDTH = 15;
	public static int CELL_SIZE = 45;
	public static Map<String, Cell> cells;
	public static AStar aStar;
	public static boolean allowedDiagonals = false;
	
	public static void main(String[] args) throws InterruptedException {
		new Pathfinder(5, 5, 50, false);
	}
}
