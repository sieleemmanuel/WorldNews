package com.siele.worldnews.utils

import com.siele.worldnews.BuildConfig
import com.siele.worldnews.model.Article

class Constants {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://newsapi.org"

        val dummyNews = listOf(
            Article(
            "Author",
            "This is brief content description",
            "Description",
            "June 06, 2022",
            null,
            "Lorem ipsum News Article Title/Header goes here",
            "Url String",
            "https://picsum.photos/140"
        ),
            Article(
                "Author1",
                "This is brief content description1",
                "Description1",
                "June 06, 2022",
                null,
                "Lorem ipsum News Article Title/Header goes here",
                "Url String1",
                "https://picsum.photos/140"
            ),
            Article(
                "Author2",
                "This is brief content description1",
                "Description1",
                "June 06, 2022",
                null,
                "Lorem ipsum News Article Title/Header goes here",
                "Url String1",
                "https://picsum.photos/140"
            ),
            Article(
                "Author2",
                "This is brief content description2",
                "Description1",
                "June 06, 2022",
                null,
                "Lorem ipsum News Article Title/Header goes here",
                "Url String1",
                "https://picsum.photos/140"
            )

        )
    }
}