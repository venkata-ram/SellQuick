package com.venkat.sellquick.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.venkat.sellquick.data.model.Item
import com.venkat.sellquick.databinding.FragmentHomeBinding
import com.venkat.sellquick.fragments.home.adapter.HomeRecyclerViewAdapter


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.homeRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val items = arrayListOf<Item>()
        items.add(Item("Noodles",175.0,0, 0))
        items.add(Item("Semiya",175.0,0, 0))
        items.add(Item("Tooth paste",175.0,0, 0))
        items.add(Item("Tooth brush",175.0,0, 0))
        binding.homeRecyclerview.adapter = HomeRecyclerViewAdapter(items)
        return view
    }

}