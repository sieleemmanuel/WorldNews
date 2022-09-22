package com.siele.worldnews.data.repository

import androidx.paging.*
import com.siele.worldnews.data.api.NewsApi
import com.siele.worldnews.data.database.NewsDatabase
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.data.model.BookmarkArticle
import com.siele.worldnews.data.model.NewsResponse
import com.siele.worldnews.utils.Constants.Companion.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase,
    private val newsMediator: NewsMediator
    ) {

    private fun getDefaultPagingConfig():PagingConfig{
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getStreamArticlesFromDb(pagingConfig: PagingConfig = getDefaultPagingConfig()):Flow<PagingData<Article>> {
        val pagingSourceFactory = {newsDatabase.articlesDao.getAllArticles()}
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = newsMediator
        ).flow
    }


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
    fun getBookmarks() = newsDatabase.bookmarkDao.getArticles()
    suspend fun insertBookmarks(bookmarkArticle: BookmarkArticle) =  newsDatabase.bookmarkDao.insertArticle(bookmarkArticle)
    suspend fun deleteBookmarked(title:String) = newsDatabase.bookmarkDao.deleteArticle(title)

    suspend fun articleIsBookmarked(title:String) = newsDatabase.bookmarkDao.articleExist(title)
    suspend fun bookmarkArticle(article: Article, isBookmark: Boolean) = article.id?.let {
        newsDatabase.articlesDao.update(
            it, isBookmark)
    }

}