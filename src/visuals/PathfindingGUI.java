package visuals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data.Cell;
import main.Pathfinder;

@SuppressWarnings("serial")
public class PathfindingGUI extends JFrame {
	public PathfindingGUI(Pathfinder p) throws InterruptedException {
		Panel panel = new Panel(p);
		add(panel.getJPanel(), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Pathfinding Visualizer");
		pack();
        setVisible(true);
        
        Thread.sleep(50);
        
        panel.draw();
	}
}
