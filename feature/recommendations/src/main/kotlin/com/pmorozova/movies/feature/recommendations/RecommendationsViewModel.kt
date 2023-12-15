package com.pmorozova.movies.feature.recommendations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmorozova.movies.common.models.TitleInfo
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal class RecommendationsViewModel(private val movieRepository: RecommendationsDataSource) : ViewModel() {
	private var loadedPage = 0
	val movies = mutableStateListOf<TitleInfo>()

	val genres = mutableStateListOf<String>()
	var currentSelectedGenre: String? by mutableStateOf(null)

	val movieLists = mutableStateListOf<String>()
	var currentSelectedList: String? by mutableStateOf(null)

	init {
		viewModelScope.launch {
			launch { loadMovies() }
			launch { loadGenres() }
			launch { loadMovieLists() }
		}
	}

	suspend fun refreshMovies() = movieRepository.getTitlesPage(
		page = loadedPage + 1,
		genre = currentSelectedGenre,
		movieList = currentSelectedList
	).onSuccess {
		movies.clear()
		movies.addAll(it.results)
		loadedPage = 0

		viewModelScope.launch {
			launch { loadGenres() }
			launch { loadMovieLists() }
		}
	}


	private suspend fun loadMovies() {
		movieRepository.getTitlesPage(
			page = loadedPage + 1,
			genre = currentSelectedGenre,
			movieList = currentSelectedList
		).onSuccess {
			movies.addAll(it!!.results)
			loadedPage++
		}
	}

	private suspend fun loadGenres() = movieRepository.getGenres().onSuccess {
		genres.clear()

		it.results.forEach {
			if (it != null) { genres.add(it) }
		}
	}

	private suspend fun loadMovieLists() = movieRepository.getMovieLists().onSuccess {
		movieLists.clear()
		it.results.forEach {
			movieLists.add(it)
		}
	}

	suspend fun loadMoreMovies() {
		loadMovies()
	}

	fun changeGenre(newGenre: String) {
		resetLoadedMoviesList()
		currentSelectedGenre = newGenre
		viewModelScope.launch { loadMovies() }
	}

	fun changeMovieList(newList: String) {
		resetLoadedMoviesList()
		currentSelectedList = newList
		viewModelScope.launch { loadMovies() }
	}

	fun clearCurrentGenre() {
		resetLoadedMoviesList()
		currentSelectedGenre = null
		viewModelScope.launch { loadMovies() }
	}

	fun clearCurrentMovieList() {
		resetLoadedMoviesList()
		currentSelectedList = null
		viewModelScope.launch { loadMovies() }
	}

	private fun resetLoadedMoviesList() {
		loadedPage = 0
		movies.clear()
	}
}

val recommendationsVMModule = module {
	viewModel { RecommendationsViewModel(get()) }
}