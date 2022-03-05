package com.venkat.sellquick.fragments.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.skydoves.powerspinner.PowerSpinnerView
import com.venkat.sellquick.R
import com.venkat.sellquick.data.model.Item
import com.venkat.sellquick.data.viewmodel.SharedViewModel

class HomeRecyclerViewAdapter(
    val items: List<Item>,
    val sharedViewModel: SharedViewModel,
    val context: Context,
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
            if (!sharedViewModel.cartItems.value?.contains(item)!!) {
                sharedViewModel.addItemToCart(item)
                message = "${item.name} added to cart..."
            } else {
                message = "${item.name} already in the cart..."
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
        val orderCountTextView: TextView = customView
            .findViewById(R.id.order_count_textview)
        orderCountTextView.text = item.orderCount.toString()
        val rankTextView: TextView = customView.findViewById(R.id.rank_textview)
        rankTextView.text = item.rank.toString()
        val durationSpinner: PowerSpinnerView = customView.findViewById(R.id.duration_spinner)
        durationSpinner.selectItemByIndex(0)
        durationSpinner.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->

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