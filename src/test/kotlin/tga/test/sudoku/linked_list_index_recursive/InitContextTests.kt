package tga.test.sudoku.linked_list_index_recursive

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import tga.test.sudoku.linked_list_index_recursive.NodeAsserts.assertListEqualsTo

class InitContextTests {
    lateinit var solver: SudokuSolverLinkedListsIndexRecursive
    lateinit var board: Array<IntArray>
    lateinit var result: Array<IntArray>
    lateinit var lins: Array<Node>
    lateinit var cols: Array<Node>
    lateinit var sqrs: Array<Node>


    @Before
    fun init() {
        solver = SudokuSolverLinkedListsIndexRecursive()
        board  = Array(9){ IntArray(9){0} }
        result = Array(9){ IntArray(9){0} }
        lins   = Array(9){ Node.init9() }
        cols   = Array(9){ Node.init9() }
        sqrs   = Array(9){ Node.init9() }
    }

    @Test fun `context builder should do nothing if no one nuber defined on the board`() {
        solver.initContext(board, result, lins, cols, sqrs)
        assertThat(result).isEqualTo(board)

        listOf(cols,sqrs,lins).forEach{ indexArr ->
            for(i in 0..9)
                assertListEqualsTo(indexArr[0], 0,1,2,3,4,5,6,7,8,9)
        }
    }


    @Test fun `context builder should leave in indexes only available numbers`() {
        for(i in 0..8)
            board[i][i] = (i + 1)

        solver.initContext(board, result, lins, cols, sqrs)

        assertThat(result).isEqualTo(board)

        assertListEqualsTo(sqrs[0], 0,      4,5,6,7,8,9)
        assertListEqualsTo(sqrs[1], 0,1,2,3,4,5,6,7,8,9)
        assertListEqualsTo(sqrs[2], 0,1,2,3,4,5,6,7,8,9)

        assertListEqualsTo(sqrs[3], 0,1,2,3,4,5,6,7,8,9)
        assertListEqualsTo(sqrs[4], 0,1,2,3,      7,8,9)
        assertListEqualsTo(sqrs[5], 0,1,2,3,4,5,6,7,8,9)

        assertListEqualsTo(sqrs[6], 0,1,2,3,4,5,6,7,8,9)
        assertListEqualsTo(sqrs[7], 0,1,2,3,4,5,6,7,8,9)
        assertListEqualsTo(sqrs[8], 0,1,2,3,4,5,6      )

        assertListEqualsTo(lins[0], 0,  2,3,4,5,6,7,8,9)
        assertListEqualsTo(lins[1], 0,1,  3,4,5,6,7,8,9)
        assertListEqualsTo(lins[2], 0,1,2,  4,5,6,7,8,9)
        assertListEqualsTo(lins[3], 0,1,2,3,  5,6,7,8,9)
        assertListEqualsTo(lins[4], 0,1,2,3,4,  6,7,8,9)
        assertListEqualsTo(lins[5], 0,1,2,3,4,5,  7,8,9)
        assertListEqualsTo(lins[6], 0,1,2,3,4,5,6,  8,9)
        assertListEqualsTo(lins[7], 0,1,2,3,4,5,6,7,  9)
        assertListEqualsTo(lins[8], 0,1,2,3,4,5,6,7,8, )

        assertListEqualsTo(cols[0], 0,  2,3,4,5,6,7,8,9)
        assertListEqualsTo(cols[1], 0,1,  3,4,5,6,7,8,9)
        assertListEqualsTo(cols[2], 0,1,2,  4,5,6,7,8,9)
        assertListEqualsTo(cols[3], 0,1,2,3,  5,6,7,8,9)
        assertListEqualsTo(cols[4], 0,1,2,3,4,  6,7,8,9)
        assertListEqualsTo(cols[5], 0,1,2,3,4,5,  7,8,9)
        assertListEqualsTo(cols[6], 0,1,2,3,4,5,6,  8,9)
        assertListEqualsTo(cols[7], 0,1,2,3,4,5,6,7,  9)
        assertListEqualsTo(cols[8], 0,1,2,3,4,5,6,7,8, )

    }

}