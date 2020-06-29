package visuals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data.Cell;

@SuppressWarnings("serial")
public class PathfindingGUI extends JFrame {
	private Map<String, Cell> cells;
	
	public PathfindingGUI(Map<String, Cell> cells) throws InterruptedException {
		Panel panel = new Panel();
		add(panel.getJPanel(), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Pathfinding Visualizer");
		pack();
        setVisible(true);
        
        Thread.sleep(50);
        
        panel.draw();
	}
}
