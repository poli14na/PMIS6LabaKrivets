package com.pmorozova.movies.feature.search

import com.pmorozova.movies.domain.navigator.Navigator

interface SearchNavigator : Navigator {
	fun openMovie(id: String)
}