package com.siele.worldnews.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.data.model.Source
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
internal class NewsDatabaseTest:TestCase(){

    private lateinit var articleDb:NewsDatabase
    private lateinit var articleDao:NewsDao
    private val TAG = NewsDatabaseTest::class.simpleName

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        articleDb = Room.inMemoryDatabaseBuilder(context,NewsDatabase::class.java).build()
        articleDao = articleDb.newsDao
    }

    @Test
    fun writeAndReadArticle() = runBlocking {
       val article =  Article(
            id = 1,
            author = "Author",
            content = "This is brief content description",
            description =  "Description",
            publishedAt = "June 06, 2022",
            source =  Source(null,"bbc"),
            title = "Lorem ipsum News Article Title/Header goes here",
            url =  "Url String",
            urlToImage =  "https://picsum.photos/140"
        )
        articleDao.insertArticle(article)
        val savedArticles = articleDao.getSavedArticles()
        assertTrue(savedArticles.contains(article))

    }

    @After
    fun closeDb(){
        articleDb.close()
    }
}