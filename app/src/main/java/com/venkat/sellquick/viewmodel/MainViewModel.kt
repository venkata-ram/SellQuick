package com.venkat.sellquick.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var orderCount = 0
    private val applicationContext: Application = application

    private fun getCurrentDateAndTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault()).format(Date())
    }

    fun getFileName(): String {
        return "${getCurrentDateAndTime()}.txt"
    }

    fun increaseOrderCount() {
        orderCount++
    }

    fun createFileWriter(fileName: String): FileWriter {
        val dir = File(applicationContext.filesDir, "mydir")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val textFile = File(dir, fileName)
        Log.d("fileName", fileName)
        return FileWriter(textFile)
    }

    fun writeFileOnInternalStorage(writer: FileWriter, dataToSave: String) {
        try {
            writer.append(dataToSave)
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}