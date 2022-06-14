package com.siele.worldnews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siele.worldnews.model.NewsResponse
import com.siele.worldnews.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NewsRepository):ViewModel() {
    private val _topHeadline = MutableLiveData<Response<NewsResponse>>()
    val topHeadline: LiveData<Response<NewsResponse>> = _topHeadline

    private val _latestNews = MutableLiveData<Response<NewsResponse>>()
    val latestNews: LiveData<Response<NewsResponse>> = _latestNews

    fun getTopHeadlineNews(country: String) {
        viewModelScope.launch {
           _topHeadline.postValue(newsRepository.getNewsTopNews(country))
        }

    }

    fun getLatestNews(country: String) {
        viewModelScope.launch {
            _latestNews.postValue(newsRepository.getLatest(country))
        }
    }
}