package layoutelements.gridlayoutview

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.aqua.bgw.elements.layoutviews.GridLayoutView.Companion.COLUMN_WIDTH_AUTO
import tools.aqua.bgw.elements.layoutviews.GridLayoutView.Companion.ROW_HEIGHT_AUTO
import tools.aqua.bgw.elements.uielements.Label
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class GetSetTest : GridLayoutViewTestBase() {
	
	@Test
	@DisplayName("Get and set")
	fun testGetSet() {
		val item = Label()
		grid[1, 2] = item
		assertEquals(item, grid[1, 2])
	}
	
	@Test
	@DisplayName("Get and set null")
	fun testGetSetNull() {
		grid[1, 2] = null
		assertNull(grid[1, 2])
	}
	
	@Test
	@DisplayName("Get and set spacing")
	fun testGetSetSpacing() {
		grid.spacing = 42.0
		assertEquals(42.0, grid.spacing)
	}
	
	@Test
	@DisplayName("Get and set spacing to zero")
	fun testGetSetSpacingZero() {
		grid.spacing = 0.0
		assertEquals(0.0, grid.spacing)
	}
	
	@Test
	@DisplayName("Get and set spacing negative")
	fun testGetSetSpacingNegative() {
		assertFailsWith<IllegalArgumentException> {
			grid.spacing = -1.0
		}
	}
	
	@Test
	@DisplayName("Set column width")
	fun testGetSetColumnWidth() {
		grid.setColumnWidth(1, 42.0)
		
		assertEquals(COLUMN_WIDTH_AUTO, grid.getColumnWidth(0))
		assertEquals(42.0, grid.getColumnWidth(1))
		assertEquals(COLUMN_WIDTH_AUTO, grid.getColumnWidth(2))
	}
	
	@Test
	@DisplayName("Set column widths")
	fun testGetSetColumnWidths() {
		grid.setColumnWidths(DoubleArray(3) { 42.0 })
		
		for (i in 0..2) {
			assertEquals(42.0, grid.getColumnWidth(i))
		}
	}
	
	@Test
	@DisplayName("Set auto column width")
	fun testGetSetAutoColumnWidth() {
		grid.setColumnWidths(DoubleArray(3) { 42.0 })
		grid.setAutoColumnWidth(1)
		
		assertEquals(42.0, grid.getColumnWidth(0))
		assertEquals(COLUMN_WIDTH_AUTO, grid.getColumnWidth(1))
		assertEquals(42.0, grid.getColumnWidth(2))
	}
	
	@Test
	@DisplayName("Set auto column widths")
	fun testGetSetAutoColumnWidths() {
		grid.setColumnWidths(DoubleArray(3) { 42.0 })
		grid.setAutoColumnWidths()
		
		for (i in 0..2) {
			assertEquals(COLUMN_WIDTH_AUTO, grid.getColumnWidth(i))
		}
	}
	
	@Test
	@DisplayName("Set column width to zero")
	fun testGetSetColumnWidthZero() {
		grid.setColumnWidth(1, 0)
	}
	
	@Test
	@DisplayName("Set column width to minus one")
	fun testGetSetColumnWidthMinusOne() {
		grid.setColumnWidth(1, -1)
	}
	
	@Test
	@DisplayName("Set column width negative")
	fun testGetSetColumnWidthNegative() {
		assertFailsWith<IllegalArgumentException> {
			grid.setColumnWidth(1, -0.5)
		}
		
		assertFailsWith<IllegalArgumentException> {
			grid.setColumnWidth(1, -42)
		}
	}
	
	@Test
	@DisplayName("Set row height")
	fun testGetSetRowHeight() {
		grid.setRowHeight(1, 42.0)
		
		assertEquals(ROW_HEIGHT_AUTO, grid.getRowHeight(0))
		assertEquals(42.0, grid.getRowHeight(1))
		assertEquals(ROW_HEIGHT_AUTO, grid.getRowHeight(2))
	}
	
	@Test
	@DisplayName("Set row heights")
	fun testGetSetRowHeights() {
		grid.setRowHeights(DoubleArray(3) { 42.0 })
		
		for (i in 0..2) {
			assertEquals(42.0, grid.getRowHeight(i), "index $i")
		}
	}
	
	@Test
	@DisplayName("Set auto row height")
	fun testGetSetAutoRowHeight() {
		grid.setRowHeights(DoubleArray(3) { 42.0 })
		grid.setAutoRowHeight(1)
		
		assertEquals(42.0, grid.getRowHeight(0))
		assertEquals(ROW_HEIGHT_AUTO, grid.getRowHeight(1))
		assertEquals(42.0, grid.getRowHeight(2))
	}
	
	@Test
	@DisplayName("Set auto row heights")
	fun testGetSetAutoRowHeights() {
		grid.setRowHeights(DoubleArray(3) { 42.0 })
		grid.setAutoRowHeights()
		
		for (i in 0..2) {
			assertEquals(ROW_HEIGHT_AUTO, grid.getRowHeight(i))
		}
	}
	
	@Test
	@DisplayName("Set row height to zero")
	fun testGetSetRowHeightZero() {
		grid.setRowHeight(1, 0)
	}
	
	@Test
	@DisplayName("Set row height to minus one")
	fun testGetSetRowHeightMinusOne() {
		grid.setRowHeight(1, -1)
	}
	
	@Test
	@DisplayName("Set row height negative")
	fun testGetSetRowHeightNegative() {
		assertFailsWith<IllegalArgumentException> {
			grid.setRowHeight(1, -0.5)
		}
		
		assertFailsWith<IllegalArgumentException> {
			grid.setRowHeight(1, -42)
		}
	}
}