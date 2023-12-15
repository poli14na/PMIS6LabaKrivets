package com.pmorozova.movies.feature.movie

import android.util.Log
import com.pmorozova.movies.common.models.Favorite
import com.pmorozova.movies.common.models.TitleInfo
import com.pmorozova.movies.impl.arch.MVIViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.UUID

internal sealed class MovieScreenIntent {
	data class LoadMovieData(val id: String) : MovieScreenIntent()
	data class LoadFavoriteStatus(val id: String) : MovieScreenIntent()
	data class LoadNotes(val id: String) : MovieScreenIntent()

	data class SetFavoriteStatus(val id: String, val movieData: TitleInfo) : MovieScreenIntent()

	data class AddNote(val id: String, val note: String) : MovieScreenIntent()
	data class RemoveNote(val note: MovieNote) : MovieScreenIntent()
	data class SwitchNoteSelection(val note: MovieNote) : MovieScreenIntent()
	data object ClearSelectedNotes : MovieScreenIntent()
	data object DeleteSelectedNotes : MovieScreenIntent()
	data class SelectNoteForEditing(val note: MovieNote) : MovieScreenIntent()
	data class SaveNoteEdits(val newText: String) : MovieScreenIntent()
	data object ExitNoteEditing : MovieScreenIntent()
}

internal sealed class MovieScreenEvent

internal data class MovieScreenState(
	val movieId: String,
	val movieData: TitleInfo?,
	val isFavorite: Boolean?,
	val movieDataLoadingSuccessful: LoadingStatus,
	val notes: Flow<List<MovieNote>>,
	val selectedNotes: List<MovieNote>,
	val currentlyEditingNote: MovieNote?
) {
	companion object {
		fun initial(movieId: String) = MovieScreenState(
			movieId = movieId,
			movieData = null,
			isFavorite = null,
			movieDataLoadingSuccessful = LoadingStatus.Loading,
			notes = emptyFlow(),
			selectedNotes = emptyList(),
			currentlyEditingNote = null
		)
	}
}

internal sealed interface LoadingStatus {
	data object Loading : LoadingStatus
	data object Loaded : LoadingStatus
	data class Error(val information: String) : LoadingStatus
}

internal class MovieViewModel(
	private val initial: MovieScreenState,
	private val movieRepository: MovieDataSource,
	private val movieNoteDataSource: MovieNotesDataSource,
	private val favouritesRepository: FavoriteMovieRepository
) : MVIViewModel<MovieScreenState, MovieScreenIntent, MovieScreenEvent>(initial) {
	override suspend fun reduce(intent: MovieScreenIntent) {
		when (intent) {
			is MovieScreenIntent.LoadMovieData -> {
				state { MovieScreenState.initial(intent.id) }

				movieRepository.getMovieDataById(intent.id)
					.onSuccess {
						state {
							this.copy(
								movieData = it,
								movieDataLoadingSuccessful = LoadingStatus.Loaded
							)
						}
					}
					.onFailure {
						state {
							this.copy(movieDataLoadingSuccessful = LoadingStatus.Error(
								it.stackTrace.first().toString()
							)
							)
						}
					}
			}

			is MovieScreenIntent.LoadFavoriteStatus -> {
				favouritesRepository.getItemStream(intent.id).collect() {
					val isFavorite = it != null

					state { this.copy(isFavorite = isFavorite) }
				}
			}

			is MovieScreenIntent.AddNote -> {
				movieNoteDataSource.insertItem(
					MovieNote(
						uuid = UUID.randomUUID().toString(),
						movieId = intent.id,
						intent.note,
						System.currentTimeMillis()
					)
				)
			}

			is MovieScreenIntent.RemoveNote -> movieNoteDataSource.deleteItem(intent.note)

			is MovieScreenIntent.SetFavoriteStatus -> {
				favouritesRepository.getItemStream(intent.id).first().let {
					if (it != null) {
						favouritesRepository.deleteItem(it)
					} else {
						favouritesRepository.insertItem(
							Favorite(
								movieId = intent.id,
								title = intent.movieData.titleText.text,
								imageUrl = intent.movieData.primaryImage?.url ?: ""
							)
						)
					}

					val isFavorite = it != null
					state { this.copy(isFavorite = isFavorite) }
				}
			}

			is MovieScreenIntent.LoadNotes -> state {
				this.copy(notes = movieNoteDataSource.getAllItemsStreamByMovieId(intent.id))
			}

			is MovieScreenIntent.DeleteSelectedNotes -> state {
				scope.launch {
					selectedNotes.forEach() {
						movieNoteDataSource.deleteItem(it)
					}
				}

				this.copy(selectedNotes = emptyList())
			}

			is MovieScreenIntent.ClearSelectedNotes -> state {
				this.copy(selectedNotes = emptyList())
			}

			is MovieScreenIntent.SelectNoteForEditing -> state {
				this.copy(currentlyEditingNote = intent.note)
			}

			is MovieScreenIntent.SaveNoteEdits -> state {
				scope.launch {
					movieNoteDataSource.updateItem(
						this@state.currentlyEditingNote!!.copy(text = intent.newText)
					)
				}

				this.copy(currentlyEditingNote = null)
			}

			MovieScreenIntent.ExitNoteEditing -> state {
				this.copy(currentlyEditingNote = null)
			}

			is MovieScreenIntent.SwitchNoteSelection -> state {
				if (this.selectedNotes.contains(intent.note)) {
					this.copy(selectedNotes = this.selectedNotes - intent.note)
				} else {
					this.copy(selectedNotes = this.selectedNotes + intent.note)
				}
			}
		}
	}

	override fun CoroutineScope.onSubscribe() {
		Log.d("Movie MVI VM", "Subscribed")

		intent(MovieScreenIntent.LoadMovieData(initial.movieId))
		intent(MovieScreenIntent.LoadFavoriteStatus(initial.movieId))
		intent(MovieScreenIntent.LoadNotes(initial.movieId))
	}

	override fun onCleared() {
		Log.d("Movie MVI VM", "Unsubscribed")
	}
}

val movieVMModule = module {
	viewModel { parameter -> MovieViewModel(parameter.get(), get(), get(), get()) }
}