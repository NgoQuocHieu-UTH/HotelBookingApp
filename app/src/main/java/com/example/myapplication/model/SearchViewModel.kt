package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.SearchInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.myapplication.model.Room
import com.example.myapplication.remote.RetrofitClient
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


private val _roomList = MutableStateFlow<List<Room>>(emptyList())
val roomList: StateFlow<List<Room>> = _roomList

class SearchViewModel : ViewModel() {

    var search by mutableStateOf(SearchInfo())
        private set

    fun updateSearch(newSearch: SearchInfo) {
        search = newSearch
    }

    fun updateHotelName(name: String) {
        search = search.copy(hotelName = name)
    }

    fun clearSearch() {
        search = SearchInfo()
    }
}
