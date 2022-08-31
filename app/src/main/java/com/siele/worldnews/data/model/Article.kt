package com.siele.worldnews.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.material.internal.Experimental
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = "articles_db_table")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: @RawValue Source?,
    val title: String?,
    val url: String,
    val urlToImage: String?
):Parcelable