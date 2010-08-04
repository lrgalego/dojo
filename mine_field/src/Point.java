import java.util.ArrayList;
import java.util.List;

public final class Point {

	private final int x;
	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point)) {
			return false;
		}
		Point other = (Point) obj;
		if (x != other.x || y != other.y) {
			return false;
		}
		return true;
	}

	public List<Point> adjacents() {

		final List<Point> list = new ArrayList<Point>();
		list.add(new Point(x - 1, y));
		list.add(new Point(x + 1, y));
		list.add(new Point(x - 1, y - 1));
		list.add(new Point(x, y - 1));
		list.add(new Point(x + 1, y - 1));
		list.add(new Point(x - 1, y + 1));
		list.add(new Point(x, y + 1));
		list.add(new Point(x + 1, y + 1));
		return list;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}

}
