package com.techno.developer.balvarta.xmlhandler

import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.util.ArrayList

class StoryListXMLHandler : DefaultHandler() {

  private var currentElement: Boolean? = false
  private var currentValue = ""
  private var item: StoryListItem? = null
  val itemsList = ArrayList<StoryListItem>()

  @Throws(SAXException::class)
  override fun startElement(
    str: String,
    str2: String,
    str3: String,
    attributes: Attributes
  ) {
    this.currentElement = java.lang.Boolean.valueOf(true)
    this.currentValue = ""
    if (str2 == "story") {
      this.item = StoryListItem()
    }
  }

  @Throws(SAXException::class)
  override fun endElement(
    str: String,
    str2: String,
    str3: String
  ) {
    this.currentElement = java.lang.Boolean.valueOf(null)
    when {
      str2.equals(Constant.storyId, ignoreCase = true) -> this.item!!.storyId = this.currentValue
      str2.equals(Constant.strName, ignoreCase = true) -> this.item!!.storyTitle =
          this.currentValue
      str2.equals(Constant.strDesc, ignoreCase = true) -> this.item!!.storyDes = this.currentValue
      str2.equals("story", ignoreCase = true) -> this.itemsList.add(this.item!!)
    }
  }

  @Throws(SAXException::class)
  override fun characters(
    cArr: CharArray,
    i: Int,
    i2: Int
  ) {
    if (this.currentElement!!) {
      val stringBuilder = StringBuilder()
      stringBuilder.append(this.currentValue)
      stringBuilder.append(String(cArr, i, i2))
      this.currentValue = stringBuilder.toString()
    }
  }
}
