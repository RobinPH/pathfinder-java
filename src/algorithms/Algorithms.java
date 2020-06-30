package algorithms;

import java.util.List;

import data.Cell;
import data.Cells;

public interface Algorithms {
	public List<Cell> start(Cells cells);

	public void setAllowedDiagonals(boolean b);
}
