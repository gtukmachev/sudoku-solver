package tga.test.sudoku.linked_list_index_recursive

import tga.test.sudoku.SudokuSolver
import tga.test.sudoku.UnresolvableSudoku

class SudokuSolverLinkedListsIndexRecursive :SudokuSolver {

    override fun solve(board: Array<IntArray>): Array<IntArray> {
        try {

            val result = Array(9){ IntArray(9){0} }

            val lins = Array(9){ Node.init9() } // elements available in a line
            val cols = Array(9){ Node.init9() } // elements available in a column
            val sqrs = Array(9){ Node.init9() } // elements available in a squire

            initContext(board, result, lins, cols, sqrs)

            fun solveRec(l: Int, c: Int): Boolean {
                var nextL = l; var nextC = c + 1
                if (nextC == 9) {nextL++; nextC = 0}

                if (l == 9) return true // we are out of the field -> success
                if (result[l][c] != 0) return solveRec(nextL, nextC)

                var linPrev = lins[l]                 ; var lin = linPrev.next
                var colPrev = cols[c]                 ; var col = colPrev.next
                var sqrPrev = sqrs[sqrCoordinate(l,c)]; var sqr = sqrPrev.next

                while (lin != null && col != null && sqr != null) { // a loop by all available numbers in the position

                    if (lin.n == col.n && lin.n == sqr.n) { // we found a number which is available in all 3 indexes
                        val n = lin.n

                        // removing of the element from all 3 indexes and set up the number to the board
                        linPrev.next = lin.next
                        colPrev.next = col.next
                        sqrPrev.next = sqr.next
                        result[l][c] = n

                        if (solveRec(nextL, nextC)) return true

                        // rollback
                        linPrev.next = lin
                        colPrev.next = col
                        sqrPrev.next = sqr
                        result[l][c] = 0

                        linPrev = lin; lin = lin.next // goto the next element
                    } else {

                        // goto the next element
                        when {
                            (col.n < lin.n) -> { colPrev = col; col = col.next }
                            (sqr.n < lin.n) -> { sqrPrev = sqr; sqr = sqr.next }
                            else            -> { linPrev = lin; lin = lin.next }
                        }

                    }

                }

                return false
            }

            val isSolved = solveRec(0,0)

            if (!isSolved) throw UnresolvableSudoku(board)

            return result

        } catch (e: Exception) {
            throw when (e) {
                is UnresolvableSudoku -> e
                else                  -> UnresolvableSudoku(board, e)
            }
        }
    }

    fun sqrCoordinate(l: Int, c: Int): Int = (l/3)*3 + c/3

    fun initContext(board: Array<IntArray>, result: Array<IntArray>, lins :Array<Node>, cols :Array<Node>, sqrs :Array<Node>){
        for (l in 0..8)
            for (c in 0..8)
                if (board[l][c] != 0) {
                    val n = board[l][c]
                    result[l][c] = n
                    lins[l].remove(n)
                    cols[c].remove(n)
                    sqrs[sqrCoordinate(l,c)].remove(n)
                }

    }
}

class Node(val n: Int, var next: Node? = null) {

    fun remove(nValueToRemove: Int) {
        var prev = this

        while (prev.next != null && prev.next!!.n != nValueToRemove) {
            prev = prev.next!!
        }

        if (prev.next == null) throw RuntimeException("No such element '$nValueToRemove' in the list")

        prev.next = prev.next!!.next
    }

    companion object {

        fun init9(): Node {
            val list = Node(0) // first element of the list - is a "fake head" without a value

            var current = list
            for (i in 1 ..9) {
                current.next = Node(i)
                current = current.next!!
            }

            return list
        }

    }

}