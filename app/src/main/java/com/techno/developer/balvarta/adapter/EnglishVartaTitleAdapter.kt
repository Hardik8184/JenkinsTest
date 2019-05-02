package com.techno.developer.balvarta.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techno.developer.balvarta.R
import com.techno.developer.balvarta.utils.CommonDataUtility
import com.techno.developer.balvarta.xmlhandler.StoryListItem
import kotlinx.android.synthetic.main.techno_varta_title_list.view.titleName

class EnglishVartaTitleAdapter(
  private var activity: Activity,
  private var listener: EnglishTitleClickListener
) : RecyclerView.Adapter<EnglishVartaTitleAdapter.ViewHolder>() {

  interface EnglishTitleClickListener {
    fun onEnglishTitleClick(
      position: Int
    )
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    return ViewHolder(
        LayoutInflater.from(activity)
            .inflate(R.layout.techno_varta_title_list, parent, false)
    )
  }

  override fun getItemCount(): Int {
    return CommonDataUtility.itemsList.size
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    holder.bind(CommonDataUtility.itemsList[position], listener)
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
      storyListItem: StoryListItem,
      listener: EnglishTitleClickListener
    ) {

      itemView.titleName.text = storyListItem.storyTitle

      itemView.setOnClickListener {
        listener.onEnglishTitleClick(adapterPosition)
      }
    }
  }
}