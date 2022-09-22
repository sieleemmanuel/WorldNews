package com.siele.worldnews.data.api

import com.siele.worldnews.data.model.NewsResponse
import com.siele.worldnews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("country")
        country:String = "",
        @Query("language")
        language:String = "en",
        @Query("category")
        category:String = "",
        @Query("page")
        page:Int = 1,
        @Query("apiKey")
        apiKey:String = Constants.API_KEY
    ):Response<NewsResponse>

 /*   @GET("v2/top-headlines/sources")
    suspend fun getTopNewsByCategory(
        @Query("category")
        category:String = "",
        @Query("language")
        language:String = "en",
        @Query("apiKey")
        apiKey:String = Constants.API_KEY
    ):Response<NewsResponse>*/

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery:String = "",
        @Query("language")
        language:String = "en",
        @Query("apiKey")
        apiKey:String = Constants.API_KEY
    ):Response<NewsResponse>

    @GET("v2/latest")
    suspend fun getLatest(
        @Query("q")
        category:String = "",
        @Query("language")
        language:String = "en",
        @Query("apiKey")
        apiKey:String = Constants.API_KEY
    ):Response<NewsResponse>

}