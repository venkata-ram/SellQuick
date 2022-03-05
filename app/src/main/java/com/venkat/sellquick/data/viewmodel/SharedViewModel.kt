package com.venkat.sellquick.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.venkat.sellquick.data.model.Item

class SharedViewModel : ViewModel() {
    private var _cartItems = MutableLiveData<ArrayList<Item>>()
    val cartItems :LiveData<ArrayList<Item>>
        get() = _cartItems

    init {
        _cartItems.value = arrayListOf<Item>()
    }

    fun addItemToCart(item : Item){
        _cartItems.value?.add(0,item)
        _cartItems.value = _cartItems.value
    }

    fun removeItemFromCart(item:Item){
        _cartItems.value?.remove(item)
        _cartItems.value = _cartItems.value
    }



}