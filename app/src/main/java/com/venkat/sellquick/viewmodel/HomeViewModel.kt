package com.venkat.sellquick.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkat.quickselldatabase.db.MainRepository
import com.venkat.quickselldatabase.db.models.Item
import com.venkat.quickselldatabase.db.models.Order
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: MainRepository) : ViewModel() {
    val items = repository.items
    val orders = repository.orders


    val cartItems = repository.cartItems

    fun insertItem(item: Item) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }


    fun deleteAllItems() {
        viewModelScope.launch {
            repository.deleteAllItems()
        }
    }

    fun addItemToCart(item: Item) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

    fun getOrderCount(item_id: Int): LiveData<Int> {
        return repository.getTotalOrdersOfItem(item_id)
    }

    fun getAllOrders(duration: String): LiveData<List<Order>> {
        return repository.getAllOrders(duration)
    }

    fun getItemIdAndOrderCountMap(orders: List<Order>): Map<Int, Int> {
        val map = hashMapOf<Int, Int>()
        for (order in orders) {
            Log.d("order", order.toString())
            Log.d("item_id", order.item_id.toString())
            if (!map.containsKey(order.item_id))
                map[order.item_id] = 1
            else
                map[order.item_id] = map[order.item_id]!! + 1

        }
        return map.toList().sortedBy { (_, value) -> value }.toMap()
    }

    fun calculateRank(selectedItem: Item, itemIdAndOrderCountMap: Map<Int, Int>): Int {
        val reversedItemIdList = itemIdAndOrderCountMap.keys.toList().reversed()

        val reversedOrderCountList = itemIdAndOrderCountMap.values.toList().reversed()
        val orderCountAndRankMap: MutableMap<Int, Int?> = HashMap()

        var rankOfEachItem = 1

        for (index in reversedOrderCountList.indices) {
            val element: Int = reversedOrderCountList.get(index)

            // Update rank of element
            if (orderCountAndRankMap[element] == null) {
                orderCountAndRankMap[element] = rankOfEachItem
                rankOfEachItem++
            }
        }
        val rankList = arrayListOf<Int?>()
        for (i in reversedOrderCountList.indices) {
            rankList.add(orderCountAndRankMap.get(reversedOrderCountList[i]))
        }
        val index = reversedItemIdList.indexOf(selectedItem.id)
        var finalRank = 0
        if (index >= 0 && rankList.isNotEmpty())
            finalRank = rankList.get(index) ?: 0

        return finalRank
    }

}

