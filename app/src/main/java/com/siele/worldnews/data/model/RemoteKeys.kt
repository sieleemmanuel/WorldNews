package com.siele.worldnews.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key_table")
data class RemoteKeys(
    @PrimaryKey val repoId:String,
    val preKey:Int?,
    val nextKey:Int?
)
