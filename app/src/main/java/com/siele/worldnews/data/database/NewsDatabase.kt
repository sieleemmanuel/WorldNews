package com.siele.worldnews.data.database

import android.content.Context
import androidx.room.*
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.data.model.RemoteKeys

@Database(entities = [Article::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract val articlesDao: ArticlesDao
    abstract val remoteKeysDao: RemoteKeysDao
    abstract val bookmarkDao: BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null
        fun getInstance(context: Context): NewsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        NewsDatabase::class.java,
                        "bookmark_new_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}