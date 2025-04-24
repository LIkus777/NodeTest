package com.zaur.nodetest.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.security.MessageDigest

class DisplayNodeNameTest {

    private lateinit var display: DisplayNodeName

    @Before
    fun setUp() {
        display = DisplayNodeName.Base()
    }

    private fun makeNode(id: String): Node =
        Node(id = id, name = "name-$id", parentId = "parent-$id")

    @Test
    fun `displayName возвращает строку длины 40`() {
        val node = makeNode("some-unique-id")
        val name = display.displayName(node)
        assertEquals(
            "Ожидаем длину 40",
            40,
            name.length
        )
    }

    @Test
    fun `displayName детерминирован для одного id`() {
        val node = makeNode("same-id")
        val first = display.displayName(node)
        val second = display.displayName(node)
        assertEquals(
            "Для одного и того же id результат должен быть одинаковым",
            first,
            second
        )
    }

    @Test
    fun `displayName различается для разных id`() {
        val nameA = display.displayName(makeNode("id-A"))
        val nameB = display.displayName(makeNode("id-B"))
        assertNotEquals(
            "Ожидается, что разные id дадут разные хеши",
            nameA,
            nameB
        )
    }

    @Test
    fun `displayName соответствует ручному вычислению`() {
        val testId = "test-123"
        val node = makeNode(testId)

        val digest = MessageDigest.getInstance("SHA-256")
            .digest(testId.toByteArray())
        val expected = digest.takeLast(20)
            .joinToString("") { "%02x".format(it) }

        val actual = display.displayName(node)
        assertEquals(
            "Результат должен совпадать с ручным вычислением",
            expected,
            actual
        )
    }
}
