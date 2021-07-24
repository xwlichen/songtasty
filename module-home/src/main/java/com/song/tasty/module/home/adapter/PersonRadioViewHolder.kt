package com.song.tasty.module.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.tasty.module.home.R
import com.song.tasty.module.home.entity.SongBean
import me.drakeet.multitype.ItemViewBinder

class PersonRadioViewHolder: ItemViewBinder<SongBean, PersonRadioViewHolder.ViewHolder>() {




    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val view=inflater.inflate(R.layout.home_item_person_radio,parent,false)
        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: SongBean) {
        TODO("Not yet implemented")
    }
}
