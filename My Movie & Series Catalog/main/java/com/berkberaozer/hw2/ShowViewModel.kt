package com.berkberaozer.hw2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
it provides data to the UI and survive configuration changes. It acts as a communication center between repository and the UI
 */
class ShowViewModel(application:Application):AndroidViewModel(application) {
    val readAllData: LiveData<List<Show>>
    private val repository: ShowRepository
    init {
        val showDAO= ShowRoomDatabase.getDatabase(application).showDao()
        repository = ShowRepository(showDAO)
        readAllData = repository.readAlldata
    }
    fun addShow(show:Show){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.insertShow(show)
        }
    }
    fun addShows(shows: ArrayList<Show>){
        viewModelScope.launch(Dispatchers.IO) { // that code will be run in background thread, coroutine scope
            shows.forEach{
                repository.insertShow(it)
            }
        }
    }
    fun deleteShow(show:Show){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.deleteShow(show)
        }
    }
    fun deleteAllShows(){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.deleteAllShows()
        }
    }
    fun updateShow(show:Show){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            repository.updateShow(show)
        }
    }
    fun searchShow(searchkey:String):LiveData<List<Show>>{
            return repository.getShowsBySearchKey(searchkey).asLiveData()
    }
    fun getAllShows():LiveData<List<Show>>{
        return repository.getAllShow()
    }
    fun getCount():Int{
        return repository.getCount()
    }
}