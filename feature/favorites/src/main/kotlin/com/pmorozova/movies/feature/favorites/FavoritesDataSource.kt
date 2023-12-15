package com.pmorozova.movies.feature.favorites

import com.pmorozova.movies.common.models.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesDataSource {
	fun getAllItemsStream(): Flow<List<Favorite>>
	fun getItemStream(uuid: String): Flow<Favorite?>
	suspend fun insertItem(item: Favorite)
	suspend fun deleteItem(item: Favorite)
	suspend fun updateItem(item: Favorite)
}

interface FavoritesRepository {
	fun getAllItemsStream(): Flow<List<Favorite>>
	fun getItemStream(uuid: String): Flow<Favorite?>
	suspend fun insertItem(item: Favorite)
	suspend fun deleteItem(item: Favorite)
	suspend fun updateItem(item: Favorite)
}