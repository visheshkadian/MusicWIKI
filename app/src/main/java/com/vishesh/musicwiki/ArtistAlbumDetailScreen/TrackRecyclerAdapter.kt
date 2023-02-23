package com.vishesh.musicwiki.ArtistAlbumDetailScreen

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vishesh.musicwiki.Api_implementation.TrackListResponse
import com.vishesh.musicwiki.R

class TrackRecyclerAdapter(var data: MutableList<TrackListResponse>, val c: Activity, val tagData: String) :
    RecyclerView.Adapter<TrackRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        var v: View = inflater.inflate(R.layout.top_album_horizontal_itam, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = data[p1]
        p0.titleTv.text = item.name
        p0.desc.visibility = View.GONE

        if (tagData != "album") {
            p0.titleTv.setTextColor(c.resources.getColor(R.color.secondaryText))
        }

        Glide
            .with(c)
            .load(item.image[3].text)
            .centerCrop()
            .into(p0.image)

        p0.maincard.setOnClickListener {
        }
    }

    fun updateDataset(dataList: MutableList<TrackListResponse>) {
        data = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val desc: TextView = itemView.findViewById(R.id.desc)
        val image: ImageView = itemView.findViewById(R.id.backImage)
        val maincard: CardView = itemView.findViewById(R.id.maincard)
    }
}
