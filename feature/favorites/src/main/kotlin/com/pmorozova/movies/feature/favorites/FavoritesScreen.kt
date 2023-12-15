@file:OptIn(ExperimentalMaterial3Api::class)

package com.pmorozova.movies.feature.favorites

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pmorozova.movies.common.models.ui.MovieItem
import com.pmorozova.movies.impl.arch.container
import com.pmorozova.movies.impl.arch.subscribe
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreen(navigator: FavoritesNavigator) {
	val vm = container<FavoritesViewModel, _, _, _> { parametersOf(FavoritesScreenState.initial) }
	val state by vm.subscribe { }

	val favorites by state.favorites.collectAsState(initial = emptyList())
	val favoritesListIsEmpty by remember { derivedStateOf { favorites.isEmpty() } }

	val selectedMovies by remember { derivedStateOf { state.selectedMovies } }
	val isInSelectionMode by remember { derivedStateOf { state.selectedMovies.isNotEmpty() } }

	var displayExitConfirmationDialog by remember { mutableStateOf(false) }

	BackHandler(enabled = isInSelectionMode) {
		displayExitConfirmationDialog = true
	}

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					if (isInSelectionMode)
						Text(text = "Выбрано ${selectedMovies.size} фильмов")
					else
						Text(text = "Любимые")
				},
				navigationIcon = {
					IconButton(
						onClick = {
							if (isInSelectionMode)
								displayExitConfirmationDialog = true
							else
								navigator.pop()
						}
					) {
						Icon(
							imageVector = Icons.Default.ArrowBack,
							contentDescription = "Назад"
						)
					}
				},
				actions = {
					if (isInSelectionMode) {
						IconButton(
							onClick = {
								vm.intent(FavoritesScreenIntent.RemoveSelectedItems)
							}
						) {
							Icon(
								Icons.Default.Delete,
								contentDescription = "Удалить выбранное"
							)
						}

						IconButton(
							onClick = { vm.intent(FavoritesScreenIntent.CleanListOfSelectedMovies) }
						) {
							Icon(
								Icons.Default.Clear,
								contentDescription = "Очистить выбранное"
							)
						}
					}
				}
			)
		}
	) { scaffoldPadding ->
		if (favoritesListIsEmpty) {
			Box(
				Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			) {
				Text(text = "Пусто :( Нужно что-то лайкнуть")
			}
		}

		Column(Modifier.padding(scaffoldPadding)) {
			LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
				items(favorites) {
					val isSelected by remember {
						derivedStateOf { selectedMovies.contains(it) }
					}

					val animatedBackground by animateColorAsState(
						if (isSelected)
							MaterialTheme.colorScheme.secondaryContainer
						else
							Color.Transparent,
						label = ""
					)

					MovieItem(
						Modifier
							.padding(horizontal = 16.dp)
							.clip(RoundedCornerShape(8.dp))
							.background(animatedBackground)
							.combinedClickable(
								onLongClick = {
									vm.intent(FavoritesScreenIntent.SwitchSelectionForMovieItem(it))
								},
								onClick = {
									if (isInSelectionMode)
										vm.intent(
											FavoritesScreenIntent.SwitchSelectionForMovieItem(
												it
											)
										)
									else
										navigator.openMovie(it.movieId)
								}
							),
						name = it.title,
						imageUrl = it.imageUrl
					)
				}
			}
		}
	}

	if (displayExitConfirmationDialog) AlertDialog(
		onDismissRequest = { displayExitConfirmationDialog = false },
		title = { Text(text = "Вы действительно хотите выйти?")},
		text = { Text(text = "Выход из экрана очистит список выбранных фильмов.") },
		confirmButton = {
			Button(
				onClick = {
					vm.intent(FavoritesScreenIntent.CleanListOfSelectedMovies)
					displayExitConfirmationDialog = false
					navigator.pop()
				}
			) {
				Text(text = "Да, выйти")
			}
		},
		dismissButton = {
			FilledTonalButton(onClick = { displayExitConfirmationDialog = false }) {
				Text(text = "Нет, остаться")
			}
		}
	)
}

