package com.venkat.sellquick.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.venkat.quickselldatabase.db.MainDatabase
import com.venkat.quickselldatabase.db.MainRepository
import com.venkat.sellquick.R
import com.venkat.sellquick.databinding.FragmentHomeBinding
import com.venkat.sellquick.fragments.home.adapter.HomeRecyclerViewAdapter
import com.venkat.sellquick.viewmodel.HomeViewModel
import com.venkat.sellquick.viewmodel.HomeViewModelFactory


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val dao = MainDatabase.getInstance(requireActivity().application).mainDAO()
        val repository = MainRepository(dao)
        val factory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(requireActivity(), factory).get(HomeViewModel::class.java)

        setUpRecyclerView()
        return view
    }

    private fun setUpRecyclerView() {
        binding.homeRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation_view)
        homeViewModel.items.observe(viewLifecycleOwner) {
            binding.homeRecyclerview.adapter = HomeRecyclerViewAdapter(
                it,
                homeViewModel,
                requireActivity(),
                viewLifecycleOwner,
                bottomNavigationView
            )
        }

    }


}