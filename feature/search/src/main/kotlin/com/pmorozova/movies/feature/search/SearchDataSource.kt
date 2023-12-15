package com.pmorozova.movies.feature.search

import com.pmorozova.movies.common.models.SearchHistoryItem
import com.pmorozova.movies.common.models.TitlesPage
import kotlinx.coroutines.flow.Flow

interface SearchDataSource {
	fun getAllItemsStream(): Flow<List<SearchHistoryItem>>
	fun getItemStream(id: String): Flow<SearchHistoryItem?>
	suspend fun insertItem(item: SearchHistoryItem)
	suspend fun deleteItem(item: SearchHistoryItem)
	suspend fun updateItem(item: SearchHistoryItem)

	suspend fun getSearchPage(searchQuery: String, page: Int? = null): TitlesPage?
}

interface SearchRepository {
	fun getAllItemsStream(): Flow<List<SearchHistoryItem>>
	fun getItemStream(id: String): Flow<SearchHistoryItem?>
	suspend fun insertItem(item: SearchHistoryItem)
	suspend fun deleteItem(item: SearchHistoryItem)
	suspend fun updateItem(item: SearchHistoryItem)

	suspend fun getSearchPage(searchQuery: String, page: Int? = null): TitlesPage?
}