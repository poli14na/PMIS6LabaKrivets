package com.krivets.movies.impl.navigation

import android.os.Parcelable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.krivets.movies.feature.about.AboutNavigator
import com.krivets.movies.feature.about.AboutScreen
import com.krivets.movies.feature.favorites.FavoritesNavigator
import com.krivets.movies.feature.favorites.FavoritesScreen
import com.krivets.movies.feature.movie.MovieNavigator
import com.krivets.movies.feature.recommendations.RecommendationsScreen
import com.krivets.movies.feature.movie.MovieScreen
import com.krivets.movies.feature.recommendations.RecommendationsNavigator
import com.krivets.movies.feature.search.SearchNavigator
import com.krivets.movies.ui.SearchScreen
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.pop
import dev.olshevski.navigation.reimagined.rememberNavController
import kotlinx.parcelize.Parcelize

internal class NavigatorImpl(private val navController: NavController<Destinations>):
	FavoritesNavigator,
	RecommendationsNavigator,
	MovieNavigator,
	SearchNavigator,
	AboutNavigator
{
	override fun openMovie(id: String) = navController.navigate(Destinations.Movie(id))

	override fun openAboutPage() = navController.navigate(Destinations.About)

	override fun pop(): Boolean = navController.pop()
}

@Composable
fun Navigation() {
	val navController = rememberNavController<Destinations>(
		startDestination = Destinations.Recommendations
	)
	val currentDestination by remember {
		derivedStateOf {
			navController.backstack.entries.last().destination
		}
	}

	NavBackHandler(navController)

	Scaffold(
		bottomBar = {
			NavigationBar {
				NavigationBarItem(
					selected = currentDestination == Destinations.Recommendations,
					onClick = { navController.navigate(Destinations.Recommendations)},
					icon = {
						Icon(
							imageVector = Icons.Default.Home,
							contentDescription = "Домашняя страница"
						)
					}
				)

				NavigationBarItem(
					selected = currentDestination == Destinations.Search,
					onClick = { navController.navigate(Destinations.Search) },
					icon = {
						Icon(
							imageVector = Icons.Default.Search,
							contentDescription = "Поиск"
						)
					}
				)

				NavigationBarItem(
					selected = currentDestination == Destinations.Favorites,
					onClick = {  navController.navigate(Destinations.Favorites) },
					icon = {
						Icon(
							imageVector = Icons.Default.Favorite,
							contentDescription = "Любимые"
						)
					}
				)
			}
		},
	) { paddingValues ->
		NavHost(
			navController,
			modifier = Modifier.padding(paddingValues)
		) {destination ->
			when (destination) {
				is Destinations.Recommendations -> RecommendationsScreen(NavigatorImpl(navController))
				is Destinations.Search -> SearchScreen(NavigatorImpl(navController))
				is Destinations.Favorites -> FavoritesScreen(NavigatorImpl(navController))
				is Destinations.Movie -> MovieScreen(id = destination.id, NavigatorImpl(navController))
				is Destinations.About -> AboutScreen(NavigatorImpl(navController))
			}
		}
	}
}

internal sealed class Destinations(val name: String) : Parcelable {
	@Parcelize
	data object Recommendations : Destinations("Рекоммендации")

	@Parcelize
	data object Search : Destinations("Поиск")

	@Parcelize
	data object Favorites : Destinations("Любимые")

	@Parcelize
	data class Movie(val id: String) : Destinations("")

	@Parcelize
	data object About : Destinations("О приложении")
}