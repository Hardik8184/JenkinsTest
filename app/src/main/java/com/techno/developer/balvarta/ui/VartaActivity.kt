package com.techno.developer.balvarta.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.ads.AdSettings
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.facebook.ads.InterstitialAd
import com.techno.developer.balvarta.R
import com.techno.developer.balvarta.R.string
import com.techno.developer.balvarta.database.DatabaseManager
import com.techno.developer.balvarta.utils.CommonDataUtility
import kotlinx.android.synthetic.main.techno_activity_single_varta.btnCopy
import kotlinx.android.synthetic.main.techno_activity_single_varta.btnShare
import kotlinx.android.synthetic.main.techno_activity_single_varta.main_toolbar
import kotlinx.android.synthetic.main.techno_activity_single_varta.toolbar_title
import kotlinx.android.synthetic.main.techno_activity_single_varta.txtVarta
import kotlinx.android.synthetic.main.techno_activity_varta_title.bannerContainer
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

class VartaActivity : BaseActivity(), View.OnClickListener {

  private lateinit var interstitialAd: InterstitialAd
  private lateinit var adView: AdView
  private var timer: Timer? = null

  private var varta: String? = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.techno_activity_single_varta)

    loadAd()

    setToolBar()
    initUI()
  }

  override fun onResume() {

    interstitialAd =
      InterstitialAd(this@VartaActivity, getString(string.adFaceBookInterstitialId))
    interstitialAd.loadAd()

    loadBannerAd()

    super.onResume()
  }

  public override fun onDestroy() {
    interstitialAd.destroy()
    super.onDestroy()
  }

  override fun onBackPressed() {

    if (timer != null) {
      timer!!.cancel()
    }

    loadInterstitialAd()
    super.onBackPressed()
    finish()
  }

  override fun onClick(v: View) {

    when (v.id) {

      R.id.btnCopy -> {

        if (varta.toString().isNotEmpty()) {
          applicationContext
          (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip =
            ClipData.newPlainText("Copied text", varta)
        }
        Toast.makeText(applicationContext, "Copied text", Toast.LENGTH_SHORT)
            .show()

        loadBannerAd()
        loadInterstitialAd()
      }

      R.id.btnShare -> {

        loadBannerAd()
        loadInterstitialAd()

        val intent = Intent()
        intent.action = "android.intent.action.SEND"
        intent.putExtra("android.intent.extra.TEXT", varta)
        intent.type = "text/plain"
        startActivity(intent)
      }
    }
  }

  private fun loadBannerAd() {

    if (CommonDataUtility.checkConnection(this@VartaActivity)) {
      adView.loadAd()
      bannerContainer.visibility = View.VISIBLE
    } else {
      bannerContainer.visibility = View.GONE
    }
  }

  private fun loadInterstitialAd() {
    if (!interstitialAd.isAdLoaded) {
      return
    }
    // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
    if (interstitialAd.isAdInvalidated) {
      return
    }
    // Show the ad
    interstitialAd.show()
  }

  private fun setToolBar() {

    setSupportActionBar(main_toolbar)

    main_toolbar.setNavigationIcon(R.drawable.ico_arrow_back_svg)
    main_toolbar.setNavigationOnClickListener { onBackPressed() }
  }

  private fun loadAd() {

    adView =
      AdView(
          this@VartaActivity, getString(R.string.adFaceBookBannerId),
          AdSize.BANNER_HEIGHT_50
      )
    bannerContainer.addView(adView)
    // AdSettings.addTestDevice("69e1f009-5e61-4d92-a52f-a6bcc8898efe")
  }

  private fun initUI() {

    when (intent.getStringExtra("type")) {
      "gujarati" -> {

        toolbar_title.text = intent.getStringExtra("title")

        val vartaId = intent.getStringExtra("vartaId")
        varta = dataBaseHelper.getSingleBalVarta(vartaId)
        txtVarta.text = varta
      }
      "hindi" -> {

        toolbar_title.text = intent.getStringExtra("title")

        val position = intent.getIntExtra("position", 0)
        setHindiVarta(position)
      }
      else -> {
        val position = intent.getIntExtra("position", 0)

        toolbar_title.text = CommonDataUtility.itemsList[position].storyTitle

        varta = CommonDataUtility.itemsList[position].storyDes
        txtVarta.text = CommonDataUtility.getHtmlString(varta!!)
      }
    }

    btnShare.setOnClickListener(this)
    btnCopy.setOnClickListener(this)

    showAds()
  }

  private fun setHindiVarta(position: Int) {

    val fileName = resources.getStringArray(R.array.FileName)
    val selectedValue = fileName[position].toString()

    var htmlAsString = ""
    when (selectedValue) {
      "Story1" -> htmlAsString = getString(R.string.Story1)
      "Story2" -> htmlAsString = getString(R.string.Story2)
      "Story3" -> htmlAsString = getString(R.string.Story3)
      "Story4" -> htmlAsString = getString(R.string.Story4)
      "Story5" -> htmlAsString = getString(R.string.Story5)
      "Story6" -> htmlAsString = getString(R.string.Story6)
      "Story7" -> htmlAsString = getString(R.string.Story7)
      "Story8" -> htmlAsString = getString(R.string.Story8)
      "Story9" -> htmlAsString = getString(R.string.Story9)
      "Story10" -> htmlAsString = getString(R.string.Story10)
      "Story11" -> htmlAsString = getString(R.string.Story11)
      "Story12" -> htmlAsString = getString(R.string.Story12)
      "Story13" -> htmlAsString = getString(R.string.Story13)
      "Story14" -> htmlAsString = getString(R.string.Story14)
      "Story15" -> htmlAsString = getString(R.string.Story15)
      "Story16" -> htmlAsString = getString(R.string.Story16)
      "Story17" -> htmlAsString = getString(R.string.Story17)
      "Story18" -> htmlAsString = getString(R.string.Story18)
      "Story19" -> htmlAsString = getString(R.string.Story19)
      "Story20" -> htmlAsString = getString(R.string.Story20)
      "Story21" -> htmlAsString = getString(R.string.Story21)
      "Story22" -> htmlAsString = getString(R.string.Story22)
      "Story23" -> htmlAsString = getString(R.string.Story23)
      "Story24" -> htmlAsString = getString(R.string.Story24)
      "Story25" -> htmlAsString = getString(R.string.Story25)
      "Story26" -> htmlAsString = getString(R.string.Story26)
      "Story27" -> htmlAsString = getString(R.string.Story27)
      "Story28" -> htmlAsString = getString(R.string.Story28)
      "Story29" -> htmlAsString = getString(R.string.Story29)
      "Story30" -> htmlAsString = getString(R.string.Story30)
      "Story31" -> htmlAsString = getString(R.string.Story31)
      "Story32" -> htmlAsString = getString(R.string.Story32)
      "Story33" -> htmlAsString = getString(R.string.Story33)
      "Story34" -> htmlAsString = getString(R.string.Story34)
      "Story35" -> htmlAsString = getString(R.string.Story35)
      "Story36" -> htmlAsString = getString(R.string.Story36)
      "Story37" -> htmlAsString = getString(R.string.Story37)
      "Story38" -> htmlAsString = getString(R.string.Story38)
      "Story39" -> htmlAsString = getString(R.string.Story39)
      "Story40" -> htmlAsString = getString(R.string.Story40)
      "Story41" -> htmlAsString = getString(R.string.Story41)
      "Story42" -> htmlAsString = getString(R.string.Story42)
      "Story43" -> htmlAsString = getString(R.string.Story43)
      "Story44" -> htmlAsString = getString(R.string.Story44)
      "Story45" -> htmlAsString = getString(R.string.Story45)
      "Story46" -> htmlAsString = getString(R.string.Story46)
      "Story47" -> htmlAsString = getString(R.string.Story47)
      "Story48" -> htmlAsString = getString(R.string.Story48)
      "Story49" -> htmlAsString = getString(R.string.Story49)
      "Story50" -> htmlAsString = getString(R.string.Story50)
      "Story51" -> htmlAsString = getString(R.string.Story51)
      "Story52" -> htmlAsString = getString(R.string.Story52)
      "Story53" -> htmlAsString = getString(R.string.Story53)
      "Story54" -> htmlAsString = getString(R.string.Story54)
      "Story55" -> htmlAsString = getString(R.string.Story55)
      "Story56" -> htmlAsString = getString(R.string.Story56)
      "Story57" -> htmlAsString = getString(R.string.Story57)
      "Story58" -> htmlAsString = getString(R.string.Story58)
      "Story59" -> htmlAsString = getString(R.string.Story59)
      "Story60" -> htmlAsString = getString(R.string.Story60)
      "Story61" -> htmlAsString = getString(R.string.Story61)
      "Story62" -> htmlAsString = getString(R.string.Story62)
      "Story63" -> htmlAsString = getString(R.string.Story63)
      "Story64" -> htmlAsString = getString(R.string.Story64)
      "Story65" -> htmlAsString = getString(R.string.Story65)
      "Story66" -> htmlAsString = getString(R.string.Story66)
      "Story67" -> htmlAsString = getString(R.string.Story67)
      "Story68" -> htmlAsString = getString(R.string.Story68)
      "Story69" -> htmlAsString = getString(R.string.Story69)
      "Story70" -> htmlAsString = getString(R.string.Story70)
      "Story71" -> htmlAsString = getString(R.string.Story71)
      "Story72" -> htmlAsString = getString(R.string.Story72)
      "Story73" -> htmlAsString = getString(R.string.Story73)
      "Story74" -> htmlAsString = getString(R.string.Story74)
      "Story75" -> htmlAsString = getString(R.string.Story75)
      "Story76" -> htmlAsString = getString(R.string.Story76)
      "Story77" -> htmlAsString = getString(R.string.Story77)
      "Story78" -> htmlAsString = getString(R.string.Story78)
      "Story79" -> htmlAsString = getString(R.string.Story79)
      "Story80" -> htmlAsString = getString(R.string.Story80)
      "Story81" -> htmlAsString = getString(R.string.Story81)
      "Story82" -> htmlAsString = getString(R.string.Story82)
      "Story83" -> htmlAsString = getString(R.string.Story83)
      "Story84" -> htmlAsString = getString(R.string.Story84)
      "Story85" -> htmlAsString = getString(R.string.Story85)
      "Story86" -> htmlAsString = getString(R.string.Story86)
      "Story87" -> htmlAsString = getString(R.string.Story87)
      "Story88" -> htmlAsString = getString(R.string.Story88)
      "Story89" -> htmlAsString = getString(R.string.Story89)
      "Story90" -> htmlAsString = getString(R.string.Story90)
      "Story91" -> htmlAsString = getString(R.string.Story91)
      "Story92" -> htmlAsString = getString(R.string.Story92)
      "Story93" -> htmlAsString = getString(R.string.Story93)
      "Story94" -> htmlAsString = getString(R.string.Story94)
      "Story95" -> htmlAsString = getString(R.string.Story95)
      "Story96" -> htmlAsString = getString(R.string.Story96)
      "Story97" -> htmlAsString = getString(R.string.Story97)
      "Story98" -> htmlAsString = getString(R.string.Story98)
      "Story99" -> htmlAsString = getString(R.string.Story99)
      "Story100" -> htmlAsString = getString(R.string.Story100)
    }

    txtVarta.text = CommonDataUtility.getHtmlString(htmlAsString)
  }

  private fun showAds() {

    timer = Timer()

    timer!!.scheduleAtFixedRate(object : TimerTask() {
      override fun run() {

        runOnUiThread {

          loadBannerAd()
          loadInterstitialAd()
        }
      }
    }, 60000, 75000)
  }
}