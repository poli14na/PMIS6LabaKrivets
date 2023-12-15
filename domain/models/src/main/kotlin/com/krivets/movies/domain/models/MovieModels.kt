package com.krivets.movies.common.models

data class TitleInfo(
    val id: String,
    val primaryImage: ImageData?,
    val titleType: TitleType,
    val titleText: TitleText,
    val originalTitleText: TitleText,
    val releaseYear: ReleaseYear?,
    val releaseDate: ReleaseDate?
)

data class Favorite(
    val movieId: String,
    val title: String,
    val imageUrl: String
)

data class ImageData(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
    val caption: ImageCaption,
)

data class ImageCaption(
    val plainText: String,
)

data class TitleType(
    val text: String,
    val id: String,
    val isSeries: Boolean,
    val isEpisode: Boolean,
)

data class TitleText(
    val text: String,
)

data class ReleaseYear(
    val year: Int,
    val endYear: String?,
)

data class ReleaseDate(
    val day: Int?,
    val month: Int?,
    val year: Int,
)

data class Genres(
    val results: List<String?>
)

data class MovieTypeList(
    val entries: Int,
    val results: List<String>
)

data class TitlesPage(
    val page: Int,
    val next: String?,
    val entries: Int,
    val results: List<TitleInfo>
)