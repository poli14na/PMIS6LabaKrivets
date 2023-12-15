package com.pmorozova.movies.feature.recommendations

import com.pmorozova.movies.common.models.Genres
import com.pmorozova.movies.common.models.MovieTypeList
import com.pmorozova.movies.common.models.TitleInfo
import com.pmorozova.movies.common.models.TitlesPage

interface RecommendationsDataSource {
	suspend fun getTitlesPage(page: Int? = null, genre: String? = null, movieList: String? = null): Result<TitlesPage>
    suspend fun getRandomTitle(): Result<TitleInfo>
    suspend fun getGenres(): Result<Genres>
    suspend fun getMovieLists(): Result<MovieTypeList>
//	suspend fun getMovieDataById(id: String): Result<TitleInfo>
}