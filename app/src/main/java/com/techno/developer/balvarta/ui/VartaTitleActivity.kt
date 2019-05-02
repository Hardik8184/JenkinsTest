package com.techno.developer.balvarta.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdSettings
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.facebook.ads.InterstitialAd
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdListener
import com.facebook.ads.NativeAdsManager
import com.techno.developer.balvarta.R
import com.techno.developer.balvarta.adapter.EnglishVartaTitleAdapter
import com.techno.developer.balvarta.adapter.EnglishVartaTitleAdapter.EnglishTitleClickListener
import com.techno.developer.balvarta.adapter.VartaTitleAdapter
import com.techno.developer.balvarta.adapter.VartaTitleAdapter.TitleClickListener
import com.techno.developer.balvarta.database.DatabaseManager
import com.techno.developer.balvarta.model.VartaModel
import com.techno.developer.balvarta.utils.CommonDataUtility
import kotlinx.android.synthetic.main.techno_activity_varta_title.bannerContainer
import kotlinx.android.synthetic.main.techno_activity_varta_title.main_toolbar
import kotlinx.android.synthetic.main.techno_activity_varta_title.recyclerViewTitleList
import kotlinx.android.synthetic.main.techno_activity_varta_title.toolbar_title
import java.io.IOException

class VartaTitleActivity : BaseActivity(),
    TitleClickListener,
    EnglishTitleClickListener,
    NativeAdListener,
    NativeAdsManager.Listener {

  private lateinit var interstitialAd: InterstitialAd
  private lateinit var nativeAd: NativeAd
  private lateinit var adView: AdView

  var manager: NativeAdsManager? = null

  private var titleList = ArrayList<VartaModel>()
  private lateinit var type: String

  private lateinit var vartaTitleAdapter: VartaTitleAdapter
  private lateinit var englishVartaTitleAdapter: EnglishVartaTitleAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.techno_activity_varta_title)

    loadAd()

    setToolBar()
    initUI()
  }

  override fun onResume() {

    interstitialAd =
      InterstitialAd(this@VartaTitleActivity, getString(R.string.adFaceBookInterstitialId))
    interstitialAd.loadAd()

    adView.loadAd()

    if (CommonDataUtility.checkConnection(this@VartaTitleActivity)) {
      bannerContainer.visibility = View.VISIBLE
    } else {
      bannerContainer.visibility = View.GONE
    }
    super.onResume()
  }

  public override fun onDestroy() {
    interstitialAd.destroy()
    super.onDestroy()
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

  override fun onBackPressed() {
    loadInterstitialAd()
    super.onBackPressed()
    finish()
  }

  override fun onGujaratiTitleClick(vartaModel: VartaModel) {
    val intent = Intent(this@VartaTitleActivity, VartaActivity::class.java)
    intent.putExtra("vartaId", vartaModel.vartaId)
    intent.putExtra("title", vartaModel.vartaTitle)
    intent.putExtra("type", type)
    startActivity(intent)
  }

  override fun onHindiTitleClick(
    position: Int,
    title: String
  ) {
    val intent = Intent(this@VartaTitleActivity, VartaActivity::class.java)
    intent.putExtra("position", position)
    intent.putExtra("title", title)
    intent.putExtra("type", type)
    startActivity(intent)
  }

  override fun onEnglishTitleClick(
    position: Int
  ) {
    val intent = Intent(this@VartaTitleActivity, VartaActivity::class.java)
    intent.putExtra("position", position)
    intent.putExtra("type", type)
    startActivity(intent)
  }

  private fun setToolBar() {

    setSupportActionBar(main_toolbar)

    main_toolbar.setNavigationIcon(R.drawable.ico_arrow_back_svg)
    main_toolbar.setNavigationOnClickListener { onBackPressed() }
  }

  private fun loadAd() {

    manager = NativeAdsManager(
        this@VartaTitleActivity, getString(R.string.adFaceBookNativeBannerId), 10
    )
    manager!!.setListener(this)
    manager!!.loadAds()

    adView =
      AdView(
          this@VartaTitleActivity, getString(R.string.adFaceBookBannerId),
          AdSize.BANNER_HEIGHT_50
      )
    bannerContainer.addView(adView)

    nativeAd = NativeAd(this@VartaTitleActivity, getString(R.string.adFaceBookNativeBannerId))
    nativeAd.loadAd()

    // AdSettings.addTestDevice("69e1f009-5e61-4d92-a52f-a6bcc8898efe")
  }

  private fun initUI() {

    type = intent.getStringExtra("type")

    recyclerViewTitleList.isNestedScrollingEnabled = false
    recyclerViewTitleList.layoutManager =
      LinearLayoutManager(this@VartaTitleActivity, RecyclerView.VERTICAL, false)

    when (type) {
      "gujarati" -> {
        toolbar_title.text = getString(R.string.gujarati_bal_varta)
        titleList = dataBaseHelper.balVartaTitle

        vartaTitleAdapter = VartaTitleAdapter(titleList, type, this, nativeAd, manager!!)
        recyclerViewTitleList.adapter = vartaTitleAdapter

      }
      "hindi" -> {

        toolbar_title.text = getString(R.string.hindi_bal_varta)

        val displayName = resources.getStringArray(R.array.DisplayName)

        for (name in displayName) {
          val vartaModel = VartaModel()
          vartaModel.vartaTitle = name.toString()
          titleList.add(vartaModel)
        }

        vartaTitleAdapter = VartaTitleAdapter(titleList, type, this, nativeAd, manager!!)
        recyclerViewTitleList.adapter = vartaTitleAdapter

      }
      else -> {

        toolbar_title.text = getString(R.string.english_bal_varta)
        CommonDataUtility.parseXML(this@VartaTitleActivity)

        englishVartaTitleAdapter = EnglishVartaTitleAdapter(this, this)
        recyclerViewTitleList.adapter = englishVartaTitleAdapter

      }
    }
  }

  override fun onAdsLoaded() {
    println("Hardik manager --> onAdsLoaded")
    when (type) {
      "hindi", "gujarati" -> {
        vartaTitleAdapter.managerLoad(manager!!)
      }
      "english" -> {
        // later used
      }
    }
  }

  override fun onAdError(p0: AdError?) {
    // not used
  }

  override fun onAdClicked(ad: Ad?) {
    // not used
  }

  override fun onError(
    ad: Ad?,
    error: AdError?
  ) {
    if (ad == interstitialAd) {
      println("Hardik interstitialAd error --> " + error!!.errorMessage)
    } else if (ad == nativeAd) {
      println("Hardik native error --> " + error!!.errorMessage)
    }
  }

  override fun onAdLoaded(ad: Ad?) {
    if (ad == interstitialAd) {
      println("Hardik interstitialAd --> Ad loaded")
    } else if (ad == nativeAd) {
      println("Hardik nativeAd --> Ad loaded")
    }
  }

  override fun onLoggingImpression(ad: Ad?) {
    if (ad == interstitialAd) {
      println("Hardik interstitialAd --> onLoggingImpression")
    } else if (ad == nativeAd) {
      println("Hardik native --> onLoggingImpression")
    }
  }

  override fun onMediaDownloaded(p0: Ad?) {
    // not used
  }
}