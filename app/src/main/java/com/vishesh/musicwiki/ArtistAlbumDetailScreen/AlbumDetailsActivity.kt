package com.vishesh.musicwiki.ArtistAlbumDetailScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rekord.movieapplication.Retrofit.RetrofitInstance
import com.vishesh.musicwiki.Api_implementation.AlbumDetailsApiResponse
import com.vishesh.musicwiki.Api_implementation.ArtistDetailsApiResponse
import com.vishesh.musicwiki.Api_implementation.TagsListResponse
import com.vishesh.musicwiki.R
import kotlinx.android.synthetic.main.activity_album_details.*
import kotlinx.android.synthetic.main.activity_tag_details.backBtn
import kotlinx.android.synthetic.main.activity_tag_details.spinKitMasterView
import kotlinx.android.synthetic.main.activity_tag_details.tagNametv
import kotlinx.android.synthetic.main.activity_tag_details.textView3
import retrofit2.Call
import retrofit2.Response

class AlbumDetailsActivity : AppCompatActivity() {

    var album: String = ""
    var artist: String = ""
    var imageData: String = ""

    var tagList: MutableList<TagsListResponse> = arrayListOf()
    lateinit var adapter: TagRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        album = intent.getStringExtra("album")!!
        artist = intent.getStringExtra("artist")!!
        imageData = intent.getStringExtra("image")!!

        tagNametv.text = album
        artistNametv.text = artist

        Glide
            .with(this)
            .load(imageData)
            .centerCrop()
            .into(backImage)

        initTabRecycler()
        getAlbumDetailsApi()
        getArtistDetailsApi()

        backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    fun initTabRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) as RecyclerView.LayoutManager
        tagrecyclerView.layoutManager = layoutManager
        adapter = TagRecyclerAdapter(tagList, this)
        tagrecyclerView.adapter = adapter
    }

    fun getAlbumDetailsApi() {
        spinKitMasterView.visibility = View.VISIBLE
        val api: RetrofitInstance = RetrofitInstance()
        var call: Call<AlbumDetailsApiResponse> = api.getAlbumDetailsApi(artist, album)
        call.enqueue(object : retrofit2.Callback<AlbumDetailsApiResponse> {
            override fun onResponse(call: Call<AlbumDetailsApiResponse>, response: Response<AlbumDetailsApiResponse>) {
                if (response.code() == 200) {
                    tagList = response.body()!!.album.tags.tag
                    adapter.updateDataset(tagList)
                }

                spinKitMasterView.visibility = View.GONE
            }
            override fun onFailure(call: Call<AlbumDetailsApiResponse>, t: Throwable) {
                Log.e("error", t.toString())
                spinKitMasterView.visibility = View.GONE
            }
        })
    }

    fun getArtistDetailsApi() {
        spinKitMasterView.visibility = View.VISIBLE
        val api: RetrofitInstance = RetrofitInstance()
        var call: Call<ArtistDetailsApiResponse> = api.getArtistDetailsApi(artist)
        call.enqueue(object : retrofit2.Callback<ArtistDetailsApiResponse> {
            override fun onResponse(call: Call<ArtistDetailsApiResponse>, response: Response<ArtistDetailsApiResponse>) {
                if (response.code() == 200) {
                    textView3.text = response.body()!!.artist.bio.summary
                }

                spinKitMasterView.visibility = View.GONE
            }
            override fun onFailure(call: Call<ArtistDetailsApiResponse>, t: Throwable) {
                Log.e("error", t.toString())
                spinKitMasterView.visibility = View.GONE
            }
        })
    }
}
