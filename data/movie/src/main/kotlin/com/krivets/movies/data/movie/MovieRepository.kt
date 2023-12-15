package com.krivets.movies.data.movie

import com.krivets.movies.common.models.Favorite
import com.krivets.movies.common.models.Genres
import com.krivets.movies.common.models.MovieTypeList
import com.krivets.movies.common.models.TitleInfo
import com.krivets.movies.common.models.TitlesPage
import com.krivets.movies.feature.favorites.FavoritesDataSource
import com.krivets.movies.feature.movie.FavoriteMovieRepository
import com.krivets.movies.feature.movie.MovieDataSource
import com.krivets.movies.feature.movie.MovieNote
import com.krivets.movies.feature.movie.MovieNotesDataSource
import com.krivets.movies.feature.movie.MovieNotesRepository
import com.krivets.movies.feature.movie.MovieRepository
import kotlinx.coroutines.flow.Flow
import org.koin.dsl.module

internal class MovieRepositoryImpl(private val movieDataSource: MovieDataSource) : MovieRepository {
	override suspend fun getTitlesPage(page: Int?, genre: String?, movieList: String?): Result<TitlesPage> =
		movieDataSource.getTitlesPage(page, genre, movieList)

	override suspend fun getRandomTitle(): Result<TitleInfo> =
		movieDataSource.getRandomTitle()

	override suspend fun getGenres(): Result<Genres> =
		movieDataSource.getGenres()

	override suspend fun getMovieLists(): Result<MovieTypeList> =
		movieDataSource.getMovieLists()

	override suspend fun getMovieDataById(id: String): Result<TitleInfo> =
		movieDataSource.getMovieDataById(id)
}

internal class FavoriteMovieRepositoryImpl(private val favoritesDataSource: FavoritesDataSource) :
	FavoriteMovieRepository {
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

internal class MovieNotesRepositoryImpl(private val movieNotesDataSource: MovieNotesDataSource) :
	MovieNotesRepository {
	override fun getAllItemsStream(): Flow<List<MovieNote>> =
		movieNotesDataSource.getAllItemsStream()

	override fun getAllItemsStreamByMovieId(movieId: String): Flow<List<MovieNote>> =
		movieNotesDataSource.getAllItemsStreamByMovieId(movieId)

	override fun getItemStream(id: String): Flow<MovieNote?> =
		movieNotesDataSource.getItemStream(id)

	override suspend fun insertItem(item: MovieNote) =
		movieNotesDataSource.insertItem(item)

	override suspend fun deleteItem(item: MovieNote) =
		movieNotesDataSource.deleteItem(item)

	override suspend fun updateItem(item: MovieNote) =
		movieNotesDataSource.updateItem(item)
}

val movieRepositoryModule = module {
	single<MovieRepository> { MovieRepositoryImpl(get()) }
	single<FavoriteMovieRepository> { FavoriteMovieRepositoryImpl(get()) }
}

val movieNotesRepositoryModule = module {
	single<MovieNotesRepository> { MovieNotesRepositoryImpl(get()) }
}