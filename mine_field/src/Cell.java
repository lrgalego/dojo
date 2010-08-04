import java.util.List;

class Cell {
	private final Point point;
	private final String value;
	private int bombCount = 0;

	public Cell(int x, int y, String value) {
		this.point = new Point(x, y);
		this.value = value;
	}

	public void incrementBombCount() {
		bombCount++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + point.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Cell)) {
			return false;
		}
		Cell other = (Cell) obj;
		return this.point.equals(other.point) && this.value.equals(other.value);
	}

	@Override
	public String toString() {
		return String.format("Cell [x=%s, y=%s, value=%s]", point.x(),
				point.y(), value);
	}

	public boolean isBomb() {
		return "*".equals(value);
	}

	public Point point() {
		return point;
	}

	public int bombCount() {
		return bombCount;
	}

	public String value() {
		return isBomb() ? "*" : String.valueOf(bombCount);
	}

	public List<Point> adjacentPoints() {
		return point.adjacents();
	}

}