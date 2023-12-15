package com.krivets.movies.impl.network

import kotlinx.serialization.Serializable

@Serializable
data class ResponseGenres(
	val results: List<String?>
)

@Serializable
data class ResponseMovieTypeList(
	val entries: Int,
	val results: List<String>
)

@Serializable
data class ResponseRandomMovies(
	val entries: Int,
	val results: List<ResponseTitleInfo>
)

@Serializable
data class ResponseTitlesPage(
	val page: Int,
	val next: String?,
	val entries: Int,
	val results: List<ResponseTitleInfo>
)

@Serializable
data class TitleByIdResponse(
	val results: ResponseTitleInfo
)

@Serializable
data class ResponseTitleInfo(
	val _id: String,
	val id: String,
	val primaryImage: ResponseImageData?,
	val titleType: ResponseTitleType,
	val titleText: ResponseTitleText,
	val originalTitleText: ResponseTitleText,
	val releaseYear: ResponseReleaseYear?,
	val releaseDate: ResponseReleaseDate?
)

@Serializable
data class ResponseImageData(
	val id: String,
	val width: Int,
	val height: Int,
	val url: String,
	val caption: ResponseImageCaption,
	val __typename: String,
)

@Serializable
data class ResponseImageCaption(
	val plainText: String,
	val __typename: String,
)

@Serializable
data class ResponseTitleType(
	val text: String,
	val id: String,
	val isSeries: Boolean,
	val isEpisode: Boolean,
	val __typename: String,
)

@Serializable
data class ResponseTitleText(
	val text: String,
	val __typename: String,
)

@Serializable
data class ResponseReleaseYear(
	val year: Int,
	val endYear: String?,
	val __typename: String,
)

@Serializable
data class ResponseReleaseDate(
	val day: Int?,
	val month: Int?,
	val year: Int,
	val __typename: String,
)
