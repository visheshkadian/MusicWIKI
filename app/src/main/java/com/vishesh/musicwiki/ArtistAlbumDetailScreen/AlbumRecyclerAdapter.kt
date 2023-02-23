package com.vishesh.musicwiki.ArtistAlbumDetailScreen

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vishesh.musicwiki.Api_implementation.AlbumListResponse
import com.vishesh.musicwiki.R

class AlbumRecyclerAdapter(var data: MutableList<AlbumListResponse>, val c: Activity, val tagData: String) :
    RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>() {

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
        p0.desc.text = item.artist.name

        Glide
            .with(c)
            .load(item.image[3].text)
            .centerCrop()
            .into(p0.image)

        p0.maincard.setOnClickListener {
            var intent = Intent(c, AlbumDetailsActivity::class.java)
            intent.putExtra("album", item.name)
            intent.putExtra("artist", item.artist.name)
            intent.putExtra("image", item.image[3].text)

            c.startActivity(intent)
        }
    }

    fun updateDataset(dataList: MutableList<AlbumListResponse>) {
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
