package com.siele.worldnews.api

import com.siele.worldnews.model.NewsResponse
import com.siele.worldnews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("country")
        country:String = "kenya",
        @Query("apiKey")
        apiKey:String = Constants.API_KEY
    ):Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun searchNews(
        @Query("q")
        searchQuery:String = "",
        @Query("apiKey")
        apiKey:String = Constants.API_KEY
    ):Response<NewsResponse>

    @GET("v2/latest")
    suspend fun getLatest(
        @Query("q")
        category:String = "",
        @Query("apiKey")
        apiKey:String = Constants.API_KEY
    ):Response<NewsResponse>

}