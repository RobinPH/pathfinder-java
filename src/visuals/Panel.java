package visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import main.Pathfinder;
import data.Cell;
import data.CellType;
import data.Cells;

@SuppressWarnings("serial")
public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	private Graphics graphics;
	private Pathfinder p;
	
	public Panel(Pathfinder p) {
		this.p = p;
		
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
		for (Cell cell : p.getCells().values()) {
			drawCell(cell);
		}
	}
	
	public void drawCell(Cell cell) {
		int cellSize = p.getCellSize();
		int x = cell.getX() * (cellSize + 2);
		int y = cell.getY() * (cellSize + 2);
		Color cellColor = cell.getColor();
		
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.drawRect(x, y, cellSize + 2, cellSize + 2);
		
		graphics.setColor(cellColor);
		graphics.fillRect(x + 1, y + 1, cellSize + 1, cellSize + 1);
		
		if (cell.getCellType() != CellType.EMPTY && cell.getCellType() != CellType.WALL && cell.getCellType() != CellType.TARGET_NODE && cell.getCellType() != CellType.STARTING_NODE) {
			graphics.setColor(Color.BLACK);
			graphics.drawString(Double.toString(Math.round(cell.getGCost() * 100)), x + cellSize / 2 - 10, y + cellSize / 2 - 10); //G
			graphics.drawString(Double.toString(Math.round(cell.getHCost() * 100)), x + cellSize / 2 - 10, y + cellSize / 2); //H
			graphics.drawString(Double.toString(Math.round(cell.getFCost() * 100)), x + cellSize / 2 - 10, y + cellSize / 2 + 10); //F	
		}
	}
	
	public JPanel getJPanel() {
		int cellSize = p.getCellSize();
		int WIDTH = p.getWidth();
		int HEIGHT = p.getHeight();
		setPreferredSize(new Dimension(cellSize * WIDTH + (WIDTH + 1) * 2 -1, cellSize * HEIGHT + (HEIGHT + 1) * 2 - 1));
		return this;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int cellSize = p.getCellSize();
		
		int cellX = (x - (x % (cellSize + 2))) / (cellSize + 2);
		int cellY = (y - (y % (cellSize + 2))) / (cellSize + 2);
		String key = Cells.positionToKey(cellX, cellY);
		
		try {
			p.getCells().get(key).changeType(CellType.WALL);
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
		int cellSize = p.getCellSize();
		
		int cellX = (x - (x % (cellSize + 2))) / (cellSize + 2);
		int cellY = (y - (y % (cellSize + 2))) / (cellSize + 2);
		String key = Cells.positionToKey(cellX, cellY);
		
		try {
			p.getCells().get(key).changeType(CellType.WALL);
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
				p.algoStart();
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
