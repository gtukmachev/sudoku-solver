package tga.test.sudoku.linked_list_index_recursive

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Before
import org.junit.Test
import tga.test.sudoku.SudokuSolver
import tga.test.sudoku.UnresolvableSudoku

class SolvingTests {

    lateinit var task: Array<IntArray>
    val solver: SudokuSolver = SudokuSolverLinkedListsIndexRecursive()

    @Before fun init() {
        task = arrayOf(
            intArrayOf(3,6,2, 5,8,4, 9,1,7),
            intArrayOf(5,4,7, 2,1,9, 3,6,8),
            intArrayOf(8,9,1, 7,6,3, 2,4,5),

            intArrayOf(2,7,8, 6,4,5, 1,3,9),
            intArrayOf(1,5,9, 3,2,7, 4,8,6),
            intArrayOf(6,3,4, 8,9,1, 5,7,2),

            intArrayOf(7,8,5, 1,3,2, 6,9,4),
            intArrayOf(4,1,6, 9,5,8, 7,2,3),
            intArrayOf(9,2,3, 4,7,6, 8,5,1),
        )
    }

    @Test fun `solved sudoku should pass tests`(){
        assertThatSudokuIsCorrectlySolved(task)
    }

    @Test fun `not solved sudoku should not pass tests`(){
        task[5][5] = 0
        asertThatSudokuIsNotSolved(task)
    }

    @Test fun `a not solved sudoku should be resolved by the algorithm`(){
        for (i in 0..8) task[i][i] = 0
        solveAndAssert()
    }

    @Test fun `the empty sudoku should be resolved by the algorithm`(){
        for (l in 0..8)
            for (c in 0..8)
                task[l][c] = 0

        solveAndAssert()
    }

    @Test fun `a hard sudoku should be resolved by the algorithm`(){
        task = arrayOf(
            intArrayOf(0,0,0, 2,0,0, 0,0,0),
            intArrayOf(0,8,0, 0,7,1, 0,4,0),
            intArrayOf(7,3,9, 5,0,0, 0,0,0),

            intArrayOf(0,0,0, 0,0,0, 0,2,4),
            intArrayOf(9,0,0, 4,6,2, 0,0,0),
            intArrayOf(6,0,4, 8,0,0, 0,0,0),

            intArrayOf(2,0,7, 0,0,4, 0,0,0),
            intArrayOf(0,0,0, 0,8,7, 0,0,2),
            intArrayOf(0,6,0, 0,2,0, 0,3,5),
        )

        solveAndAssert()
    }

    @Test fun `a solved sudoku should pass the solving algorithm as well`() {
        solveAndAssert()
    }

    @Test
    fun `a wrong sudoku should raise the UnresolvableSudoku exception`(){
        task = arrayOf(
            intArrayOf(8,0,0, 2,0,0, 0,0,0), // the error in the coordinates (0,0)
            intArrayOf(0,8,0, 0,7,1, 0,4,0),
            intArrayOf(7,3,9, 5,0,0, 0,0,0),

            intArrayOf(0,0,0, 0,0,0, 0,2,4),
            intArrayOf(9,0,0, 4,6,2, 0,0,0),
            intArrayOf(6,0,4, 8,0,0, 0,0,0),

            intArrayOf(2,0,7, 0,0,4, 0,0,0),
            intArrayOf(0,0,0, 0,8,7, 0,0,2),
            intArrayOf(0,6,0, 0,2,0, 0,3,5),
        )

        assertThatThrownBy{
            solveAndAssert()
        }.isInstanceOf( UnresolvableSudoku::class.java )

    }


    private fun solveAndAssert() {

        val taskClone = cloneTask()
        var result: Array<IntArray> = Array(9){IntArray(9){0}}
        try {
            result = solver.solve(task)
            assertThatTheOriginalTaskWasNotMutated(task, taskClone)
            assertThatSudokuResultMatchesTheOriginalTask(task, result)
            assertThatSudokuIsCorrectlySolved(result)
        } finally {
            fun s(n: Int): String = if (n == 0) "." else n.toString()

            println("---------------------------------------------------")
            for (l in 0..8) {
                for (c in 0..8) {
                    print( s(task[l][c]) )
                    print(' ')
                    if (c % 3 == 2) print("  ")
                }
                print (" |    ")
                for (c in 0..8) {
                    print( s(result[l][c]) )
                    print(' ')
                    if (c % 3 == 2) print("  ")
                }

                println()
                if (l % 3 == 2) println()
            }
        }
    }

    private fun assertThatSudokuResultMatchesTheOriginalTask(task: Array<IntArray>, result: Array<IntArray>) {
        for (l in 0..8)
            for (c in 0..8)
                if (task[l][c] != 0)
                    assertThat( result[l][c] )
                        .describedAs("The solved sudoku should match the original task (line=$l, column=$c)")
                        .isEqualTo( task[l][c] )
    }

    private fun assertThatTheOriginalTaskWasNotMutated(taskAfterSolving: Array<IntArray>, originalTask: Array<IntArray>) {
        assertThat(taskAfterSolving)
            .describedAs("The original sudoku task should not be mutated by the solving algorithm")
            .isEqualTo(originalTask)
    }

    private fun cloneTask():Array<IntArray> {
        return Array(9){ l ->
                        IntArray(9) { c ->
                                task[l][c]
                        }
                }
    }

    private fun asertThatSudokuIsNotSolved(board: Array<IntArray>) {
        assertThatThrownBy{ assertThatSudokuIsCorrectlySolved(board) }
            .describedAs("The sudoku should not be solved")
            .isInstanceOf( java.lang.AssertionError::class.java )
    }

    private fun assertThatSudokuIsCorrectlySolved(board: Array<IntArray>) {

        asserLinesOrColumns("line", board)
        asserLinesOrColumns("column", board)

        arrayOf(0,3,6).forEach { l ->
            arrayOf(0,3,6).forEach { c ->
                assertSqr(l,c, board)
            }
        }
    }

    private fun assertSqr(l: Int, c: Int, board: Array<IntArray>) {
        val numbers = mutableSetOf(1,2,3,4,5,6,7,8,9)
        for(li in 0..2)
            for(ci in 0..2)
                numbers.remove(board[l+li][c+ci])

        assertThat(numbers)
            .describedAs("The sudoku square [($l,$c), (${l+2}, ${c+2})] should contains all the numbers exactly once ([${numbers.joinToString()}] are not present)")
            .isEmpty()

    }

    private fun asserLinesOrColumns(direction: String, board: Array<IntArray>) {
        for(l in 0..8) {
            val numbers = mutableSetOf(1,2,3,4,5,6,7,8,9)
            for (c in 0..8) {
                val n = when (direction) {
                    "line"   -> board[l][c]
                    "column" -> board[l][c]
                    else -> throw RuntimeException("unknown direction!")
                }
                numbers.remove(n)
            }
            assertThat(numbers)
                .describedAs("The sudoku $direction #$l should contains all the numbers exactly once ([${numbers.joinToString()}] are not present)")
                .isEmpty()
        }
    }

}