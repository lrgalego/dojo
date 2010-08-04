import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Field {

	private final Map<Point, Cell> mineField = new HashMap<Point, Cell>();
	private int maxX;
	private int maxY;
	private boolean resolved = false;

	public Field(final String... mine) {
		initBoundaries(mine);
		initField(mine);
	}

	private void initField(final String... mine) {
		int x = 0;
		for (String line : mine) {
			for (int y = 0; y < maxY; y++) {
				mineField.put(new Point(x, y), buildCell(x, y, line));
			}
			x++;
		}
	}

	private void initBoundaries(final String... mine) {
		maxY = mine[0].length();
		maxX = mine.length;
	}

	private void resolve() {

		if (resolved) {
			return;
		}

		for (Cell cell : mineField.values()) {
			if (!cell.isBomb()) {
				continue;
			}
			for (Cell adjacent : adjacent(cell)) {
				adjacent.incrementBombCount();
			}
		}
		resolved = true;
	}

	public String result() {
		resolve();
		return buildResultString();
	}

	private String buildResultString() {
		StringBuilder result = new StringBuilder();
		for (int x = 0; x < maxX; x++) {
			for (int y = 0; y < maxY; y++) {
				Cell cell = mineField.get(new Point(x, y));
				result.append(cell.value());
			}
			result.append("\n");
		}
		return result.toString().substring(0, result.length() - 1);
	}

	public List<Cell> adjacent(Cell cell) {
		final List<Cell> cells = new ArrayList<Cell>();
		for (final Point point : cell.adjacentPoints()) {
			Cell adjacent = mineField.get(point);
			if (adjacent != null) {
				cells.add(adjacent);
			}
		}
		return cells;
	}
	
	private Cell buildCell(int x, int y, String value) {
		final Cell cell = new Cell(x, y, String.valueOf(value.charAt(y)));
		return cell;
	}

	public Cell cell(int x, int y) {
		return mineField.get(new Point(x, y));
	}
}