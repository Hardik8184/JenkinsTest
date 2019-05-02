package com.techno.developer.balvarta.database

import android.app.Activity
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.techno.developer.balvarta.model.VartaModel
import java.io.IOException
import java.util.ArrayList

class DataBaseHelper(activity: Activity) {

  private var dbHelper: DatabaseManager? = null
  private var myDataBase: SQLiteDatabase? = null

  init {
    try {
      dbHelper = DatabaseManager(activity)
    } catch (e: IOException) {
      println("Hardik DBQuery error --> " + e.localizedMessage)
    }
  }

  @Throws(SQLException::class)
  fun createDatabase(): DataBaseHelper {
    try {
      dbHelper!!.createDatabase()
    } catch (e: IOException) {
      println("Hardik createDatabase error --> " + e.localizedMessage)
    }

    return this
  }

  @Throws(SQLException::class)
  fun open(): DataBaseHelper {
    try {
      dbHelper!!.openDatabase()
      dbHelper!!.close()
      myDataBase = dbHelper!!.readableDatabase
    } catch (e: SQLException) {
      println("Hardik open error --> " + e.localizedMessage)
    }

    return this
  }

  fun close() {
    dbHelper!!.close()
  }

  //______________________________________________________________________________________________
  //__________________________________      QUERIES      ________________________________________
  //______________________________________________________________________________________________

  fun getSingleBalVarta(vartaId: String): String {

    open()

    var status = ""

    try {

      val cursor =
        this.myDataBase!!.rawQuery("SELECT chapter FROM bv where number = $vartaId", null)

      while (cursor.moveToNext()) {
        status = cursor.getString(0)
      }

      cursor.close()

    } catch (e: Exception) {
      println("Hardik getJokes error --> " + e.localizedMessage)
    }

    close()

    return status
  }

  val balVartaTitle: ArrayList<VartaModel>
    get() {

      open()

      val titleList = ArrayList<VartaModel>()

      val cursor = this.myDataBase!!.rawQuery(
          "SELECT number,item FROM bv",
          null
      )

      while (cursor.moveToNext()) {

        val categoryModel = VartaModel()
        categoryModel.vartaId = cursor.getString(0)
        categoryModel.vartaTitle = cursor.getString(1)

        titleList.add(categoryModel)
      }

      cursor.close()

      close()

      return titleList
    }
}