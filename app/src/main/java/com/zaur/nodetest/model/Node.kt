package com.zaur.nodetest.model

import java.security.MessageDigest

data class Node(
    val id: String,
    val name: String,
    val parentId: String,
    val child: List<Node> = emptyList()
)

interface DisplayNodeName {

    fun displayName(node: Node): String

    class Base() : DisplayNodeName {
        override fun displayName(node: Node): String {
            val hash = MessageDigest.getInstance("SHA-256")
                .digest(node.id.toByteArray())
            return hash.takeLast(20)
                .joinToString("") { "%02x".format(it) }
        }
    }
}