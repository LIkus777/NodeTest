package com.zaur.nodetest.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.zaur.nodetest.App
import com.zaur.nodetest.ui.theme.NodeTestTheme

class MainActivity : ComponentActivity() {

    private val di by lazy { (application as App).diModule() }

    private val vm by lazy {
        di.provideNodeViewModelFactory().create()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val nodeState = vm.nodeState().collectAsState()

            Log.i("TAG", "onCreate: nodeState ${nodeState.value}")

            NodeTestTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    MainScreen(allNodes = nodeState.value.nodes, onAddChild = { parentId ->
                        vm.addChild(parentId)
                    }, onDeleteNode = { nodeId ->
                        vm.deleteNode(nodeId)
                    })
                }
            }
        }
    }
}