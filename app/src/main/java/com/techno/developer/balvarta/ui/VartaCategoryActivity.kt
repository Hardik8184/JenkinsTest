package com.techno.developer.balvarta.ui

//import com.google.android.gms.ads.AdRequest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.facebook.ads.AdSettings
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.techno.developer.balvarta.R
import com.techno.developer.balvarta.utils.CommonDataUtility
import kotlinx.android.synthetic.main.techno_activity_varta_category.bannerContainer
import kotlinx.android.synthetic.main.techno_activity_varta_category.btnEnglishVarta
import kotlinx.android.synthetic.main.techno_activity_varta_category.btnGujaratiVarta
import kotlinx.android.synthetic.main.techno_activity_varta_category.btnHindiVarta
import kotlinx.android.synthetic.main.techno_activity_varta_category.btnMore
import kotlinx.android.synthetic.main.techno_activity_varta_category.btnRate
import kotlinx.android.synthetic.main.techno_activity_varta_category.btnShare
import kotlinx.android.synthetic.main.techno_activity_varta_category.main_toolbar

class VartaCategoryActivity : BaseActivity(), OnClickListener {

  private lateinit var adView: AdView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.techno_activity_varta_category)

    setToolBar()
    initUI()
  }

  override fun onClick(v: View?) {

    when (v?.id) {
      R.id.btnGujaratiVarta -> {
        val intent = Intent(this@VartaCategoryActivity, VartaTitleActivity::class.java)
        intent.putExtra("type", "gujarati")
        startActivity(intent)
      }
      R.id.btnHindiVarta -> {
        val intent = Intent(this@VartaCategoryActivity, VartaTitleActivity::class.java)
        intent.putExtra("type", "hindi")
        startActivity(intent)
      }
      R.id.btnEnglishVarta -> {
        val intent = Intent(this@VartaCategoryActivity, VartaTitleActivity::class.java)
        intent.putExtra("type", "english")
        startActivity(intent)
      }
      R.id.btnShare -> {
        shareApp()
      }
      R.id.btnRate -> {
        rateUs()
      }
      R.id.btnMore -> {
        moreApp()
      }
    }
  }

  override fun onResume() {

    adView.loadAd()

    if (CommonDataUtility.checkConnection(this@VartaCategoryActivity)) {
      bannerContainer.visibility = View.VISIBLE
    } else {
      bannerContainer.visibility = View.GONE
    }
    super.onResume()
  }

  override fun onBackPressed() {
    super.onBackPressed()
    finish()
  }

  private fun setToolBar() {
    setSupportActionBar(main_toolbar)
  }

  private fun initUI() {

    createDatabase()

    adView =
      AdView(
          this@VartaCategoryActivity, getString(R.string.adFaceBookBannerId),
          AdSize.BANNER_HEIGHT_50
      )
    bannerContainer.addView(adView)
    // AdSettings.addTestDevice("69e1f009-5e61-4d92-a52f-a6bcc8898efe")

    btnGujaratiVarta.setOnClickListener(this)
    btnHindiVarta.setOnClickListener(this)
    btnEnglishVarta.setOnClickListener(this)
    btnShare.setOnClickListener(this)
    btnRate.setOnClickListener(this)
    btnMore.setOnClickListener(this)
  }

  private fun shareApp() {
    try {
      val intent = Intent("android.intent.action.SEND")
      intent.type = "text/plain"
      intent.putExtra(
          "android.intent.extra.TEXT", resources.getString(R.string.app_name)
          .toString() + "\nDownload now : " + "http://play.google.com/store/apps/details?id="
          + packageName
      )
      startActivity(Intent.createChooser(intent, "share via"))
    } catch (e: Exception) {
      Toast.makeText(
          this@VartaCategoryActivity, "Something problem please try again!!", Toast.LENGTH_SHORT
      )
          .show()
    }
  }

  private fun rateUs() {

    val intent =
      Intent("android.intent.action.VIEW", Uri.parse("market://details?id=$packageName"))
    try {
      startActivity(intent)
    } catch (e: ActivityNotFoundException) {
      startActivity(
          Intent(
              "android.intent.action.VIEW",
              Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
          )
      )
    }
  }

  private fun moreApp() {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse("https://play.google.com/store/apps/developer?id=Techno+Developer")
    startActivity(i)
  }
}