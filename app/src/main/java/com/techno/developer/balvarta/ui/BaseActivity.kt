package com.techno.developer.balvarta.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.techno.developer.balvarta.database.DataBaseHelper
import java.io.IOException

abstract class BaseActivity : AppCompatActivity() {

  lateinit var activity: BaseActivity
  lateinit var dataBaseHelper: DataBaseHelper

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    activity = this@BaseActivity

    try {
      dataBaseHelper = DataBaseHelper(activity)
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  fun createDatabase() {
    try {
      dataBaseHelper.createDatabase()
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }
}