package com.krivets.movies.impl.database

import com.krivets.movies.feature.favorites.FavoritesDataSource
import com.krivets.movies.feature.movie.FavoriteMovieDataSource
import com.krivets.movies.feature.movie.MovieDataSource
import com.krivets.movies.feature.movie.MovieNotesDataSource
import com.krivets.movies.feature.recommendations.RecommendationsDataSource
import com.krivets.movies.feature.search.SearchDataSource
import org.koin.dsl.module

val searchDataModule = module {
	single<SearchDataSource> {
		SearchRepositoryDataSourceImpl(
			SearchHistoryDatabase.getDatabase(get()).dao(),
			get()
		)
	}
}

val movieDataModule = module {
	single<MovieDataSource> { MovieDataSourceImpl(get()) }

	single<RecommendationsDataSource> { RecommendationsDataSourceImpl(get()) }
}

val movieNotesDataModule = module {
	single<MovieNotesDataSource> {
		MovieNotesDataSourceImpl(MovieNotesDatabase.getDatabase(get()).dao())
	}
}

val favoritesDataModule = module {
	single<FavoritesDataSource> {
		FavoritesDataSourceImpl(FavoritesDatabase.getDatabase(get()).dao())
	}

	single<FavoriteMovieDataSource> {
		FavoritesDataSourceImpl(FavoritesDatabase.getDatabase(get()).dao())
	}
}