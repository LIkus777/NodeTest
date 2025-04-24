package com.zaur.nodetest.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zaur.nodetest.model.Node
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first

interface NodeDataStore {

    suspend fun loadTree(): List<Node>
    suspend fun saveTree(nodes: List<Node>)

    class Base(
        context: Context,
        produceFile: String
    ) : NodeDataStore {
        // 1) Подготовка Gson TypeToken для List<Node>
        private val gson = Gson()
        private val TREE_JSON_KEY = stringPreferencesKey("tree_json")
        private val nodeListType = object : TypeToken<List<Node>>() {}.type

        // 2) Явно создаём DataStore через фабрику:
        private val nodeDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO),
            // "файл данных" в internal storage
            produceFile = { context.preferencesDataStoreFile(produceFile) })

        override suspend fun loadTree(): List<Node> {
            val prefs = nodeDataStore.data.first()
            val json = prefs[TREE_JSON_KEY].orEmpty()
            return if (json.isBlank()) emptyList()
            else gson.fromJson(json, nodeListType)
        }

        override suspend fun saveTree(nodes: List<Node>) {
            val json = gson.toJson(nodes, nodeListType)
            nodeDataStore.edit { prefs ->
                prefs[TREE_JSON_KEY] = json
            }
        }
    }
}