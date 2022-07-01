package tga.test.sudoku

class UnresolvableSudoku(val board: Array<IntArray>, cause: Throwable? = null): RuntimeException("Unresolvable Sudoku", cause)