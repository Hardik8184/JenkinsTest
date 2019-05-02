package com.techno.developer.balvarta.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.text.Html
import android.text.Spanned
import com.techno.developer.balvarta.xmlhandler.StoryListItem
import com.techno.developer.balvarta.xmlhandler.StoryListXMLHandler
import org.xml.sax.InputSource
import javax.xml.parsers.SAXParserFactory

/**
 * Created by admin on 25/01/18.
 */

object CommonDataUtility {

  var itemsList: ArrayList<StoryListItem> = ArrayList()

  //</editor-fold>
  fun checkConnection(activity: Activity): Boolean {
    val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
  }

  fun parseXML(activity: Activity) {
    try {
      val xMLReader = SAXParserFactory.newInstance()
          .newSAXParser()
          .xmlReader
      val storyListXMLHandler = StoryListXMLHandler()
      xMLReader.contentHandler = storyListXMLHandler
      xMLReader.parse(InputSource(activity.assets.open("categoryitem_1.xml")))
      itemsList = storyListXMLHandler.itemsList
    } catch (e: Throwable) {
      println("Hardik error --> " + e.printStackTrace())
    }
  }

  @SuppressWarnings("deprecation")
  fun getHtmlString(html: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
      Html.fromHtml(html)
    }
  }
}
