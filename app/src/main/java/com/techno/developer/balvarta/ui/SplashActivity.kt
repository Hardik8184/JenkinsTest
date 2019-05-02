package com.techno.developer.balvarta.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.techno.developer.balvarta.R

class SplashActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.techno_activity_splash)

    Handler().postDelayed({

      startActivity(Intent(this@SplashActivity, VartaCategoryActivity::class.java))
      finish()

    }, 2000)
  }
}