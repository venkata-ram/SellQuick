package com.venkat.sellquick.fragments.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.venkat.sellquick.R
import com.venkat.sellquick.data.model.Item
import com.venkat.sellquick.fragments.home.adapter.HomeViewHolder

class CartRecyclerViewAdapter(val items : List<Item>) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.cart_list_item,parent,false)
        return CartViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        val price = "Rs. ${item.price}"
        holder.priceTextView.text = price
        holder.removeButton.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class CartViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val nameTextView : TextView = view.findViewById(R.id.cart_item_name_textview)
    val priceTextView : TextView = view.findViewById(R.id.cart_item_price_textview)
    val removeButton : TextView = view.findViewById(R.id.cart_item_remove_button)

}
