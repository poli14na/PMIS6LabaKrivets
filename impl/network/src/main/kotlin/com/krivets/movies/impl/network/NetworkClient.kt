package com.krivets.movies.impl.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

interface NetworkClient {
	val httpClient: HttpClient
}

internal class NetworkClientImpl : NetworkClient {
	private val baseUrl = "https://moviesdatabase.p.rapidapi.com"
	private val apiKeyHeader = "X-RapidAPI-Key"
	private val apiKey = "2a10997096mshcde78da4d258acbp1f7b76jsn4fd8e6282260"
	private val hostHeader = "X-RapidAPI-Host"

	override val httpClient = HttpClient(CIO) {
		install(ContentNegotiation) {
			json(
				json = Json {
					ignoreUnknownKeys = true
					coerceInputValues = true
				}
			)
		}

		defaultRequest {
			url(baseUrl)
			this.headers {
				header(apiKeyHeader, apiKey)
				header(hostHeader, host)
			}
		}

		install(Logging) {
			level = LogLevel.BODY
			logger = object : Logger {
				override fun log(message: String) {
					Log.d("HTTP Client", message)
				}
			}
		}
	}
}

val networkClientModule = module { single<NetworkClient> { NetworkClientImpl() } }