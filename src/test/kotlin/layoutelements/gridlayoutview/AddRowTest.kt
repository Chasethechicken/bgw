package layoutelements.gridlayoutview

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AddRowTest : GridLayoutViewTestBase() {
	
	@Test
	@DisplayName("Add row at start")
	fun testAddRowAtStart() {
		grid.addRows(0, 1)
		
		//First row must be empty
		testNull(rows = 0..0)
		
		//Rows 1-3 must contain former rows 0-2
		testUnchanged(rows = 1..3, rowBias = -1)
		
		//Grid size should be updated
		checkSize(3, 4)
	}
	
	@Test
	@DisplayName("Add row at end")
	fun testAddRowAtEnd() {
		grid.addRows(3, 1)
		
		//Rows 0-2 unchanged
		testUnchanged()
		
		//Last row must be empty
		testNull(rows = 3..3)
		
		//Grid size should be updated
		checkSize(3, 4)
	}
	
	@Test
	@DisplayName("Add multiple rows in middle")
	fun testAddMultipleRowsInMiddle() {
		grid.addRows(2, 3)
		
		//Row 0-1 unchanged
		testUnchanged(rows = 0..1)
		
		//Rows 2-4 must be empty
		testNull(rows = 2..4)
		
		//Row 5 contains former row 2
		testUnchanged(rows = 5..5, rowBias = -3)
		
		checkSize(3, 6)
	}
}