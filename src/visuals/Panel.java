package visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

import javax.swing.JPanel;

import main.foo;
import data.Cell;
import data.CellType;

@SuppressWarnings("serial")
public class Panel extends JPanel implements MouseListener, MouseMotionListener {
	private Graphics graphics;
	private Map<String, Cell> cells = foo.cells;
	private int cellSize = 50;
	
	public Panel() {
		addMouseListener(this);
        addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	public void draw() {
		drawAllCell();
	}
	
	public void drawAllCell() {
		for (Cell cell : cells.values()) {
			drawCell(cell);
		}
	}
	
	public void drawCell(Cell cell) {
		int x = cell.getX();
		int y = cell.getY();
		Color cellColor = cell.getColor();
		
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.drawRect(x * (cellSize + 2), y * (cellSize + 2), cellSize + 2, cellSize + 2);
		
		graphics.setColor(cellColor);
		graphics.fillRect(x * (cellSize + 2) + 1, y * (cellSize + 2) + 1, cellSize + 1, cellSize + 1);
	}
	
	private void setUpDrawingGraphics() {
		graphics = getGraphics();
	}
	
	public JPanel getJPanel() {
		setPreferredSize(new Dimension(cellSize * 10 + (10 + 1) * 2 -1, cellSize * 10 + (10 + 1) * 2 - 1));
		return this;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int cellX = (x - (x % (cellSize + 2))) / (cellSize + 2);
		int cellY = (y - (y % (cellSize + 2))) / (cellSize + 2);
		String key = foo.positionToKey(cellX, cellY);
		
		try {
			cells.get(key).changeType(CellType.WALL);
			drawAllCell();
		} catch(Exception e1) {
			System.out.println(e1);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub		
		setUpDrawingGraphics();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		graphics.dispose();
		graphics = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
