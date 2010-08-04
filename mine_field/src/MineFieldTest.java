import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
		final Cell cell = new Cell(1, 1, ".");
		final List<Point> points = cell.adjacentPoints();

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
	public void resolveMineField() {
		Field field = new Field("*..", ".*.", "..*");
		assertEquals("*21\n2*2\n12*", field.result());
	}

	@Test
	public void resolveMineFieldTwice() {
		Field field = new Field("*..", ".*.", "..*");
		assertEquals("*21\n2*2\n12*", field.result());
		assertEquals("*21\n2*2\n12*", field.result());
	}
}
