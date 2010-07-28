import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MineFieldTest {

	@Test
	public void simpleFieldConstruction() throws Exception {
		Field field = new Field("*");
		assertEquals(new Cell(0, 0, "*"), field.cell(0, 0));
	}

	@Test
	public void twoLineFieldConstrutiction() {
		Field field = new Field("*", ".");
		assertEquals(new Cell(0, 0, "*"), field.cell(0, 0));
		assertEquals(new Cell(1, 0, "."), field.cell(1, 0));
	}

	@Test
	public void twoColumm() {
		Field field = new Field("*.", ".*");
		assertEquals(new Cell(0, 0, "*"), field.cell(0, 0));
		assertEquals(new Cell(1, 0, "."), field.cell(1, 0));
		assertEquals(new Cell(0, 1, "."), field.cell(0, 1));
		assertEquals(new Cell(1, 1, "*"), field.cell(1, 1));
	}

	@Test
	public void detectBomb() {
		Field field = new Field("*", ".");
		assertTrue(field.cell(0, 0).isBomb());
		assertFalse(field.cell(1, 0).isBomb());
	}

	@Test
	public void findAdjacents() {
		Field field = new Field("*..", ".*.", "..*");

		final List<Cell> cells = field.adjacent(new Cell(0, 0, "*"));

		assertTrue(cells.contains(new Cell(1, 0, ".")));
		assertTrue(cells.contains(new Cell(1, 1, "*")));
		assertTrue(cells.contains(new Cell(0, 1, ".")));
	}

	@Test
	public void findAdjacentPoints() {
		Field field = new Field("*..", ".*.", "..*");

		final List<Point> points = field.adjacentPoints(new Point(1, 1));

		assertEquals(8, points.size());
		
		assertTrue(points.contains(new Point(0, 0)));
		assertTrue(points.contains(new Point(0, 1)));
		assertTrue(points.contains(new Point(0, 2)));
		assertTrue(points.contains(new Point(1, 0)));
		assertTrue(points.contains(new Point(1, 2)));
		assertTrue(points.contains(new Point(2, 0)));
		assertTrue(points.contains(new Point(2, 1)));
		assertTrue(points.contains(new Point(2, 2)));
	}
	
	@Test
	public void resolveMineField(){
		Field field = new Field("*..", ".*.", "..*");
		
		assertEquals("*21\n2*2\n12*", field.resolve());
	}

	class Field {

		private final Map<Point, Cell> mineField = new HashMap<Point, Cell>();
		
		private final int maxX;
		private final int maxY;

		public Field(final String... mine) {
			
			maxY = mine[0].length();
			int x = 0;
			for (String line : mine) {
				for (int y = 0; y < maxY; y++) {
					mineField.put(new Point(x, y), buildCell(x, y, line));
				}
				x++;
			}
			maxX = x;
		}

		public String resolve() {
			for (Cell cell: mineField.values()){
				if(!cell.isBomb()){
					continue;
				}
				for ( Cell adjacent : adjacent(cell) ){
					adjacent.incrementBombCount();
				}
			}
			StringBuilder result = new StringBuilder();
			for(int x=0;x<maxX;x++){
				for(int y=0;y<maxY;y++){
					Cell cell = mineField.get(new Point(x,y));
					if(!cell.isBomb()){
						result.append(cell.bombCount());
					}else{
						result.append("*");
					}
				}
				result.append("\n");
			}
			
			return result.toString().substring(0, result.length()-1);
		}

		public List<Cell> adjacent(Cell cell) {
			final List<Cell> cells = new ArrayList<Cell>();
			for(final Point point : adjacentPoints(cell.point())){
				Cell adjacent = mineField.get(point);
				if(adjacent != null){
					cells.add(adjacent);
				}
			}
			return cells;
		}

		public List<Point> adjacentPoints(final Point point) {

			final List<Point> list = new ArrayList<Point>();
			list.add(new Point(point.x - 1, point.y));
			list.add(new Point(point.x + 1, point.y));
			list.add(new Point(point.x - 1, point.y - 1));
			list.add(new Point(point.x, point.y - 1));
			list.add(new Point(point.x + 1, point.y - 1));
			list.add(new Point(point.x - 1, point.y + 1));
			list.add(new Point(point.x, point.y + 1));
			list.add(new Point(point.x + 1, point.y + 1));
			return list;
		}

		private Cell buildCell(int x, int y, String value) {
			final Cell cell = new Cell(x, y, String.valueOf(value.charAt(y)));
			return cell;
		}

		public Cell cell(int x, int y) {
			return mineField.get(new Point(x, y));
		}
	}
}

class Cell {
	private final Point point;
	private final String value;
	private int bombCount = 0;

	public Cell(int x, int y, String value) {
		this.point = new Point(x, y);
		this.value = value;
	}

	public void incrementBombCount() {
		bombCount ++;
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
		return String.format("Cell [x=%s, y=%s, value=%s]", point.x, point.y,
				value);
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

}
