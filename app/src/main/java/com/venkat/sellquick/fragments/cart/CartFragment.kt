package com.venkat.sellquick.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.venkat.quickselldatabase.db.MainDatabase
import com.venkat.quickselldatabase.db.MainRepository
import com.venkat.sellquick.viewmodel.CartViewModel
import com.venkat.sellquick.viewmodel.CartViewModelFactory
import com.venkat.sellquick.databinding.FragmentCartBinding
import com.venkat.sellquick.fragments.cart.adapter.CartRecyclerViewAdapter
import com.venkat.sellquick.viewmodel.MainViewModel


class CartFragment : Fragment() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        val dao = MainDatabase.getInstance(requireActivity().application).mainDAO()
        val repository = MainRepository(dao)
        val factory = CartViewModelFactory(repository)
        cartViewModel = ViewModelProvider(requireActivity(), factory).get(CartViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.cartRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        cartViewModel.cartItems.observe(viewLifecycleOwner) {
            val adapter = CartRecyclerViewAdapter(it, cartViewModel)
            binding.cartRecyclerview.adapter = adapter

            if (it.isEmpty())
                binding.placeOrderButton.visibility = View.GONE
            else
                binding.placeOrderButton.visibility = View.VISIBLE
        }

        binding.placeOrderButton.setOnClickListener {
            if (cartViewModel.placeOrder()) {
                mainViewModel.increaseOrderCount()
                val message = "Order placed successfully..."
                Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
            }
        }


        return view
    }


}