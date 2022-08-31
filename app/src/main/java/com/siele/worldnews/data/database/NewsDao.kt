package com.siele.worldnews.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siele.worldnews.data.model.Article

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Query("SELECT * FROM articles_db_table")
    fun getArticles():LiveData<List<Article>>

    @Query("SELECT * FROM articles_db_table")
    fun getSavedArticles():List<Article>

    @Query("DELETE FROM articles_db_table WHERE title =:title")
    suspend fun deleteArticle(title:String)

    @Query("SELECT EXISTS(SELECT * FROM articles_db_table WHERE title =:title)")
    fun articleExist(title: String):LiveData<Boolean>
}