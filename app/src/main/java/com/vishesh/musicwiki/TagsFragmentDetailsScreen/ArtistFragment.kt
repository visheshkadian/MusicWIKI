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
import com.vishesh.musicwiki.Api_implementation.ArtistApiResponse
import com.vishesh.musicwiki.Api_implementation.ArtistListResponse
import com.vishesh.musicwiki.R
import com.vishesh.musicwiki.TagsFragmentDetailsScreen.adapters.ArtistRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_album.view.spinKitMasterView
import kotlinx.android.synthetic.main.fragment_artist.view.*
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ArtistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArtistFragment : Fragment() {

    lateinit var artistRecyclerView: RecyclerView
    lateinit var adapter: ArtistRecyclerAdapter
    lateinit var spinKitMasterView: LinearLayout
    var artistList: MutableList<ArtistListResponse> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_artist, container, false)

        artistRecyclerView = v.artistRecyclerView
        spinKitMasterView = v.spinKitMasterView

        initTabRecycler()
        getArtistApi()

        return v
    }

    fun initTabRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(ac) as RecyclerView.LayoutManager
        artistRecyclerView.layoutManager = layoutManager
        artistRecyclerView.layoutManager = GridLayoutManager(ac, 2)
        adapter = ArtistRecyclerAdapter(artistList, ac, "artist")
        artistRecyclerView.adapter = adapter
    }

    fun getArtistApi() {
        spinKitMasterView.visibility = View.VISIBLE
        val api: RetrofitInstance = RetrofitInstance()
        var call: Call<ArtistApiResponse> = api.getArtistApi(tagName)
        call.enqueue(object : retrofit2.Callback<ArtistApiResponse> {
            override fun onResponse(call: Call<ArtistApiResponse>, response: Response<ArtistApiResponse>) {
                if (response.code() == 200) {
                    artistList = response.body()!!.topartists.artist
                    adapter.updateDataset(artistList)
                }

                spinKitMasterView.visibility = View.GONE
            }
            override fun onFailure(call: Call<ArtistApiResponse>, t: Throwable) {
                Log.e("error", t.toString())
                spinKitMasterView.visibility = View.GONE
            }
        })
    }

    companion object {

        private lateinit var ctx: Context
        private lateinit var tagName: String
        private lateinit var ac: Activity
        fun newInstance(name: String, a: Activity): ArtistFragment {
            val f = ArtistFragment()
            this.tagName = name
            ac = a
            return f
        }
    }
}
