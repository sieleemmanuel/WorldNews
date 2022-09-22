package com.siele.worldnews.di

import android.content.Context
import com.siele.worldnews.data.api.NewsApi
import com.siele.worldnews.data.api.RetrofitInstance
import com.siele.worldnews.data.database.BookmarkDao
import com.siele.worldnews.data.database.NewsDatabase
import com.siele.worldnews.data.database.RemoteKeysDao
import com.siele.worldnews.data.repository.NewsMediator
import com.siele.worldnews.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideRepository(newsApi: NewsApi, newsDatabase: NewsDatabase, newsMediator: NewsMediator): NewsRepository =  NewsRepository(newsApi, newsDatabase, newsMediator)

    @Singleton
    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context):NewsDatabase {
        return NewsDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase):BookmarkDao {
        return newsDatabase.bookmarkDao
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(newsDatabase: NewsDatabase):RemoteKeysDao {
        return newsDatabase.remoteKeysDao
    }

    @Singleton
    @Provides
    fun provideNewsMediator(newsDatabase: NewsDatabase, newsApi: NewsApi):NewsMediator {
        return NewsMediator(newsApi, newsDatabase)
    }

}