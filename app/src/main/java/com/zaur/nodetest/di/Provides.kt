package com.zaur.nodetest.di

import com.zaur.nodetest.model.DisplayNodeName
import com.zaur.nodetest.data.NodeDataStore
import com.zaur.nodetest.viewmodel.NodeViewModelFactory

interface ProvideNodeViewModelFactory {
    fun provideNodeViewModelFactory(): NodeViewModelFactory
}

interface ProvideDisplayNodeName {
    fun provideDisplayNodeName(): DisplayNodeName
}

interface ProvideNodeDataStore {
    fun provideNodeDataStore(): NodeDataStore
}