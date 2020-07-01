package visuals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Map;

import javax.swing.JFrame;

import data.Cell;
import main.Pathfinder;

@SuppressWarnings("serial")
public class PathfindingGUI extends JFrame {
	private Panel panel;
	public PathfindingGUI(Pathfinder p) throws InterruptedException {
		panel = new Panel(p);
		add(panel.getJPanel(), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Pathfinding Visualizer");
		pack();
        setVisible(true);
        
        Thread.sleep(50);
        
        panel.draw();
	}
}
