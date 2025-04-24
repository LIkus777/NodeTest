package com.zaur.nodetest.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zaur.nodetest.model.Node

@Composable
fun TreeScreen(
    rootId: String,
    allNodes: List<Node>,
    onNodeClick: (String) -> Unit,
    onAddChild: (parentId: String) -> Unit,
    onDeleteNode: (nodeId: String) -> Unit,
) {
    // Возвращаем node or null
    val node = findNodeById(allNodes, rootId)

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (node == null) {
            Text(
                "Узел не найден",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
            return@Column
        }

        Text(
            "Текущий узел: ${node.id}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(8.dp))

        if (node.child.isEmpty()) {
            Text("Нет потомков", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                itemsIndexed(node.child) { index, child ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onNodeClick(child.id) }
                    ) {
                        Text(
                            text = "id — ${child.id}\nname — ${child.name}",
                            Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { onAddChild(node.id) }) {
                Text("Добавить потомка")
            }
            if (node.parentId.isNotEmpty()) {
                Button(onClick = { onDeleteNode(node.id) }) {
                    Text("Удалить этот узел")
                }
            }
        }
    }
}

/* Ищет node с данным id где угодно в дереве */
fun findNodeById(list: List<Node>, id: String): Node? {
    if (id == "root") return list.firstOrNull()
    list.forEach {
        if (it.id == id) return it
        findNodeById(it.child, id)?.let { return it }
    }
    return null
}