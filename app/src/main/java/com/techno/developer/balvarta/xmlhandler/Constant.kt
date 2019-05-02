package com.techno.developer.balvarta.xmlhandler

import java.io.Serializable

class Constant : Serializable {

  companion object {
    var STR_CAT_ID: String? = null
    var STR_CAT_NAME: String? = null
    val strDesc = "story_des"
    var STR_DESCC: Array<String>? = null
    val storyId = "story_id"
    var STR_IDD: Array<String>? = null
    val strName = "story_title"
    var STR_NAMEE: Array<String>? = null
    private const val serialVersionUID: Long = 1
  }
}
//companion object {
//    var STR_CAT_ID: String? = null
//    var STR_CAT_NAME: String? = null
//    val STR_DESC = "story_des"
//    var STR_DESCC: Array<String>? = null
//    val STR_ID = "story_id"
//    var STR_IDD: Array<String>? = null
//    val STR_NAME = "story_title"
//    var STR_NAMEE: Array<String>? = null
//    private const val serialVersionUID: Long = 1
//  }
