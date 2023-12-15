package com.krivets.movies

import android.app.Application
import com.krivets.movies.data.favorites.favoritesRepositoryModule
import com.krivets.movies.data.movie.movieNotesRepositoryModule
import com.krivets.movies.data.movie.movieRepositoryModule
import com.krivets.movies.data.search.searchRepositoryModule
import com.krivets.movies.feature.favorites.favoriteVMModule
import com.krivets.movies.feature.movie.movieVMModule
import com.krivets.movies.feature.recommendations.recommendationsVMModule
import com.krivets.movies.impl.database.favoritesDataModule
import com.krivets.movies.impl.database.movieDataModule
import com.krivets.movies.impl.database.movieNotesDataModule
import com.krivets.movies.impl.database.searchDataModule
import com.krivets.movies.impl.network.moviesClientModule
import com.krivets.movies.impl.network.networkClientModule
import com.krivets.movies.impl.network.searchClientModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@App)
			modules(
				favoritesDataModule,
				favoritesRepositoryModule,
				movieDataModule,
				movieRepositoryModule,
				searchDataModule,
				searchRepositoryModule,
				movieNotesDataModule,
				movieNotesRepositoryModule,

				networkClientModule,
				searchClientModule,
				moviesClientModule,

				recommendationsVMModule,
				movieVMModule,
				favoriteVMModule,
			)
		}
	}
}