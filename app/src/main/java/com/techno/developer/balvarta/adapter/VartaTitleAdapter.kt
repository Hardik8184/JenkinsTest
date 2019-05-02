package com.techno.developer.balvarta.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdsManager
import com.techno.developer.balvarta.R
import com.techno.developer.balvarta.model.VartaModel
import kotlinx.android.synthetic.main.native_ad_layout.view.nativeAdTitle
import kotlinx.android.synthetic.main.native_ad_layout.view.nativeAdCallToAction
import kotlinx.android.synthetic.main.native_ad_layout.view.nativeAdIcon
import kotlinx.android.synthetic.main.native_ad_layout.view.nativeAdMedia
import kotlinx.android.synthetic.main.native_ad_layout.view.nativeAdSocialContext
import kotlinx.android.synthetic.main.native_ad_layout.view.nativeAdBody
import kotlinx.android.synthetic.main.native_ad_layout.view.sponsoredLabel
import kotlinx.android.synthetic.main.techno_varta_title_list.view.titleName

class VartaTitleAdapter(
  private var titleList: ArrayList<VartaModel>,
  private var type: String,
  var listener: TitleClickListener,
  var nativeAd: NativeAd,
  var manager: NativeAdsManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  interface TitleClickListener {
    fun onGujaratiTitleClick(vartaModel: VartaModel)
    fun onHindiTitleClick(
      position: Int,
      title: String
    )
  }

  fun managerLoad(nativeAdsManager : NativeAdsManager){
    manager = nativeAdsManager
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(
    viewGroup: ViewGroup,
    viewType: Int
  ): ViewHolder {

    return if (viewType == 0) {
      VartaTitleViewHolder(
          LayoutInflater.from(viewGroup.context).inflate(
              R.layout.techno_varta_title_list, viewGroup, false
          )
      )
    } else {
      NativeBannerHolder(
          LayoutInflater.from(viewGroup.context).inflate(
              R.layout.native_ad_layout, viewGroup, false
          )
      )
    }
  }

  override fun getItemViewType(position: Int): Int {
    var viewType = 0
    if (position % 10 == 0 && position != 0) viewType = 1
    return viewType
  }

  override fun getItemCount(): Int {
    return titleList.size
  }

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int
  ) {

    when (holder.itemViewType) {
      0 -> {

        val categoryShayariHolder = holder as VartaTitleViewHolder
        categoryShayariHolder.bind(titleList[position], type)
      }
      1 -> {
        val nativeBannerHolder = holder as NativeBannerHolder
        nativeBannerHolder.bind()
      }
    }
  }

  inner class VartaTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
      vartaModel: VartaModel,
      type: String
    ) {

      itemView.titleName.text = vartaModel.vartaTitle

      if ((adapterPosition % 5 == 0) && adapterPosition != 0 && manager.isLoaded) {
        println("Hardik manager --> nextNativeAd")
        nativeAd = manager.nextNativeAd()
      }

      itemView.setOnClickListener {
        if (type.equals("gujarati", ignoreCase = true)) {
          listener.onGujaratiTitleClick(vartaModel)
        } else {
          listener.onHindiTitleClick(adapterPosition, vartaModel.vartaTitle!!)
        }
      }
    }
  }

  inner class NativeBannerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind() {

//      itemView.nativeAdContainer.removeAllViews()

      // Add the Ad view into the ad container.
//      val inflater = LayoutInflater.from(activity)
//      // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
//      val adView =
//        inflater.inflate(
//            R.layout.native_ad_layout, itemView.nativeAdContainer, false
//        ) as LinearLayout
//      itemView.nativeAdContainer.addView(adView)

      inflateAd(nativeAd, itemView)
    }
  }

  private fun inflateAd(
    nativeAd: NativeAd,
    itemView: View
  ) {

    nativeAd.unregisterView()

    // Create native UI using the ad metadata.
//    val nativeAdIcon = adView.findViewById<AdIconView>(R.id.native_ad_icon)
//    val nativeAdTitle = adView.findViewById<TextView>(R.id.native_ad_title)
//    val nativeAdMedia = adView.findViewById<MediaView>(R.id.native_ad_media)
//    val nativeAdSocialContext = adView.findViewById<TextView>(R.id.native_ad_social_context)
//    val nativeAdBody = adView.findViewById<TextView>(R.id.native_ad_body)
//    val sponsoredLabel = adView.findViewById<TextView>(R.id.native_ad_sponsored_label)
//    val nativeAdCallToAction = adView.findViewById<Button>(R.id.native_ad_call_to_action)

    // Set the Text.
    itemView.nativeAdTitle.text = nativeAd.advertiserName
    itemView.nativeAdBody.text = nativeAd.adBodyText
    itemView.nativeAdSocialContext.text = nativeAd.adSocialContext
    itemView.nativeAdCallToAction.visibility =
      if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
    itemView.nativeAdCallToAction.text = nativeAd.adCallToAction
    itemView.sponsoredLabel.text = nativeAd.sponsoredTranslation

    // Create a list of clickable views
    val clickableViews = ArrayList<View>()
    clickableViews.add(itemView.nativeAdTitle)
    clickableViews.add(itemView.nativeAdCallToAction)

    // Register the Title and CTA button to listen for clicks.
    nativeAd.registerViewForInteraction(
        itemView,
        itemView.nativeAdMedia,
        itemView.nativeAdIcon,
        clickableViews
    )
  }
}