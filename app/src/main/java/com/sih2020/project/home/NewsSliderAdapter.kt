package com.sih2020.project.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sih2020.project.R
import com.sih2020.project.constants.RestURLs
import com.smarteist.autoimageslider.SliderViewAdapter


class NewsSliderAdapter(private var mSliderItems: ArrayList<News> , private var context: Context) :
    SliderViewAdapter<NewsSliderAdapter.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(
        viewHolder: SliderAdapterVH,
        position: Int
    ) {
        val link: String = mSliderItems[position].link

        Glide.with(viewHolder.itemView)
            .load("${RestURLs.SERVER}/$link")
            .fitCenter()
            .into(viewHolder.newsImage)

        viewHolder.itemView.setOnClickListener {
            if(mSliderItems[position].href.isNotEmpty()){
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(mSliderItems[position].href)
                context.startActivity(intent)
            }
        }
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) :
        ViewHolder(itemView) {

        var newsImage: ImageView = itemView.findViewById(R.id.newsImage)

    }

}