package com.techno.developer.balvarta.ui

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * Created by admin on 27/01/18.
 */

class MyApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    instance = this

    Fabric.with(this, Crashlytics())

    // Initialize the Mobile Ads SDK.
//    MobileAds.initialize(this, getString(R.string.adMobAppId));
  }

  companion object {

    @get:Synchronized
    lateinit var instance: MyApplication
  }
}
