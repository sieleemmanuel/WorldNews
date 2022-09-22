package com.siele.worldnews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.data.model.BookmarkArticle
import com.siele.worldnews.data.model.NewsResponse
import com.siele.worldnews.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NewsRepository):ViewModel() {
    private val _topHeadline = MutableLiveData<NewsResponse>()
    val topHeadline: LiveData<NewsResponse> = _topHeadline

    private val _topGeneralNews = MutableLiveData<NewsResponse>()
    val topGeneralNews: LiveData<NewsResponse> = _topGeneralNews

    private val _topBusinessNews = MutableLiveData<NewsResponse>()
    val topBusinessNews: LiveData<NewsResponse> = _topBusinessNews

    private val _topEntertainmentNews = MutableLiveData<NewsResponse>()
    val topEntertainmentNews: LiveData<NewsResponse> = _topEntertainmentNews

    private val _topHealthNews = MutableLiveData<NewsResponse>()
    val topHealthNews: LiveData<NewsResponse> = _topHealthNews

    private val _topSportNews = MutableLiveData<NewsResponse>()
    val topSportNews: LiveData<NewsResponse> = _topSportNews

    private val _topScienceNews = MutableLiveData<NewsResponse>()
    val topScienceNews: LiveData<NewsResponse> = _topScienceNews

    private val _topTechnologyNews = MutableLiveData<NewsResponse>()
    val topTechnologyNews: LiveData<NewsResponse> = _topTechnologyNews

    private val _latestNews = MutableLiveData<NewsResponse>()
    val latestNews: LiveData<NewsResponse> = _latestNews

    private val _searchedNews = MutableLiveData<NewsResponse>()
    val searchedNews: LiveData<NewsResponse> = _searchedNews

    private val _isArticleBookmarked = MutableLiveData<Boolean?>(null)
    val isArticleBookmarked: LiveData<Boolean?> = _isArticleBookmarked

    private val TAG = MainViewModel::class.simpleName

    init {
        getTopHeadlineNews("us")
        getGeneralNews()
        getBusinessNews()
        getEntertainmentNews( )
        getHealthNews( )
        getSportsNews()
        getScienceNews()
        getTechnologyNews()
    }

    fun getTopHeadlineNews(country: String) {
        viewModelScope.launch {
            newsRepository.getNewsTopNews(country).collect { response ->
                if (response.isSuccessful){
                    _topHeadline.postValue(response.body())
                }
            }
        }
    }

    private fun getGeneralNews() {
        viewModelScope.launch {
            newsRepository.getTopNewByCategory(category ="general").collect { response ->
                if (response.isSuccessful) {
                    _topGeneralNews.postValue(response.body())
                }
            }
        }

    }

    private fun getBusinessNews() {
        viewModelScope.launch {
            newsRepository.getTopNewByCategory(category = "business").collect { response ->
                if (response.isSuccessful) {
                    _topBusinessNews.postValue(response.body())
                }
            }
        }
    }

    private fun getEntertainmentNews() {
        viewModelScope.launch {
            newsRepository.getTopNewByCategory(category = "entertainment").collect { response ->
                if (response.isSuccessful) {
                    _topEntertainmentNews.postValue(response.body())
                }
            }
        }
    }

    private fun getHealthNews() {
        viewModelScope.launch {
            newsRepository.getTopNewByCategory(category = "health").collect { response ->
                if (response.isSuccessful) {
                    _topHealthNews.postValue(response.body())
                }
            }
        }
    }

    private fun getSportsNews() {
        viewModelScope.launch {
            newsRepository.getTopNewByCategory(category = "sports").collect { response ->
                if (response.isSuccessful) {
                    _topSportNews.postValue(response.body())
                }
            }
        }
    }

    private fun getScienceNews() {
        viewModelScope.launch {
            newsRepository.getTopNewByCategory(category = "science").collect { response ->
                if (response.isSuccessful) {
                    _topScienceNews.postValue(response.body())
                }
            }
        }
    }

    private fun getTechnologyNews() {
        viewModelScope.launch {
            newsRepository.getTopNewByCategory(category = "technology").collect { response ->
                if (response.isSuccessful) {
                    _topTechnologyNews.postValue(response.body())
                }
            }
        }
    }

    fun getLatestNews(): Flow<PagingData<Article>> {
        return newsRepository.getStreamArticlesFromDb()
    }

    fun getSearchedNews(query: String) {
        viewModelScope.launch {
            newsRepository.searchNews(query).collect { response ->
                _searchedNews.postValue(response.body())
            }
        }
    }

    fun getBookmarkedNews() = newsRepository.getBookmarks()

    fun bookmarkAnArticle(bookmarkArticle: BookmarkArticle) {
                viewModelScope.launch {
                if (newsRepository.articleIsBookmarked(bookmarkArticle.title!!)) {
                    newsRepository.deleteBookmarked(bookmarkArticle.title)
                }else{
                    newsRepository.insertBookmarks(bookmarkArticle)
                }
        }
    }

    fun bookmarkAnArticle(article: Article, isBookmarked:Boolean) {
        viewModelScope.launch {
                newsRepository.bookmarkArticle(article, isBookmarked)
        }
    }
    fun isArticleInBookmark(article: Article): Boolean? {
        val bookmarked = MutableLiveData(false)
        viewModelScope.launch {
           bookmarked.postValue(newsRepository.articleIsBookmarked(article.title!!))
        }
        return bookmarked.value
    }


    override fun onCleared() {
        super.onCleared()
    }
}