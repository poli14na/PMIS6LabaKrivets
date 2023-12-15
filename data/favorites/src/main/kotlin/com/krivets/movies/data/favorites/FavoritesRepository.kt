package com.krivets.movies.data.favorites

import com.krivets.movies.common.models.Favorite
import com.krivets.movies.feature.favorites.FavoritesDataSource
import com.krivets.movies.feature.favorites.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module

internal class FavoritesRepositoryImpl(private val favoritesDataSource: FavoritesDataSource) :
	FavoritesRepository {
	override fun getAllItemsStream(): Flow<List<Favorite>> =
		favoritesDataSource.getAllItemsStream()

	override fun getItemStream(id: String): Flow<Favorite?> =
		favoritesDataSource.getItemStream(id)

	override suspend fun insertItem(item: Favorite) =
		favoritesDataSource.insertItem(item)

	override suspend fun deleteItem(item: Favorite) =
		favoritesDataSource.deleteItem(item)

	override suspend fun updateItem(item: Favorite) =
		favoritesDataSource.updateItem(item)
}

val favoritesRepositoryModule = module {
	single<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
}