package com.siele.worldnews.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siele.worldnews.data.model.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * FROM remote_key_table WHERE repoId =:id")
    suspend fun getRemoteKeys(id:String):RemoteKeys

    @Query("DELETE FROM remote_key_table")
    suspend fun clearRemoteKeys()
}