package com.siele.worldnews.di

import com.siele.worldnews.api.NewsApi
import com.siele.worldnews.api.RetrofitInstance
import com.siele.worldnews.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApiService(): NewsApi =  RetrofitInstance.retrofit.create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(newsApi: NewsApi): NewsRepository =  NewsRepository(newsApi)
}