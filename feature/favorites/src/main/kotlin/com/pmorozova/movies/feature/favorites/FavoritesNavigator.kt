package com.pmorozova.movies.feature.favorites

import androidx.compose.runtime.Immutable
import com.pmorozova.movies.domain.navigator.Navigator

interface FavoritesNavigator : Navigator {
	fun openMovie(id: String)
}