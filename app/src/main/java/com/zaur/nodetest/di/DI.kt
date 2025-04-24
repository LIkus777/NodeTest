package com.zaur.nodetest.di

import android.content.Context
import com.zaur.nodetest.data.NodeDataStore
import com.zaur.nodetest.model.DisplayNodeName
import com.zaur.nodetest.viewmodel.NodeViewModelFactory

interface DI : ProvideNodeViewModelFactory, ProvideDisplayNodeName, ProvideNodeDataStore {

    class Base(
        private val context: Context
    ) : DI {

        private val displayNodeName by lazy {
            DisplayNodeName.Base()
        }

        private val nodeDataStore by lazy {
            NodeDataStore.Base(context, "tree.preferences_pb")
        }

        override fun provideNodeViewModelFactory(): NodeViewModelFactory = NodeViewModelFactory.Base(provideNodeDataStore(), provideDisplayNodeName())

        override fun provideDisplayNodeName(): DisplayNodeName = displayNodeName

        override fun provideNodeDataStore(): NodeDataStore =  nodeDataStore

    }

}