@file:OptIn(ExperimentalMaterial3Api::class)

package com.pmorozova.movies.feature.movie

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pmorozova.movies.impl.arch.container
import com.pmorozova.movies.impl.arch.subscribe
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieScreen(id: String, navigator: MovieNavigator) {
	val context = LocalContext.current
	val vm = container<MovieViewModel, _, _, _> { parametersOf(MovieScreenState.initial(id)) }
	val state by vm.subscribe {}

	val movieData by remember { derivedStateOf { state.movieData } }
	val title by remember { derivedStateOf { movieData?.titleText?.text } }
	val isFavorite by remember { derivedStateOf { state.isFavorite } }
	val notes by state.notes.collectAsState(initial = emptyList())
	val selectedNotes by remember { derivedStateOf { state.selectedNotes } }
	val isInNoteSelectionMode by remember { derivedStateOf { selectedNotes.isNotEmpty() } }
	val isInNoteEditingMode by remember { derivedStateOf { state.currentlyEditingNote != null } }

	val loading by remember {
		derivedStateOf {
			state.movieDataLoadingSuccessful !== LoadingStatus.Loaded
		}
	}

	var displayExitConfirmationDialog by remember { mutableStateOf(false) }

	BackHandler(enabled = isInNoteSelectionMode) {
		displayExitConfirmationDialog = true
	}

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					if (isInNoteSelectionMode) Text("Выбрано ${selectedNotes.size} заметок")
				},
				navigationIcon = {
					IconButton(
						onClick = {
							if (isInNoteSelectionMode || isInNoteEditingMode)
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
					if (isInNoteSelectionMode) {
						IconButton(
							onClick = {
								vm.intent(MovieScreenIntent.DeleteSelectedNotes)

								Toast.makeText(
									context,
									"Заметки успещно удалены",
									Toast.LENGTH_SHORT
								).show()
							}
						) {
							Icon(
								Icons.Default.Delete,
								contentDescription = "Удалить выбранное"
							)
						}

						IconButton(
							onClick = { vm.intent(MovieScreenIntent.ClearSelectedNotes) }
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
		Box(modifier = Modifier
			.fillMaxSize()
			.padding(scaffoldPadding)) {
			if (loading) {
				Box(
					modifier = Modifier.fillMaxSize(),
					contentAlignment = Alignment.Center
				) {
					CircularProgressIndicator()
				}
			} else {
				val imageModel = ImageRequest.Builder(context)
					.data(movieData?.primaryImage?.url)
					.build()

				LazyColumn(
					verticalArrangement = Arrangement.spacedBy(8.dp),
					modifier = Modifier.imePadding()
				) {
					item {
						Column(
							Modifier
								.fillMaxWidth()
								.padding(horizontal = 16.dp),
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							Box(
								Modifier.align(Alignment.CenterHorizontally),
								contentAlignment = Alignment.Center
							) {
								Icon(
									Icons.Default.Star,
									contentDescription = null
								)
								AsyncImage(
									model = imageModel,
									contentDescription = title!!,
									modifier = Modifier.size(256.dp)
								)
							}

							Spacer(modifier = Modifier.height(8.dp))

							Text(
								text = title!!,
								style = MaterialTheme.typography.headlineSmall,
								textAlign = TextAlign.Center
							)

							IconButton(
								onClick = {
									vm.intent(
										MovieScreenIntent.SetFavoriteStatus(
											id,
											state.movieData!!
										)
									)

									val toastText = if (isFavorite == true)
										"$title удален из списка любимых"
									else
										"$title внесен в список любимых"

									Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
								}
							) {
								Icon(
									imageVector = if (isFavorite == true)
										Icons.Default.Favorite
									else
										Icons.Default.FavoriteBorder,
									contentDescription = "Понравилось"
								)
							}

							Spacer(modifier = Modifier.height(16.dp))

							Column {
								Text(
									text = "Заметки",
									style = MaterialTheme.typography.titleSmall
								)

								Spacer(modifier = Modifier.height(8.dp))

								var newNoteText by remember { mutableStateOf("") }

								Row(
									Modifier.fillMaxWidth(),
									horizontalArrangement = Arrangement.SpaceBetween
								) {
									TextField(
										value = newNoteText,
										onValueChange = { newNoteText = it },
										placeholder = { Text(text = "Напишите новую заметку") }
									)

									FilledIconButton(
										onClick = {
											vm.intent(MovieScreenIntent.AddNote(id, newNoteText))
											newNoteText = ""
										}
									) {
										Icon(
											imageVector = Icons.Default.Add,
											contentDescription = "Добавить"
										)
									}
								}
							}
						}
					}

					items(notes, key = { it.uuid }) {
						val isInEditMode by remember {
							derivedStateOf { state.currentlyEditingNote?.uuid == it.uuid }
						}
						val isSelected by remember {
							derivedStateOf {
								state.selectedNotes.contains(it)
							}
						}
						var dropDownMenuIsDisplayed by remember { mutableStateOf(false) }

						var editedText by remember { mutableStateOf(it.text) }

						Column {
							val animateBackgroundColor by animateColorAsState(
								if (isInEditMode || isSelected)
									MaterialTheme.colorScheme.primaryContainer
								else
									MaterialTheme.colorScheme.surfaceContainer,
								label = ""
							)

							Box(
								Modifier
									.padding(horizontal = 16.dp)
									.clip(RoundedCornerShape(8.dp))
									.background(animateBackgroundColor)
									.fillMaxWidth()
									.heightIn(min = 64.dp)
									.combinedClickable(
										onClick = {
											if (isInNoteSelectionMode)
												vm.intent(MovieScreenIntent.SwitchNoteSelection(it))
										},
										onLongClick = { dropDownMenuIsDisplayed = true }
									),
							) {
								Box(Modifier.padding(8.dp)) {
									if (isInEditMode) {
										BasicTextField(
											value = editedText,
											onValueChange = { editedText = it },
											textStyle = MaterialTheme.typography.bodyLarge,
											modifier = Modifier.heightIn(min = 56.dp).fillMaxWidth()
										)
									} else {
										Text(
											text = it.text,
											style = MaterialTheme.typography.bodyLarge
										)
									}

									DropdownMenu(
										expanded = dropDownMenuIsDisplayed,
										onDismissRequest = { dropDownMenuIsDisplayed = false }
									) {
										DropdownMenuItem(
											text = { Text("Выбрать") },
											onClick = {
												vm.intent(MovieScreenIntent.SwitchNoteSelection(it))
												dropDownMenuIsDisplayed = false
											}
										)
										DropdownMenuItem(
											text = { Text("Изменить") },
											onClick = {
												vm.intent(MovieScreenIntent.SelectNoteForEditing(it))
												dropDownMenuIsDisplayed = false
											}
										)
										DropdownMenuItem(
											text = { Text("Удалить") },
											onClick = {
												vm.intent(MovieScreenIntent.RemoveNote(it))
												dropDownMenuIsDisplayed = false

												Toast.makeText(
													context,
													"Заметка успешно удалена",
													Toast.LENGTH_SHORT
												).show()
											}
										)
									}
								}
							}

							AnimatedVisibility(visible = isInEditMode) {
								Row(
									Modifier
										.fillMaxWidth()
										.padding(horizontal = 16.dp, vertical = 4.dp),
									horizontalArrangement = Arrangement.End
								) {
									FilledTonalButton(
										onClick = {
											vm.intent(MovieScreenIntent.SaveNoteEdits(editedText))
											Toast.makeText(
												context,
												"Изменения сохранены успещно",
												Toast.LENGTH_SHORT
											).show()
										}
									) {
										Icon(Icons.Default.Done, contentDescription = null)
										Text(text = "Сохранить")
									}
								}
							}
						}
					}
				}
			}
		}
	}

	if (state.movieDataLoadingSuccessful is LoadingStatus.Error) AlertDialog(
		onDismissRequest = { navigator.pop() },
		icon = { Icon(Icons.Default.Warning, contentDescription = "Ошибка") },
		title = { Text(text = "Произошла ошибка при загрузке") },
		text = {
			   Text(text = (state.movieDataLoadingSuccessful as LoadingStatus.Error).information)
		},
		confirmButton = {
			Button(onClick = { vm.intent(MovieScreenIntent.LoadMovieData(id)) }) {
				Text(text = "Попробовать снова")
			}
		}
	)

	if (displayExitConfirmationDialog) AlertDialog(
		onDismissRequest = { displayExitConfirmationDialog = false },
		title = { Text(text = "Вы действительно хотите выйти?")},
		text = { Text(text = "Все внесённые изменения будут удалены.") },
		confirmButton = {
			Button(
				onClick = {
					vm.intent(MovieScreenIntent.ClearSelectedNotes)
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