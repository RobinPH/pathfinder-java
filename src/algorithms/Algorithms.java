package algorithms;

import java.util.Map;

import data.Cell;

public interface Algorithms {
	public void start(Map<String, Cell> cells);

	public void setAllowedDiagonals(boolean b);
}
