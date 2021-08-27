package com.song.tasty.module.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.drakeet.multitype.ItemViewBinder
import com.smart.ui.widget.image.SMUIImageView
import com.smart.utils.LogUtils
import com.song.tasty.common.core.utils.imglaoder.GlideUtils
import com.song.tasty.module.home.R
import com.song.tasty.module.home.entity.SongBean
import kotlinx.android.synthetic.main.home_item_person_radio.view.*

class PersonRadioViewHolder: ItemViewBinder<SongBean, PersonRadioViewHolder.ViewHolder>() {




    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val ivCover = itemView.ivCover

    }
                                                                                                                                                                                                                                                                                                                                      
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val view=inflater.inflate(R.layout.home_item_person_radio,parent,false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: SongBean) {
        LogUtils.e("onBindViewHolder---")
        //GlideUtils.loadImage(holder.itemView.context,item.playurl,holder.ivCover)
        //holder.ivCover.setImageResource(R.mipmap.ic_logo)

        Glide
            .with(holder.itemView.context)
            .load(item.playurl)
            .transition(DrawableTransitionOptions().crossFade(GlideUtils.FADE_TIME))
            .into(holder.ivCover)
    }


}
