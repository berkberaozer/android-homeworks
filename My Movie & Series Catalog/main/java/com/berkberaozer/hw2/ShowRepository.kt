package com.berkberaozer.hw2

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow


/*
Used to access multiple data sources. It is used to seperate code and the architecture
 */
class ShowRepository(private val showDAO: ShowDAO) {
    val readAlldata:LiveData<List<Show>> = showDAO.getAllShows()

    fun insertShow(Show:Show){
        showDAO.insertShow(Show)
    }
    fun insertShows(Shows:ArrayList<Show>){
        showDAO.insertAllShow(Shows)
    }

    fun updateShow(Show: Show){
        showDAO.updateShow(Show)
    }

    fun deleteShow(Show: Show){
        showDAO.deleteShow(Show)
    }

    fun deleteAllShows(){
        showDAO.deleteAllShows()
    }

    fun getAllShow():LiveData<List<Show>>{
        return showDAO.getAllShows()
    }

    fun getShowById(id:Int):Show{
        return showDAO.getShowById(id)
    }
    fun getShowsBySearchKey(searchKey:String): Flow<List<Show>> {
        return showDAO.getShowsBySearchKey(searchKey)
    }

    fun getCount(): Int {
        return showDAO.getCount()
    }
}