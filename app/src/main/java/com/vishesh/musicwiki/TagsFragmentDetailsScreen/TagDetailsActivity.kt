package com.vishesh.musicwiki.TagsFragmentDetailsScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rekord.movieapplication.Retrofit.RetrofitInstance
import com.vishesh.musicwiki.Api_implementation.TagDetailsApiResponse
import com.vishesh.musicwiki.R
import kotlinx.android.synthetic.main.activity_tag_details.*
import retrofit2.Call
import retrofit2.Response

class TagDetailsActivity : AppCompatActivity() {

    var tag: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_details)

        tag = intent.getStringExtra("tagName")!!

        val adapter = ViewPagerAdpater(supportFragmentManager)
        adapter.addFragment(AlbumFragment.newInstance(tag, this), "ALBUMS")
        adapter.addFragment(ArtistFragment.newInstance(tag, this), "ARTISTS")
        adapter.addFragment(TracksFragment.newInstance(tag, this), "TRACKS")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        getTagsApi()

        backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    fun getTagsApi() {
        spinKitMasterView.visibility = View.VISIBLE
        val api: RetrofitInstance = RetrofitInstance()
        var call: Call<TagDetailsApiResponse> = api.getTagsDetailsApi(tag.toString())
        call.enqueue(object : retrofit2.Callback<TagDetailsApiResponse> {
            override fun onResponse(call: Call<TagDetailsApiResponse>, response: Response<TagDetailsApiResponse>) {
                if (response.code() == 200) {
                    textView3.text = response.body()!!.tag.wiki!!.summary
                    tagNametv.text = response.body()!!.tag.name
                }

                spinKitMasterView.visibility = View.GONE
            }
            override fun onFailure(call: Call<TagDetailsApiResponse>, t: Throwable) {
                Log.e("error", t.toString())
                spinKitMasterView.visibility = View.GONE
            }
        })
    }
}
