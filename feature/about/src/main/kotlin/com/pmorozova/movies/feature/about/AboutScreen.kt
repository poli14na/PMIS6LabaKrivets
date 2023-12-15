package com.pmorozova.movies.feature.about

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navigator: AboutNavigator) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("О приложении") },
				navigationIcon = {
					IconButton(onClick = { navigator.pop() }) {
						Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
					}
				}
			)
		}
	) { scaffoldPadding ->
		Column(
			Modifier
				.fillMaxSize()
				.padding(scaffoldPadding),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				"Movies",
				style = MaterialTheme.typography.displayLarge
			)

			Spacer(modifier = Modifier.height(8.dp))

			Text(
				"от Полины Морозовой",
				style = MaterialTheme.typography.headlineSmall
			)
		}
	}
}