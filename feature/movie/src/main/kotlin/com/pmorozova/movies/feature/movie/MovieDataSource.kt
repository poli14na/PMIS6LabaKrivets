package com.pmorozova.movies.feature.movie

import com.pmorozova.movies.common.models.Favorite
import com.pmorozova.movies.common.models.Genres
import com.pmorozova.movies.common.models.MovieTypeList
import com.pmorozova.movies.common.models.TitleInfo
import com.pmorozova.movies.common.models.TitlesPage
import kotlinx.coroutines.flow.Flow


interface MovieNotesDataSource {
    fun getAllItemsStream(): Flow<List<MovieNote>>
    fun getAllItemsStreamByMovieId(movieId: String): Flow<List<MovieNote>>
    fun getItemStream(id: String): Flow<MovieNote?>
    suspend fun insertItem(item: MovieNote)
    suspend fun deleteItem(item: MovieNote)
    suspend fun updateItem(item: MovieNote)
}

interface MovieNotesRepository {
    fun getAllItemsStream(): Flow<List<MovieNote>>
    fun getAllItemsStreamByMovieId(movieId: String): Flow<List<MovieNote>>
    fun getItemStream(id: String): Flow<MovieNote?>
    suspend fun insertItem(item: MovieNote)
    suspend fun deleteItem(item: MovieNote)
    suspend fun updateItem(item: MovieNote)
}

interface MovieDataSource {
    suspend fun getTitlesPage(page: Int? = null, genre: String? = null, movieList: String? = null): Result<TitlesPage>
    suspend fun getRandomTitle(): Result<TitleInfo>
    suspend fun getGenres(): Result<Genres>
    suspend fun getMovieLists(): Result<MovieTypeList>
    suspend fun getMovieDataById(id: String): Result<TitleInfo>
}

interface MovieRepository {
    suspend fun getTitlesPage(page: Int? = null, genre: String? = null, movieList: String? = null): Result<TitlesPage>
    suspend fun getRandomTitle(): Result<TitleInfo>
    suspend fun getGenres(): Result<Genres>
    suspend fun getMovieLists(): Result<MovieTypeList>
    suspend fun getMovieDataById(id: String): Result<TitleInfo>
}

interface FavoriteMovieDataSource {
    fun getAllItemsStream(): Flow<List<Favorite>>
    fun getItemStream(uuid: String): Flow<Favorite?>
    suspend fun insertItem(item: Favorite)
    suspend fun deleteItem(item: Favorite)
    suspend fun updateItem(item: Favorite)
}

interface FavoriteMovieRepository {
    fun getAllItemsStream(): Flow<List<Favorite>>
    fun getItemStream(uuid: String): Flow<Favorite?>
    suspend fun insertItem(item: Favorite)
    suspend fun deleteItem(item: Favorite)
    suspend fun updateItem(item: Favorite)
}

data class MovieNote(
    val uuid: String,
    val movieId: String,
    val text: String,
    val timeOfCreation: Long
)
