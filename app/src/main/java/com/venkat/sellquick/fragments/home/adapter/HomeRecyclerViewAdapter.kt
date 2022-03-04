package com.venkat.sellquick.fragments.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.venkat.sellquick.R
import com.venkat.sellquick.data.model.Item

class HomeRecyclerViewAdapter(val items : List<Item>) : RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.home_list_item,parent,false)
        return HomeViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        val price = "Rs. ${item.price}"
        holder.priceTextView.text = price
        holder.addButton.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class HomeViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    val nameTextView : TextView = view.findViewById(R.id.home_item_name_textview)
    val priceTextView : TextView = view.findViewById(R.id.home_item_price_textview)
    val addButton : TextView = view.findViewById(R.id.home_item_add_button)


}