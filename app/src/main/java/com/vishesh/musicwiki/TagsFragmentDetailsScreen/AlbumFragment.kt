package com.vishesh.musicwiki.TagsFragmentDetailsScreen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rekord.movieapplication.Retrofit.RetrofitInstance
import com.vishesh.musicwiki.Api_implementation.AlbumApiResponse
import com.vishesh.musicwiki.Api_implementation.AlbumListResponse
import com.vishesh.musicwiki.R
import com.vishesh.musicwiki.TagsFragmentDetailsScreen.adapters.TagDetailsRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_album.view.*
import retrofit2.Call
import retrofit2.Response

class AlbumFragment : Fragment() {

    lateinit var albumRecyclerView: RecyclerView
    lateinit var adapter: TagDetailsRecyclerAdapter
    lateinit var spinKitMasterView: LinearLayout

    var albumList: MutableList<AlbumListResponse> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_album, container, false)

        albumRecyclerView = v.albumRecyclerView
        spinKitMasterView = v.spinKitMasterView

        initTabRecycler()
        getTagsApi()

        return v
    }

    fun initTabRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(ac) as RecyclerView.LayoutManager
        albumRecyclerView.layoutManager = layoutManager
        albumRecyclerView.layoutManager = GridLayoutManager(ac, 2)
        adapter = TagDetailsRecyclerAdapter(albumList, ac, "album")
        albumRecyclerView.adapter = adapter
    }

    fun getTagsApi() {
        spinKitMasterView.visibility = View.VISIBLE
        val api: RetrofitInstance = RetrofitInstance()
        var call: Call<AlbumApiResponse> = api.getAlbumApi(tagName)
        call.enqueue(object : retrofit2.Callback<AlbumApiResponse> {
            override fun onResponse(call: Call<AlbumApiResponse>, response: Response<AlbumApiResponse>) {
                if (response.code() == 200) {
                    albumList = response.body()!!.albums.album

                    adapter.updateDataset(albumList)
                }

                spinKitMasterView.visibility = View.GONE
            }
            override fun onFailure(call: Call<AlbumApiResponse>, t: Throwable) {
                Log.e("error", t.toString())
                spinKitMasterView.visibility = View.GONE
            }
        })
    }
    companion object {

        private lateinit var ctx: Context
        private lateinit var tagName: String
        private lateinit var ac: Activity
        fun newInstance(name: String, a: Activity): AlbumFragment {
            val f = AlbumFragment()
            this.tagName = name
            ac = a
            return f
        }
    }
}
