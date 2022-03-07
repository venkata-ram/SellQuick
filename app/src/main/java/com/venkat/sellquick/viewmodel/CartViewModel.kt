package com.venkat.sellquick.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkat.quickselldatabase.db.MainRepository
import com.venkat.quickselldatabase.db.models.Item
import com.venkat.quickselldatabase.db.models.Order
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CartViewModel(private val repository: MainRepository) : ViewModel(){

    val cartItems = repository.cartItems

    fun removeItemFromCart(item:Item){
        val itemToRemove = Item(item.id,item.name,item.price,false)
        viewModelScope.launch {
            repository.updateItem(itemToRemove)
        }
    }

    fun orderItems(order: Order){
        viewModelScope.launch {
            repository.insertOrders(order)
        }
    }

    fun placeOrder() : Boolean{
        val cartItems = cartItems.value
        val currentTime = getCurrentTime()
        if (cartItems != null) {
            for(cartItem in cartItems){
                val order = Order(0,cartItem.id,currentTime)
                orderItems(order)
                removeItemFromCart(cartItem)
            }
            return true
        }
        return false
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }
}