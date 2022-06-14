package com.siele.worldnews.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: @RawValue Source?,
    val title: String,
    val url: String,
    val urlToImage: String
):Parcelable