package com.krivets.movies.impl.database

import com.krivets.movies.common.models.Genres
import com.krivets.movies.common.models.ImageCaption
import com.krivets.movies.common.models.ImageData
import com.krivets.movies.common.models.MovieTypeList
import com.krivets.movies.common.models.ReleaseDate
import com.krivets.movies.common.models.ReleaseYear
import com.krivets.movies.common.models.TitleInfo
import com.krivets.movies.common.models.TitleText
import com.krivets.movies.common.models.TitleType
import com.krivets.movies.common.models.TitlesPage
import com.krivets.movies.feature.movie.MovieDataSource
import com.krivets.movies.feature.recommendations.RecommendationsDataSource
import com.krivets.movies.impl.network.MoviesClient
import com.krivets.movies.impl.network.ResponseGenres
import com.krivets.movies.impl.network.ResponseImageCaption
import com.krivets.movies.impl.network.ResponseImageData
import com.krivets.movies.impl.network.ResponseMovieTypeList
import com.krivets.movies.impl.network.ResponseReleaseDate
import com.krivets.movies.impl.network.ResponseReleaseYear
import com.krivets.movies.impl.network.ResponseTitleInfo
import com.krivets.movies.impl.network.ResponseTitleText
import com.krivets.movies.impl.network.ResponseTitleType
import com.krivets.movies.impl.network.ResponseTitlesPage

internal class MovieDataSourceImpl(private val moviesClient: MoviesClient) : MovieDataSource {
	override suspend fun getTitlesPage(page: Int?, genre: String?, movieList: String?): Result<TitlesPage> =
		moviesClient.getTitlesPage(page, genre, movieList).map { it.toCommonTitlesPage() }

	override suspend fun getRandomTitle(): Result<TitleInfo> =
		moviesClient.getRandomTitle().map { it.toCommonTitleInfo() }

	override suspend fun getGenres(): Result<Genres> =
		moviesClient.getGenres().map { it.toCommonGenres() }

	override suspend fun getMovieLists(): Result<MovieTypeList> =
		moviesClient.getMovieLists().map { it.toCommonMovieTypeList() }

	override suspend fun getMovieDataById(id: String): Result<TitleInfo> =
		moviesClient.getMovieDataById(id).map { it.toCommonTitleInfo() }

	private fun ResponseTitleInfo.toCommonTitleInfo(): TitleInfo =
		TitleInfo(
			id,
			primaryImage?.toCommonImageData(),
			titleType.toCommonTitleType(),
			titleText.toCommonTitleText(),
			originalTitleText.toCommonTitleText(),
			releaseYear?.toCommonReleaseYear(),
			releaseDate?.toCommonReleaseDate()
		)

	private fun ResponseImageData.toCommonImageData(): ImageData =
		ImageData(id, width, height, url, caption.toCommonImageCaption())

	private fun ResponseImageCaption.toCommonImageCaption(): ImageCaption =
		ImageCaption(plainText)

	private fun ResponseTitleType.toCommonTitleType(): TitleType =
		TitleType(text, id, isSeries, isEpisode)

	private fun ResponseTitleText.toCommonTitleText(): TitleText =
		TitleText(text)

	private fun ResponseReleaseYear.toCommonReleaseYear(): ReleaseYear =
		ReleaseYear(year, endYear)

	private fun ResponseReleaseDate.toCommonReleaseDate(): ReleaseDate =
		ReleaseDate(day, month, year)

	private fun ResponseGenres.toCommonGenres(): Genres =
		Genres(results)

	private fun ResponseMovieTypeList.toCommonMovieTypeList(): MovieTypeList =
		MovieTypeList(entries, results)

	private fun ResponseTitlesPage.toCommonTitlesPage(): TitlesPage =
		TitlesPage(page, next, entries, results.map { it.toCommonTitleInfo() })
}

internal class RecommendationsDataSourceImpl(private val moviesClient: MoviesClient) :
	RecommendationsDataSource {
	override suspend fun getTitlesPage(page: Int?, genre: String?, movieList: String?): Result<TitlesPage> =
		moviesClient.getTitlesPage(page, genre, movieList).map { it.toCommonTitlesPage() }

	override suspend fun getRandomTitle(): Result<TitleInfo> =
		moviesClient.getRandomTitle().map { it.toCommonTitleInfo() }

	override suspend fun getGenres(): Result<Genres> =
		moviesClient.getGenres().map { it.toCommonGenres() }

	override suspend fun getMovieLists(): Result<MovieTypeList> =
		moviesClient.getMovieLists().map { it.toCommonMovieTypeList() }

//	override suspend fun getMovieDataById(id: String): Result<TitleInfo> =
//		moviesClient.getMovieDataById(id).map { it.toCommonTitleInfo() }

	private fun ResponseTitleInfo.toCommonTitleInfo(): TitleInfo =
		TitleInfo(
			id,
			primaryImage?.toCommonImageData(),
			titleType.toCommonTitleType(),
			titleText.toCommonTitleText(),
			originalTitleText.toCommonTitleText(),
			releaseYear?.toCommonReleaseYear(),
			releaseDate?.toCommonReleaseDate()
		)

	private fun ResponseImageData.toCommonImageData(): ImageData =
		ImageData(id, width, height, url, caption.toCommonImageCaption())

	private fun ResponseImageCaption.toCommonImageCaption(): ImageCaption =
		ImageCaption(plainText)

	private fun ResponseTitleType.toCommonTitleType(): TitleType =
		TitleType(text, id, isSeries, isEpisode)

	private fun ResponseTitleText.toCommonTitleText(): TitleText =
		TitleText(text)

	private fun ResponseReleaseYear.toCommonReleaseYear(): ReleaseYear =
		ReleaseYear(year, endYear)

	private fun ResponseReleaseDate.toCommonReleaseDate(): ReleaseDate =
		ReleaseDate(day, month, year)

	private fun ResponseGenres.toCommonGenres(): Genres =
		Genres(results)

	private fun ResponseMovieTypeList.toCommonMovieTypeList(): MovieTypeList =
		MovieTypeList(entries, results)

	private fun ResponseTitlesPage.toCommonTitlesPage(): TitlesPage =
		TitlesPage(page, next, entries, results.map { it.toCommonTitleInfo() })
}