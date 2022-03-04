package com.venkat.sellquick.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.venkat.sellquick.data.model.Item

class SharedViewModel : ViewModel() {
    var cartItems = MutableLiveData<ArrayList<Item>>()

    init {
        cartItems.value = arrayListOf<Item>()
    }

    fun addItemToCart(item : Item){
        cartItems.value?.add(0,item)
    }

    fun removeItemFromCart(item:Item){
        cartItems.value?.remove(item)
    }



}