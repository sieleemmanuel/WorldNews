package com.siele.worldnews.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.siele.worldnews.data.api.NewsApi
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.utils.Constants.Companion.DEFAULT_PAGE_INDEX
import retrofit2.HttpException
import javax.inject.Inject

class NewsPagingSource @Inject constructor(private val newsApi:NewsApi):PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
       return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key?:DEFAULT_PAGE_INDEX
        return try {
            val response = newsApi.getTopNews(page = page).body()?.articles
            LoadResult.Page(
                data = response!!,
                prevKey = if(page == DEFAULT_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        }catch (exception:Exception){
            LoadResult.Error(exception)
        }
        catch (httpException:HttpException){
            LoadResult.Error(httpException)
        }
    }
}