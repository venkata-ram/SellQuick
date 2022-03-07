package com.venkat.sellquick.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.venkat.quickselldatabase.db.MainRepository
import java.lang.IllegalArgumentException

class CartViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CartViewModel::class.java)){
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}