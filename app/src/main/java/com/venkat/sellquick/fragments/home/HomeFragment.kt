package com.venkat.sellquick.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.venkat.sellquick.R
import com.venkat.sellquick.data.model.Item
import com.venkat.sellquick.data.viewmodel.SharedViewModel
import com.venkat.sellquick.databinding.FragmentHomeBinding
import com.venkat.sellquick.fragments.home.adapter.HomeRecyclerViewAdapter


class HomeFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpRecyclerView()
        return view
    }

    private fun setUpRecyclerView() {
        binding.homeRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val items = arrayListOf<Item>()
        addItems(items)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation_view)
        binding.homeRecyclerview.adapter = HomeRecyclerViewAdapter(items,sharedViewModel,requireActivity(),bottomNavigationView)
    }

    private fun addItems(items: ArrayList<Item>) {
        items.add(Item("Noodles", 175.0, 0, 0))
        items.add(Item("Semiya", 175.0, 0, 0))
        items.add(Item("Tooth paste", 175.0, 0, 0))
        items.add(Item("Tooth brush", 175.0, 0, 0))
    }

}