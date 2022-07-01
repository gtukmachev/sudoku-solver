package tga.test.sudoku.linked_list_index_recursive

import org.assertj.core.api.Assertions

object NodeAsserts {

    fun assertListEqualsTo(list: Node, vararg n: Int) {
        if (n.isEmpty()) Assertions.assertThat(list).isNull()

        var node: Node? = list
        for (i in n.indices) {

            Assertions.assertThat(node)
                .describedAs("node[$i] should be not null (the list is shorter than expected length = ${n.size})")
                .isNotNull

            Assertions.assertThat(node!!.n)
                .describedAs("node[$i].n (${node.n}) should be equals to ${n[i]}")
                .isEqualTo(n[i])

            node = node.next
        }

        Assertions.assertThat(node)
            .describedAs("The list is longer than expected length = ${n.size}")
            .isNull()

    }

}