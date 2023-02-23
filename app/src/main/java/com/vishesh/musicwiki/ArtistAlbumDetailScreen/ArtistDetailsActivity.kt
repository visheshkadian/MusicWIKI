package com.vishesh.musicwiki.ArtistAlbumDetailScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rekord.movieapplication.Retrofit.RetrofitInstance
import com.vishesh.musicwiki.Api_implementation.*
import com.vishesh.musicwiki.R
import kotlinx.android.synthetic.main.activity_artist_details.*
import kotlinx.android.synthetic.main.activity_artist_details.backBtn
import kotlinx.android.synthetic.main.activity_artist_details.backImage
import kotlinx.android.synthetic.main.activity_artist_details.spinKitMasterView
import kotlinx.android.synthetic.main.activity_artist_details.tagNametv
import kotlinx.android.synthetic.main.activity_artist_details.textView3
import retrofit2.Call
import retrofit2.Response

class ArtistDetailsActivity : AppCompatActivity() {
    var artist: String = ""
    var imageData: String = ""
    lateinit var adapter: AlbumRecyclerAdapter
    var albumList: MutableList<AlbumListResponse> = arrayListOf()

    lateinit var adapterTracks: TrackRecyclerAdapter
    var trackList: MutableList<TrackListResponse> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_details)
        artist = intent.getStringExtra("artist")!!
        imageData = intent.getStringExtra("image")!!

        tagNametv.text = artist

        Glide
            .with(this)
            .load(imageData)
            .centerCrop()
            .into(backImage)

        initAlbumRecycler()
        initTrackRecycler()
        getArtistDetailsApi()
        getArtistTopAlbums()
        getArtistTopTracks()

        backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    fun initAlbumRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) as RecyclerView.LayoutManager
        topAlbumsRecyclerView.layoutManager = layoutManager
        adapter = AlbumRecyclerAdapter(albumList, this, "album")
        topAlbumsRecyclerView.adapter = adapter
    }

    fun initTrackRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) as RecyclerView.LayoutManager
        topTracksRecyclerView.layoutManager = layoutManager
        adapterTracks = TrackRecyclerAdapter(trackList, this, "track")
        topTracksRecyclerView.adapter = adapterTracks
    }

    fun getArtistTopAlbums() {
        spinKitMasterView.visibility = View.VISIBLE
        val api: RetrofitInstance = RetrofitInstance()
        var call: Call<ArtistTopAlbumsApiResponse> = api.getArtistTopAlbums(artist)
        call.enqueue(object : retrofit2.Callback<ArtistTopAlbumsApiResponse> {
            override fun onResponse(call: Call<ArtistTopAlbumsApiResponse>, response: Response<ArtistTopAlbumsApiResponse>) {
                if (response.code() == 200) {
                    albumList = response.body()!!.topalbums.album
                    adapter.updateDataset(albumList)
                }

                spinKitMasterView.visibility = View.GONE
            }
            override fun onFailure(call: Call<ArtistTopAlbumsApiResponse>, t: Throwable) {
                Log.e("error", t.toString())
                spinKitMasterView.visibility = View.GONE
            }
        })
    }

    fun getArtistTopTracks() {
        spinKitMasterView.visibility = View.VISIBLE
        val api: RetrofitInstance = RetrofitInstance()
        var call: Call<ArtistTopTracksApiResponse> = api.getArtistTopTracks(artist)
        call.enqueue(object : retrofit2.Callback<ArtistTopTracksApiResponse> {
            override fun onResponse(call: Call<ArtistTopTracksApiResponse>, response: Response<ArtistTopTracksApiResponse>) {
                if (response.code() == 200) {
                    trackList = response.body()!!.toptracks.track
                    adapterTracks.updateDataset(trackList)
                }

                spinKitMasterView.visibility = View.GONE
            }
            override fun onFailure(call: Call<ArtistTopTracksApiResponse>, t: Throwable) {
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
                    playcountTv.text = "${response.body()!!.artist.stats.playcount.substring(0,3)}K"
                    followersTv.text = "${response.body()!!.artist.stats.listeners.substring(0,3)}K"
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
