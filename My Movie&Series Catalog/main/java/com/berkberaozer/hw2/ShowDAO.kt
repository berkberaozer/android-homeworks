package com.berkberaozer.hw2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShow(show: Show)

    @Update
    fun updateShow(show: Show)

    @Delete
    fun deleteShow(show: Show)

    @Query("DELETE FROM ${Constants.TABLENAME}")
    fun deleteAllShows()

    @Query("SELECT * FROM ${Constants.TABLENAME} ORDER BY name ASC")
    fun getAllShows():LiveData<List<Show>>

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE id =:id")
    fun getShowById(id:Int):Show

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE name LIKE :searchKey")
    fun getShowsBySearchKey(searchKey:String): Flow<List<Show>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllShow(Shows: ArrayList<Show>){
        Shows.forEach{
            insertShow(it)
        }
    }

    @Query("SELECT COUNT(*) FROM ${Constants.TABLENAME}")
    fun getCount(): Int

}