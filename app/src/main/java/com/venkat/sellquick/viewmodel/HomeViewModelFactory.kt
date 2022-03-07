package com.venkat.sellquick.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.venkat.quickselldatabase.db.MainRepository
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}