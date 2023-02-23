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
import com.vishesh.musicwiki.Api_implementation.TrackApiResponse
import com.vishesh.musicwiki.Api_implementation.TrackListResponse
import com.vishesh.musicwiki.R
import com.vishesh.musicwiki.TagsFragmentDetailsScreen.adapters.TrackRecyclerAdpater
import kotlinx.android.synthetic.main.fragment_tracks.view.*
import retrofit2.Call
import retrofit2.Response

class TracksFragment : Fragment() {

    lateinit var tracksRecyclerView: RecyclerView
    lateinit var adapter: TrackRecyclerAdpater
    lateinit var spinKitMasterView: LinearLayout
    var trackList: MutableList<TrackListResponse> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_tracks, container, false)

        tracksRecyclerView = v.tracksRecyclerView
        spinKitMasterView = v.spinKitMasterView

        initTabRecycler()
        getTracksApi()

        return v
    }

    fun initTabRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(ac) as RecyclerView.LayoutManager
        tracksRecyclerView.layoutManager = layoutManager
        tracksRecyclerView.layoutManager = GridLayoutManager(ac, 2)
        adapter = TrackRecyclerAdpater(trackList, ac, "track")
        tracksRecyclerView.adapter = adapter
    }

    fun getTracksApi() {
        spinKitMasterView.visibility = View.VISIBLE
        val api: RetrofitInstance = RetrofitInstance()
        var call: Call<TrackApiResponse> = api.getTracksApi(tagName)
        call.enqueue(object : retrofit2.Callback<TrackApiResponse> {
            override fun onResponse(call: Call<TrackApiResponse>, response: Response<TrackApiResponse>) {
                if (response.code() == 200) {
                    trackList = response.body()!!.tracks.track
                    adapter.updateDataset(trackList)
                }

                spinKitMasterView.visibility = View.GONE
            }
            override fun onFailure(call: Call<TrackApiResponse>, t: Throwable) {
                Log.e("error", t.toString())
                spinKitMasterView.visibility = View.GONE
            }
        })
    }

    companion object {

        private lateinit var ctx: Context
        private lateinit var tagName: String
        private lateinit var ac: Activity
        fun newInstance(name: String, a: Activity): TracksFragment {
            val f = TracksFragment()
            this.tagName = name
            ac = a
            return f
        }
    }
}
