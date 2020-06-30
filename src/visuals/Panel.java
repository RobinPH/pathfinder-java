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

import main.Pathfinder;
import data.Cell;
import data.CellType;
import data.Cells;

@SuppressWarnings("serial")
public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	private Graphics graphics;
	private Pathfinder p;
	private boolean mousePressed = false;
	private boolean mouseDragging = false;
	private Cell cellPressed;
	private boolean rendered = false;
	private Cell prevDraggedCell;
	private boolean isDeletingWall = false;
	private boolean isCreatingWall = false;
	
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
		for (Cell cell : p.getCells().get().values()) {
			drawCell(cell);
		}
	}
	
	public void drawCell(Cell cell) {
		int cellSize = p.getCellSize();
		int x = cell.getX() * (cellSize + 2);
		int y = cell.getY() * (cellSize + 2);
		Color cellColor = cell.getColor();
		CellType cellType = cell.getCellType();
		
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.drawRect(x, y, cellSize + 2, cellSize + 2);

		// Disable Display for OPEN & CLOSE Cells
//		graphics.setColor(cellColor);
//		if (!p.isOnDebug() && !(cellType == CellType.WALL
//				|| cellType == CellType.PATH
//				|| cellType == CellType.STARTING_NODE
//				|| cellType == CellType.TARGET_NODE))
//			graphics.setColor(Cell.getColor(Cell.getHexColor(CellType.EMPTY)));
		
		graphics.setColor(cellColor);
		graphics.fillRect(x + 1, y + 1, cellSize + 1, cellSize + 1);

		if (p.isOnDebug() && cell.getCellType() != CellType.EMPTY && cell.getCellType() != CellType.WALL && cell.getCellType() != CellType.TARGET_NODE && cell.getCellType() != CellType.STARTING_NODE) {
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
	
	public String getKeyByEvent(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int cellSize = p.getCellSize();
		
		int cellX = (x - (x % (cellSize + 2))) / (cellSize + 2);
		int cellY = (y - (y % (cellSize + 2))) / (cellSize + 2);
		String key = Cells.positionToKey(cellX, cellY);
		
		return key;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		String key = getKeyByEvent(e);
		this.mouseDragging = true;
		
		if (this.cellPressed != null) {
			int x = e.getX();
			int y = e.getY();
			int cellSize = p.getCellSize();
			
			int cellX = (x - (x % (cellSize + 2))) / (cellSize + 2);
			int cellY = (y - (y % (cellSize + 2))) / (cellSize + 2);
			
			Cell currentCell = p.getCells().getCell(cellX, cellY);
			CellType currentCellType = this.cellPressed.getCellType();
			
			if (this.prevDraggedCell != null) {
				if (this.prevDraggedCell.getX() == cellX && this.prevDraggedCell.getY() == cellY)
					return;
			}
			
			if (currentCellType == CellType.STARTING_NODE) {
				
				if (p.getCells().changeStartingNode(cellX, cellY)) {
					this.cellPressed = currentCell;
					if (rendered) p.algoStart();
					this.prevDraggedCell = this.cellPressed;
					draw();
				}
			} else if (currentCellType == CellType.TARGET_NODE) {
				if (p.getCells().changeTargetNode(cellX, cellY)) {
					this.cellPressed = currentCell;
					if (rendered) p.algoStart();
					this.prevDraggedCell = this.cellPressed;
					draw();
				}
			} else if (p.getCells().getCell(cellX, cellY).getCellType() == CellType.WALL && this.isDeletingWall) {
				currentCell.changeType(CellType.EMPTY, false);
				drawCell(currentCell);
			} else {
				if (this.isCreatingWall) {
					currentCell.changeType(CellType.WALL, false);
					drawCell(currentCell);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String key = getKeyByEvent(e);
		
		try {
			Map<String, Cell> cells = p.getCells().get();
			cells.get(key).changeType(CellType.WALL, false);
			draw();
		} catch(Exception e1) {
			System.out.println(e1);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mousePressed = true;
		this.cellPressed = p.getCells().getCell(getKeyByEvent(e));
		
		if (p.getCells().getCell(getKeyByEvent(e)).getCellType() == CellType.WALL) {
			this.isDeletingWall = true;
		} else {
			this.isCreatingWall = true;
		}
		
		setUpDrawingGraphics();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mousePressed = false;
		this.mouseDragging = false;
		this.cellPressed = null;
		this.isCreatingWall = false;
		this.isDeletingWall = false;
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
				this.rendered = true;
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
