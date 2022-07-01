package tga.test.sudoku.linked_list_index_recursive

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SqrCoordinateTests {

    @Test fun `coordinates of board should be correctly converted to the coordinates o sqr index`() {
        val solver = SudokuSolverLinkedListsIndexRecursive()

        val arr = arrayOf(
            intArrayOf( 0,0,0, 1,1,1, 2,2,2),
            intArrayOf( 0,0,0, 1,1,1, 2,2,2),
            intArrayOf( 0,0,0, 1,1,1, 2,2,2),

            intArrayOf( 3,3,3, 4,4,4, 5,5,5),
            intArrayOf( 3,3,3, 4,4,4, 5,5,5),
            intArrayOf( 3,3,3, 4,4,4, 5,5,5),

            intArrayOf( 6,6,6, 7,7,7, 8,8,8),
            intArrayOf( 6,6,6, 7,7,7, 8,8,8),
            intArrayOf( 6,6,6, 7,7,7, 8,8,8),
        )

        for(l in 0..8)
            for(c in 0..8)
                assertThat( solver.sqrCoordinate(l,c) )
                    .isEqualTo(arr[l][c])
    }




}