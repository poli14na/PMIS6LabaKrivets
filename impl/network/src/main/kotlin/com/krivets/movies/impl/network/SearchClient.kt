package com.krivets.movies.impl.network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import org.koin.dsl.module

interface SearchClient {
	suspend fun getSearchPage(searchQuery: String, page: Int? = null): ResponseTitlesPage?
}

internal class SearchClientImpl(networkClient: NetworkClient) : SearchClient {
	val httpClient = networkClient.httpClient

	override suspend fun getSearchPage(searchQuery: String, page: Int?): ResponseTitlesPage? {
		val pageParameter = if (page == null) "" else "?page=$page"
		val response = httpClient.get("/titles/search/keyword/$searchQuery$pageParameter")

		return if (response.status.isSuccess()) {
			response.body()
		} else {
			null
		}
	}
}

val searchClientModule = module { single<SearchClient> { SearchClientImpl(get()) } }