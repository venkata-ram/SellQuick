package com.venkat.sellquick

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.venkat.sellquick.viewmodel.MainViewModel
import java.io.FileWriter


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var fileWriter: FileWriter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav: BottomNavigationView =
            findViewById(R.id.activity_main_bottom_navigation_view)
        bottomNav.setupWithNavController(navController)

        val fileName = mainViewModel.getFileName()
        fileWriter = mainViewModel.createFileWriter(fileName)
        Log.d("onCreate", "onCreate")

    }

    override fun onPause() {
        super.onPause()
        val dataToSave = "Orders count : ${mainViewModel.orderCount}"
        mainViewModel.writeFileOnInternalStorage(fileWriter, dataToSave)
        Log.d("onPause", "onPause")
    }


}