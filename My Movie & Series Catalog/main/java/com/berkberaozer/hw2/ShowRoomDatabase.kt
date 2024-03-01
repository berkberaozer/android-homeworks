package com.berkberaozer.hw2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Show::class],
    version = 2,
    exportSchema = false
)
abstract class ShowRoomDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDAO

    companion object{
        @Volatile
        private var INSTANCE:ShowRoomDatabase?=null

        fun getDatabase(context:Context):ShowRoomDatabase{
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return  tempInstance
            }

            synchronized(this){
                val  instance =Room.databaseBuilder(context.applicationContext, ShowRoomDatabase::class.java, Constants.DATABASENAME).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
