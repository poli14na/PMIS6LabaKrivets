package com.pmorozova.movies.feature.recommendations

import com.pmorozova.movies.domain.navigator.Navigator

interface RecommendationsNavigator : Navigator {
	fun openMovie(id: String)
	fun openAboutPage()
}