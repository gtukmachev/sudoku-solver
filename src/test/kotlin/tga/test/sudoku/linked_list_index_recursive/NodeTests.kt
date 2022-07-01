package tga.test.sudoku.linked_list_index_recursive


import org.assertj.core.api.Assertions.*
import org.junit.Test
import tga.test.sudoku.linked_list_index_recursive.NodeAsserts.assertListEqualsTo

class NodeTests {

    @Test
    fun `init9 should create a new list (0)-(1,2,3,4,5,6,7,8,9)`() {
        val list = Node.init9()
        assertListEqualsTo(list, 0,1,2,3,4,5,6,7,8,9)
    }

    @Test fun `remove() should delete exactly the passed element in middle`() {
        val list = Node.init9()
        list.remove(5)
        assertListEqualsTo(list, 0,1,2,3,4,  6,7,8,9)
    }

    @Test fun `remove() of the first element should return an error`() {
        val list = Node.init9()

        assertThatThrownBy{ list.remove(0) }
            .hasMessageMatching("No such element '0' in the list")
    }

    @Test fun `remove() of a not existed element should return an error`() {
        val list = Node.init9()

        assertThatThrownBy{ list.remove(15) }
            .hasMessageMatching("No such element '15' in the list")
    }

    @Test fun `remove() should delete exactly the passed element at the end`() {
        val list = Node.init9()
        list.remove(9)
        assertListEqualsTo(list, 0, 1,2,3,4,5,6,7,8)
    }

}