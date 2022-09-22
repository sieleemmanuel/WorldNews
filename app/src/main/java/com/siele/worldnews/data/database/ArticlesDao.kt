package com.siele.worldnews.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siele.worldnews.data.model.Article

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles:List<Article>)

    @Query("SELECT * FROM ARTICLES_DB_TABLE")
    fun getAllArticles():PagingSource<Int, Article>

    @Query("UPDATE articles_db_table SET isBookmarked=:bookmark WHERE id =:id")
    suspend fun update(id:Int, bookmark: Boolean)

    @Query("DELETE FROM ARTICLES_DB_TABLE")
    suspend fun clearAllArticles()

}
