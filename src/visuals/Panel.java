package visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.List;
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
	private Cell prevDraggedCell;
	private boolean isDeletingWall = false;
	private boolean isCreatingWall = false;
	private List<Cell> cellToAnimate;
	public boolean doAnimate = false;
	private boolean isFirstAnimationDone = false;
	
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
        new Thread(() -> {
        	if (this.cellToAnimate == null || !this.doAnimate) {
    			drawAllCell(p.getCells().get().values(), false);
    		} else {
    			drawAllCell(this.cellToAnimate, true);
    		}
        }).start();
	}
	
	public void drawAllCell(Collection<Cell> cells, boolean animated) {
		for (Cell cell : cells) {
			drawCell(cell, animated);
		}
	}
	
	public void drawCell(Cell cell, boolean animated) {
		int cellSize = p.getCellSize();
		int x = cell.getX() * (cellSize + 2);
		int y = cell.getY() * (cellSize + 2);
		Color cellColor = cell.getColor();
		
		if (graphics == null) return;
		
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.drawRect(x, y, cellSize + 2, cellSize + 2);
		
		graphics.setColor(cellColor);
		graphics.fillRect(x + 1, y + 1, cellSize + 1, cellSize + 1);

		if (p.isOnDebug() && cell.getCellType() != CellType.EMPTY && cell.getCellType() != CellType.WALL) {
			graphics.setColor(Color.BLACK);
//			graphics.drawString(Double.toString(Math.round(cell.getGCost() * 100)), x + cellSize / 2 - 15, y + cellSize / 2 - 10); //G
//			graphics.drawString(Double.toString(Math.round(cell.getHCost() * 100)), x + cellSize / 2 - 15, y + cellSize / 2); //H
//			graphics.drawString(Double.toString(Math.round(cell.getFCost() * 100)), x + cellSize / 2 - 15, y + cellSize / 2 + 10); //F
			graphics.drawString(String.format("%d %d", cell.getX(), cell.getY()), x + cellSize / 2 - 15, y + cellSize / 2 + 20);
			try {
				graphics.drawString(String.format("%d %d", cell.getParent().getX(), cell.getParent().getY()), x + cellSize / 2 - 15, y + cellSize / 2 + 10);
			} catch (Exception e){}
		}
		
		if (animated && this.doAnimate) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
					this.prevDraggedCell = this.cellPressed;
					
					if (this.isFirstAnimationDone) this.cellToAnimate = p.algoStart();
					
					this.doAnimate = false;
					draw();
				}
			} else if (currentCellType == CellType.TARGET_NODE) {
				if (p.getCells().changeTargetNode(cellX, cellY)) {
					this.cellPressed = currentCell;
					this.prevDraggedCell = this.cellPressed;
					
					if (this.isFirstAnimationDone) this.cellToAnimate = p.algoStart();
					
					this.doAnimate = false;
					draw();
				}
			} else if (p.getCells().getCell(cellX, cellY).getCellType() == CellType.WALL && this.isDeletingWall) {
				currentCell.changeType(CellType.EMPTY, false);
				drawCell(currentCell, false);
			} else if (this.isCreatingWall) {
				currentCell.changeType(CellType.WALL, false);
				drawCell(currentCell, false);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String key = getKeyByEvent(e);
		Cell cell = p.getCells().get().get(key);
		if (cell.getCellType() == CellType.WALL ) {
			cell.changeType(CellType.EMPTY, false);
		} else if (cell.getCellType() == CellType.EMPTY ) {
			cell.changeType(CellType.WALL, false);
		}
		draw();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.doAnimate = false;
		
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
				this.doAnimate = !this.doAnimate;
				if(this.cellToAnimate == null) this.cellToAnimate = p.algoStart();
				
				if (this.isFirstAnimationDone) {
					p.clearCells(); 
					drawAllCell(p.getCells().get().values(), false);
				}
				
				draw();
				this.isFirstAnimationDone = true;
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
