package com.vishesh.musicwiki.MainScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rekord.movieapplication.Retrofit.RetrofitInstance
import com.vishesh.musicwiki.Api_implementation.TagsApiResponse
import com.vishesh.musicwiki.Api_implementation.TagsListResponse
import com.vishesh.musicwiki.R
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter: GenreRecyclerAdapter
    var tagList: MutableList<TagsListResponse> = arrayListOf()

    var openList = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTabRecycler()

        dropDownBtn.setOnClickListener {
            if (openList) {
                openList = false
                dropDownBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24))
                adapter.updateDataset(tagList.subList(0, 10))
            } else {
                openList = true
                dropDownBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24))
                adapter.updateDataset(tagList)
            }
        }

        getTagsApi()
    }

    fun initTabRecycler() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager
        genereRecyclerView.layoutManager = layoutManager
        genereRecyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = GenreRecyclerAdapter(tagList, this)
        genereRecyclerView.adapter = adapter
    }

    fun getTagsApi() {
        spinKitMasterView.visibility = View.VISIBLE
        val api: RetrofitInstance = RetrofitInstance()
        var call: Call<TagsApiResponse> = api.getTagsApi()
        call.enqueue(object : retrofit2.Callback<TagsApiResponse> {
            override fun onResponse(call: Call<TagsApiResponse>, response: Response<TagsApiResponse>) {
                if (response.code() == 200) {
                    tagList = response.body()!!.tags.tag
                    if (tagList.size > 10) {
                        adapter.updateDataset(tagList.subList(0, 10))
                        linearLayout.visibility = View.VISIBLE
                    } else {
                        adapter.updateDataset(tagList)
                        linearLayout.visibility = View.GONE
                    }
                }

                spinKitMasterView.visibility = View.GONE
            }
            override fun onFailure(call: Call<TagsApiResponse>, t: Throwable) {
                Log.e("error", t.toString())
                spinKitMasterView.visibility = View.GONE
            }
        })
    }
}
