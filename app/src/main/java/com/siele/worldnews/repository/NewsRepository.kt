package com.siele.worldnews.repository

import com.siele.worldnews.api.NewsApi
import com.siele.worldnews.model.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getNewsTopNews(country:String): Response<NewsResponse> {
        return newsApi.getTopNews(country)
    }
    suspend fun getLatest(country:String): Response<NewsResponse> {
        return newsApi.getTopNews(country)
    }
}