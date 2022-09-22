package com.siele.worldnews.utils

import com.siele.worldnews.BuildConfig
import com.siele.worldnews.data.model.Article

class Constants {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://newsapi.org"
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 50
    }
}