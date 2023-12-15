package com.krivets.movies.impl.database

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.krivets.movies.common.models.Genres
import com.krivets.movies.common.models.ImageCaption
import com.krivets.movies.common.models.ImageData
import com.krivets.movies.common.models.ReleaseDate
import com.krivets.movies.common.models.ReleaseYear
import com.krivets.movies.common.models.SearchHistoryItem
import com.krivets.movies.common.models.TitleInfo
import com.krivets.movies.common.models.TitleText
import com.krivets.movies.common.models.TitleType
import com.krivets.movies.common.models.TitlesPage
import com.krivets.movies.feature.search.SearchDataSource
import com.krivets.movies.impl.network.ResponseGenres
import com.krivets.movies.impl.network.ResponseImageCaption
import com.krivets.movies.impl.network.ResponseImageData
import com.krivets.movies.impl.network.ResponseReleaseDate
import com.krivets.movies.impl.network.ResponseReleaseYear
import com.krivets.movies.impl.network.ResponseTitleInfo
import com.krivets.movies.impl.network.ResponseTitleText
import com.krivets.movies.impl.network.ResponseTitleType
import com.krivets.movies.impl.network.ResponseTitlesPage
import com.krivets.movies.impl.network.SearchClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SearchRepository(
	private val searchClient: SearchClient,
	private val searchHistoryDao: SearchHistoryDao
) : SearchHistoryRepository {
	suspend fun getSearchPage(searchQuery: String, page: Int? = null) =
		searchClient.getSearchPage(searchQuery, page)

	override fun getAllItemsStream(): Flow<List<DatabaseSearchHistoryItem>> = searchHistoryDao.getAllItems()

	override fun getItemStream(id: String): Flow<DatabaseSearchHistoryItem?> = searchHistoryDao.getItem(id)

	override suspend fun insertItem(item: DatabaseSearchHistoryItem) = searchHistoryDao.insert(item)

	override suspend fun deleteItem(item: DatabaseSearchHistoryItem) = searchHistoryDao.delete(item)

	override suspend fun updateItem(item: DatabaseSearchHistoryItem) = searchHistoryDao.update(item)
}

interface SearchHistoryRepository {
	fun getAllItemsStream(): Flow<List<DatabaseSearchHistoryItem>>

	fun getItemStream(id: String): Flow<DatabaseSearchHistoryItem?>

	suspend fun insertItem(item: DatabaseSearchHistoryItem)

	suspend fun deleteItem(item: DatabaseSearchHistoryItem)

	suspend fun updateItem(item: DatabaseSearchHistoryItem)
}

@Database(entities = [DatabaseSearchHistoryItem::class], version = 1)
internal abstract class SearchHistoryDatabase : RoomDatabase() {
	abstract fun dao(): SearchHistoryDao

	companion object {
		@Volatile
		private var Instance: SearchHistoryDatabase? = null
		fun getDatabase(context: Context): SearchHistoryDatabase {
			return Instance ?: synchronized(this) {
				Room.databaseBuilder(
					context,
					SearchHistoryDatabase::class.java,
					"search_history"
				).build()
			}.also { Instance = it }
		}
	}
}

@Dao
interface SearchHistoryDao {
	@Insert
	suspend fun insert(item: DatabaseSearchHistoryItem)

	@Update
	suspend fun update(item: DatabaseSearchHistoryItem)

	@Delete
	suspend fun delete(item: DatabaseSearchHistoryItem)

	@Query("SELECT * from search_history WHERE id = :id")
	fun getItem(id: String): Flow<DatabaseSearchHistoryItem?>

	@Query("SELECT * from search_history ORDER BY time_of_creation DESC")
	fun getAllItems(): Flow<List<DatabaseSearchHistoryItem>>
}

@Entity(tableName = "search_history")
data class DatabaseSearchHistoryItem(
	@PrimaryKey val id: String,
	@ColumnInfo(name = "title") val title: String,
	@ColumnInfo("time_of_creation") val timeOfCreation: Long
)

internal class SearchRepositoryDataSourceImpl(
	private val searchHistoryDao: SearchHistoryDao,
	private val searchClient: SearchClient
) : SearchDataSource {
	// Search history methods
	override fun getAllItemsStream(): Flow<List<SearchHistoryItem>> =
		searchHistoryDao.getAllItems().map { it.map { dbSearchHistoryItem -> dbSearchHistoryItem.toCommonSearchHistoryItem() } }

	override fun getItemStream(id: String): Flow<SearchHistoryItem?> =
		searchHistoryDao.getItem(id).map { it?.toCommonSearchHistoryItem() }

	override suspend fun insertItem(item: SearchHistoryItem) =
		searchHistoryDao.insert(item.toDbSearchHistoryItem())

	override suspend fun deleteItem(item: SearchHistoryItem) =
		searchHistoryDao.delete(item.toDbSearchHistoryItem())

	override suspend fun updateItem(item: SearchHistoryItem) =
		searchHistoryDao.update(item.toDbSearchHistoryItem())

	private fun SearchHistoryItem.toDbSearchHistoryItem(): DatabaseSearchHistoryItem =
		DatabaseSearchHistoryItem(id, title, timeOfCreation)

	private fun DatabaseSearchHistoryItem.toCommonSearchHistoryItem(): SearchHistoryItem =
		SearchHistoryItem(id, title, timeOfCreation)

	private fun ResponseTitleInfo.toCommonTitleInfo(): TitleInfo =
		TitleInfo(
			id,
			primaryImage?.toCommonImageData(),
			titleType.toCommonTitleType(),
			titleText.toCommonTitleText(),
			originalTitleText.toCommonTitleText(),
			releaseYear?.toCommonReleaseYear(),
			releaseDate?.toCommonReleaseDate()
		)

	private fun ResponseImageData.toCommonImageData(): ImageData =
		ImageData(id, width, height, url, caption.toCommonImageCaption())

	private fun ResponseImageCaption.toCommonImageCaption(): ImageCaption =
		ImageCaption(plainText)

	private fun ResponseTitleType.toCommonTitleType(): TitleType =
		TitleType(text, id, isSeries, isEpisode)

	private fun ResponseTitleText.toCommonTitleText(): TitleText =
		TitleText(text)

	private fun ResponseReleaseYear.toCommonReleaseYear(): ReleaseYear =
		ReleaseYear(year, endYear)

	private fun ResponseReleaseDate.toCommonReleaseDate(): ReleaseDate =
		ReleaseDate(day, month, year)

	private fun ResponseGenres.toCommonGenres(): Genres =
		Genres(results)

	private fun ResponseTitlesPage.toCommonTitlesPage(): TitlesPage =
		TitlesPage(page, next, entries, results.map { it.toCommonTitleInfo() })

	override suspend fun getSearchPage(
		searchQuery: String, page: Int?
	): TitlesPage? = searchClient.getSearchPage(searchQuery, page)?.toCommonTitlesPage()
}