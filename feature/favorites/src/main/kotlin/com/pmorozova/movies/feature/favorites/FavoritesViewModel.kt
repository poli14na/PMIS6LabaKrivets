package com.pmorozova.movies.feature.favorites

import android.util.Log
import com.pmorozova.movies.common.models.Favorite
import com.pmorozova.movies.impl.arch.MVIViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal sealed class FavoritesScreenIntent {
	data object LoadFavorites : FavoritesScreenIntent()
	data class SwitchSelectionForMovieItem(val favoriteMovie: Favorite) : FavoritesScreenIntent()
	data object CleanListOfSelectedMovies : FavoritesScreenIntent()
	data object RemoveSelectedItems: FavoritesScreenIntent()
}

sealed class FavoritesScreenEvent

internal data class FavoritesScreenState(
	val favorites: Flow<List<Favorite>>,
	val selectedMovies: List<Favorite>
) {
	companion object {
		val initial = FavoritesScreenState(emptyFlow(), emptyList())
	}
}

internal class FavoritesViewModel(
	private val initial: FavoritesScreenState,
	private val favoritesRepository: FavoritesRepository
) : MVIViewModel<FavoritesScreenState, FavoritesScreenIntent, FavoritesScreenEvent>(initial) {
	override suspend fun reduce(intent: FavoritesScreenIntent) {
		when (intent) {
			is FavoritesScreenIntent.LoadFavorites -> state {
				this.copy(favorites = favoritesRepository.getAllItemsStream())
			}

			is FavoritesScreenIntent.SwitchSelectionForMovieItem -> state {
				if (this.selectedMovies.contains(intent.favoriteMovie)) {
					this.copy(selectedMovies = this.selectedMovies - intent.favoriteMovie)
				} else {
					this.copy(selectedMovies = this.selectedMovies + intent.favoriteMovie)
				}
			}

			is FavoritesScreenIntent.CleanListOfSelectedMovies -> state {
				this.copy(selectedMovies = emptyList())
			}

			is FavoritesScreenIntent.RemoveSelectedItems -> state {
				scope.launch {
					this@state.selectedMovies.forEach {
						favoritesRepository.deleteItem(it)
					}
				}

				this.copy(selectedMovies = emptyList())
			}
		}
	}

	override fun CoroutineScope.onSubscribe() {
		Log.d("Favorites MVI VM", "Subscribed")

		intent(FavoritesScreenIntent.LoadFavorites)
	}

	override fun onCleared() {
		Log.d("favorites MVI VM", "Unsubscribed")
	}
}

val favoriteVMModule = module {
	viewModel { parameter -> FavoritesViewModel(parameter.get(), get()) }
}

