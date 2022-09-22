package com.siele.worldnews.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.siele.worldnews.data.api.NewsApi
import com.siele.worldnews.data.database.NewsDatabase
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.data.model.RemoteKeys
import com.siele.worldnews.utils.Constants.Companion.DEFAULT_PAGE_INDEX
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsMediator @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase,
   ):RemoteMediator<Int, Article>() {
    /*override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
*/
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        val page = when(val pageKeyData = getPageKeyData(loadType, state)){
            is MediatorResult.Success ->{
                return pageKeyData
            }
            else ->{
                pageKeyData as Int
            }
        }

        try {
            val response = newsApi.getTopNews(page = page, country = "us").body()?.articles
            val isEndOfTheList = response?.isEmpty()
            newsDatabase.withTransaction {
                if (loadType== LoadType.REFRESH){
                    newsDatabase.apply{
                        remoteKeysDao.clearRemoteKeys()
                        articlesDao.clearAllArticles()
                    }
                }
                val preKey = if (page == DEFAULT_PAGE_INDEX) null else page.minus(1)
                val nextKey = if (isEndOfTheList!!) null else page.plus(1)

                val keys = response.map {
                    RemoteKeys(repoId = it.id.toString(), preKey = preKey, nextKey = nextKey)
                }
                newsDatabase.apply {
                    remoteKeysDao.insertAll(keys)
                    articlesDao.insertAll(response)
                }

            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfTheList!!)
        }catch (exception:Exception){
            return MediatorResult.Error(exception)
        }catch (httpException: HttpException){
            return MediatorResult.Error(httpException)
        }

    }

    private suspend fun getPageKeyData(loadType: LoadType, state: PagingState<Int, Article>): Any? {
        return when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1)?:DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND ->{
                val remoteKeys = getLastRemoteKey(state) /*?: throw InvalidObjectException("Remote key should not be null for $loadType")*/
               return remoteKeys?.nextKey?:MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND ->{
                //return MediatorResult.Success(endOfPaginationReached = true)
                val remoteKeys = getFirstRemoteKey(state) /*?:
                throw InvalidObjectException("Invalid state, key should not be null")*/
                remoteKeys?.preKey ?:return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.preKey
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Article>): RemoteKeys? {
       return  state.pages
           .lastOrNull{it.data.isNotEmpty()}
           ?.data?.lastOrNull()
           ?.let { article -> newsDatabase.remoteKeysDao.getRemoteKeys(article.id.toString()) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Article>): RemoteKeys? {
        return state.pages
            .firstOrNull{it.data.isNotEmpty()}
            ?.data?.firstOrNull()
            ?.let { article -> newsDatabase.remoteKeysDao.getRemoteKeys(article.id.toString()) }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, Article>): RemoteKeys? {
        return  state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId->
                newsDatabase.remoteKeysDao.getRemoteKeys(repoId.toString())
            }
        }
    }
}