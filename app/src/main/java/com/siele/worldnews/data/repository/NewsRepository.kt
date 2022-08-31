package com.siele.worldnews.data.repository

import com.siele.worldnews.data.api.NewsApi
import com.siele.worldnews.data.database.NewsDao
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi, private val newsDao: NewsDao) {
    suspend fun getNewsTopNews(country:String): Flow<Response<NewsResponse>> {
        return flow {
            val topNews =  newsApi.getTopNews(country = country)
            emit(topNews)
        }
    }

    suspend fun getTopNewByCategory(category:String ="",country: String =""): Flow<Response<NewsResponse>>{
        return flow {
            emit(newsApi.getTopNews(category = category, country = country))
        }
    }

    suspend fun getLatest(country:String): Flow<Response<NewsResponse>> {
        return flow {
            val latestNews = newsApi.getLatest(category = country)
            emit(latestNews)
        }
    }

    suspend fun searchNews(query:String): Flow<Response<NewsResponse>> {
        return flow {
            val searchedNews = newsApi.searchNews(searchQuery = query)
            emit(searchedNews)
        }
    }
    fun getBookmarks() = newsDao.getArticles()
    suspend fun insertBookmarks(article: Article) =  newsDao.insertArticle(article)
    suspend fun deleteBookmarked(title:String) {
        return newsDao.deleteArticle(title)
    }
    fun articleIsBookmarked(title:String) = newsDao.articleExist(title)

}