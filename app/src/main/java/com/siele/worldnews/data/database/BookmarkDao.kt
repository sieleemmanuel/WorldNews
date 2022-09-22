package com.siele.worldnews.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.data.model.BookmarkArticle

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(bookmarkArticle: BookmarkArticle)

    @Query("SELECT * FROM bookmark_table")
    fun getArticles():LiveData<List<Article>>

    @Query("SELECT * FROM bookmark_table")
    fun getSavedArticles():List<BookmarkArticle>

    @Query("DELETE FROM bookmark_table WHERE title =:title")
    suspend fun deleteArticle(title:String)

    @Query("SELECT EXISTS(SELECT * FROM bookmark_table WHERE title =:title)")
    suspend fun articleExist(title: String):Boolean
}