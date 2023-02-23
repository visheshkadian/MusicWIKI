package com.vishesh.musicwiki.ArtistAlbumDetailScreen

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.vishesh.musicwiki.Api_implementation.TagsListResponse
import com.vishesh.musicwiki.R
import com.vishesh.musicwiki.TagsFragmentDetailsScreen.TagDetailsActivity

class TagRecyclerAdapter(var data: MutableList<TagsListResponse>, val c: Activity) :
    RecyclerView.Adapter<TagRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        var v: View = inflater.inflate(R.layout.tag_horizontal_item, p0, false)
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
        p0.itemBtn.text = item.name

        p0.itemBtn.setOnClickListener {
            var intent = Intent(c, TagDetailsActivity::class.java)
            intent.putExtra("tagName", item.name)
            c.startActivity(intent)
        }
    }

    fun updateDataset(dataList: MutableList<TagsListResponse>) {
        data = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemBtn: Button = itemView.findViewById(R.id.itemButton)
    }
}
