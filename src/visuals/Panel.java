package visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

import javax.swing.JPanel;

import main.foo;
import data.Cell;
import data.CellType;

@SuppressWarnings("serial")
public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	private Graphics graphics;
	private Map<String, Cell> cells = foo.cells;
	private int cellSize = foo.CELL_SIZE;
	private int WIDTH = foo.WIDTH;
	private int HEIGHT = foo.HEIGHT;
	
	public Panel() {
		addMouseListener(this);
        addMouseMotionListener(this);
        this.addKeyListener(this);
        setFocusable(true);
        requestFocus(); 
        setUpDrawingGraphics();
	}
	
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//	}
	
	private void setUpDrawingGraphics() {
		graphics = getGraphics();
	}
	
	public void draw() {
        setUpDrawingGraphics();
        
		drawAllCell();
		
		graphics.dispose();
		graphics = null;
	}
	
	public void drawAllCell() {
		for (Cell cell : cells.values()) {
			drawCell(cell);
		}
	}
	
	public void drawCell(Cell cell) {
		int x = cell.getX() * (cellSize + 2);
		int y = cell.getY() * (cellSize + 2);
		Color cellColor = cell.getColor();
		
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.drawRect(x, y, cellSize + 2, cellSize + 2);
		
		graphics.setColor(cellColor);
		graphics.fillRect(x + 1, y + 1, cellSize + 1, cellSize + 1);
		
//		if (cell.getCellType() != CellType.EMPTY && cell.getCellType() != CellType.WALL) {
//			graphics.setColor(Color.BLACK);
//			graphics.drawString(Double.toString(cell.getGCost()), x + cellSize / 2 - 15, y + cellSize / 2 - 15); //G
//			graphics.drawString(Double.toString(cell.getHCost()), x + cellSize / 2 - 15, y + cellSize / 2); //H
//			graphics.drawString(Double.toString(cell.getFCost()), x + cellSize / 2 - 15, y + cellSize / 2 + 15); //F	
//		}
	}
	
	public JPanel getJPanel() {
		setPreferredSize(new Dimension(cellSize * WIDTH + (WIDTH + 1) * 2 -1, cellSize * HEIGHT + (HEIGHT + 1) * 2 - 1));
		return this;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int cellX = (x - (x % (cellSize + 2))) / (cellSize + 2);
		int cellY = (y - (y % (cellSize + 2))) / (cellSize + 2);
		String key = foo.positionToKey(cellX, cellY);
		System.out.println(key);
		
		try {
			cells.get(key).changeType(CellType.WALL);
			draw();
		} catch(Exception e1) {
			System.out.println(e1);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int cellX = (x - (x % (cellSize + 2))) / (cellSize + 2);
		int cellY = (y - (y % (cellSize + 2))) / (cellSize + 2);
		String key = foo.positionToKey(cellX, cellY);
		System.out.println(key);
		try {
			cells.get(key).changeType(CellType.WALL);
			draw();
		} catch(Exception e1) {
			System.out.println(e1);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub		
		setUpDrawingGraphics();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case 32:
				foo.aStar.pathFind();
				draw();
				return;
			default:
				return;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
