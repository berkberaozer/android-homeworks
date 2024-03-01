package com.berkberaozer.hw2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Constants.TABLENAME)
class Show(
    var name: String,
    var score:Int,
    var date:String,
    var type:Int) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
    override fun toString(): String {
        return "Name: $name\nScore: $score\nWatch Date: $date"
    }
}