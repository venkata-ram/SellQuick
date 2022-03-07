package com.venkat.sellquick.fragments.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.skydoves.powerspinner.PowerSpinnerView
import com.venkat.quickselldatabase.db.models.Item
import com.venkat.sellquick.R
import com.venkat.sellquick.viewmodel.HomeViewModel

class HomeRecyclerViewAdapter(
    val items: List<Item>,
    val homeViewModel: HomeViewModel,
    val context: Context,
    val viewLifCycleOwner: LifecycleOwner,
    val bottomNavigationView: BottomNavigationView
) : RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.home_list_item, parent, false)
        return HomeViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        val price = "Rs. ${item.price}"
        holder.priceTextView.text = price
        var message = ""
        holder.addButton.setOnClickListener {
            val itemToAdd = Item(item.id, item.name, item.price, true)
            if (!item.inCart) {
                homeViewModel.addItemToCart(itemToAdd)
                message = "${item.name} added to cart..."
            } else {
                message = "${item.name} already in cart..."
            }



            Snackbar.make(holder.view, message, Snackbar.LENGTH_SHORT)
                .setAction("View Cart") {
                    bottomNavigationView.selectedItemId = R.id.cartFragment
                }
                .show()
        }
        holder.relativeLayout.setOnLongClickListener {
            showCustomViewDialog(item)
            true
        }

    }



    private fun showCustomViewDialog(item: Item, dialogBehavior: DialogBehavior = ModalDialog) {
        val dialog = MaterialDialog(context, dialogBehavior).show {
            setTitle(item.name)
            customView(R.layout.item_details, scrollable = true, horizontalPadding = true)
            negativeButton(R.string.close)
            context
        }

        // Setup custom view content
        val customView = dialog.getCustomView()
        val totalOrdersTextView: TextView = customView.findViewById(R.id.total_orders_textview)
        val rankTextView: TextView = customView.findViewById(R.id.rank_textview)
        val durationSpinner: PowerSpinnerView = customView.findViewById(R.id.duration_spinner)
        val orderCountTextView: TextView = customView.findViewById(R.id.orders_count_textview)
        homeViewModel.getOrderCount(item.id).observe(viewLifCycleOwner) {
            Log.d("item_id", it.toString())
            totalOrdersTextView.text = it.toString()
        }



        calculateOrderCountAndRank(item, "-1 Hour", rankTextView, orderCountTextView)
        durationSpinner.selectItemByIndex(0)
        durationSpinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldString, newIndex, newString ->
            val duration = when (newString) {
                "1 hour" -> "-1 Hour"
                "24 hours" -> "-24 Hours"
                else -> "-7 days"
            }
            calculateOrderCountAndRank(item, duration, rankTextView, orderCountTextView)


        }
    }

    private fun calculateOrderCountAndRank(
        selectedItem: Item,
        duration: String,
        rankTextView: TextView,
        orderCountTextView: TextView
    ) {

        homeViewModel.getAllOrders(duration).observe(viewLifCycleOwner) { orders ->

            val itemIdAndOrderCountMap = homeViewModel.getItemIdAndOrderCountMap(orders)

            //calculate order count
            val orderCount = itemIdAndOrderCountMap[selectedItem.id] ?: 0
            orderCountTextView.text = orderCount.toString()

            //calculate rank
            val rank = homeViewModel.calculateRank(selectedItem, itemIdAndOrderCountMap)
            if (rank == 0)
                rankTextView.text = "no rank"
            else
                rankTextView.text = rank.toString()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class HomeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val nameTextView: TextView = view.findViewById(R.id.home_item_name_textview)
    val priceTextView: TextView = view.findViewById(R.id.home_item_price_textview)
    val addButton: TextView = view.findViewById(R.id.home_item_add_button)
    val relativeLayout: RelativeLayout = view.findViewById(R.id.home_item_relative_layout)


}