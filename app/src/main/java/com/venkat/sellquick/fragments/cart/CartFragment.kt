package com.venkat.sellquick.fragments.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.venkat.sellquick.data.model.Item
import com.venkat.sellquick.data.viewmodel.SharedViewModel
import com.venkat.sellquick.databinding.FragmentCartBinding
import com.venkat.sellquick.fragments.cart.adapter.CartRecyclerViewAdapter


class CartFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.cartRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        sharedViewModel.cartItems.observe(viewLifecycleOwner){
            val adapter = CartRecyclerViewAdapter(it,sharedViewModel)
            binding.cartRecyclerview.adapter = adapter

            if(it.isEmpty())
                binding.placeOrderButton.visibility = View.GONE
            else
                binding.placeOrderButton.visibility = View.VISIBLE
        }



        return view
    }

}