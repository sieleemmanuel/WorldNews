package com.siele.worldnews.utils

import android.os.Build
import com.siele.worldnews.data.model.Article
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

class GetElapsedTime {
    companion object{
        fun getElapsedTime(topArticle: Article): Long {
            val currentDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val currentDateSeconds = currentDateTime.toInstant().epochSecond

            val zonedDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ZonedDateTime.parse(topArticle.publishedAt)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val newsEpochSeconds = zonedDateTime.toInstant().epochSecond
            return currentDateSeconds.minus(newsEpochSeconds)
        }
    }
}