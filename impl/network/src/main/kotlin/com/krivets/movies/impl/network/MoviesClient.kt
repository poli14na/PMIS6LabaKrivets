package com.krivets.movies.impl.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.dsl.module

interface MoviesClient {
	suspend fun getTitlesPage(
		page: Int? = null,
		genre: String? = null,
		movieList: String? = null
	): Result<ResponseTitlesPage>

	suspend fun getRandomTitle(): Result<ResponseTitleInfo>
	suspend fun getGenres(): Result<ResponseGenres>
	suspend fun getMovieLists(): Result<ResponseMovieTypeList>
	suspend fun getMovieDataById(id: String): Result<ResponseTitleInfo>
}

internal class MoviesClientImpl(networkClient: NetworkClient) : MoviesClient {
	private val httpClient = networkClient.httpClient

	override suspend fun getTitlesPage(
		page: Int?,
		genre: String?,
		movieList: String?
	) = runCatching {
		val pageParameter = if (page == null) "" else "?page=$page"
		val response = httpClient.get("/titles$pageParameter") {
			if (genre != null) parameter("genre", genre)
			if (movieList != null) parameter("list", movieList)
		}

		response.body<ResponseTitlesPage>()
	}

	override suspend fun getRandomTitle(): Result<ResponseTitleInfo> = runCatching {
		return@runCatching httpClient.get("/titles/random") {
			parameter("titleType", "movie")
			parameter("limit", 1)
			parameter("list", "most_pop_movies")
		}.body<ResponseRandomMovies>().results.first()
	}

	override suspend fun getGenres() = runCatching {
		httpClient.get("/titles/utils/genres").body<ResponseGenres>()
	}

	override suspend fun getMovieLists() = runCatching {
		httpClient.get("/titles/utils/lists").body<ResponseMovieTypeList>()
	}

	override suspend fun getMovieDataById(id: String): Result<ResponseTitleInfo> = runCatching {
		val response = httpClient.get("/titles/$id")

		response.body<TitleByIdResponse>().results
	}
}

val moviesClientModule = module { single<MoviesClient> { MoviesClientImpl(get()) } }
