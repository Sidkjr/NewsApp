package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Adapter.ListNewsAdapter
import com.example.newsapp.Common.Common
import com.example.newsapp.Interface.NewsService
import com.example.newsapp.model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNewsActivity : AppCompatActivity() {

    var source = ""
    var webHotURL: String? = ""

    lateinit var dialog: AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService

        dialog = SpotsDialog.Builder().setContext(this).setCancelable(false).build()

        swipe_to_refresh.setOnRefreshListener {
            loadNews(source, true)
        }

        diagonallayout.setOnClickListener {
            val detail = Intent(baseContext, NewsDetailsActivity::class.java)
            detail.putExtra("webURL", webHotURL)
            startActivity(detail)
        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if (intent != null) {
            source = intent.getStringExtra("source").toString()
            if (!source.isEmpty()) {
                loadNews(source, false)
            }
        }
    }

    private fun loadNews(sources: String?, isRefreshed: Boolean) {

        if (!isRefreshed) {
            dialog.show()

            mService.getNewsFromSource(Common.getNewsAPI(sources!!)).enqueue(object :
                Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    dialog.dismiss()
                    Picasso.get().load(response.body()!!.articles!![0].urlToImage).into(top_image)

                    top_title.text = response.body()!!.articles!![0].title
                    top_author.text = response.body()!!.articles!![0].author

                    webHotURL = response.body()!!.articles!![0].url

                    val removeFirstItem = response.body()!!.articles

                    removeFirstItem!!.removeAt(0)

                    adapter = ListNewsAdapter(baseContext, removeFirstItem)
                    adapter.notifyDataSetChanged()
                    list_news.adapter = adapter


                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

        else {
            swipe_to_refresh.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsAPI(sources!!)).enqueue(object :
                Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    swipe_to_refresh.isRefreshing = false
                    Picasso.get().load(response.body()!!.articles!![0].urlToImage).into(top_image)

                    top_title.text = response.body()!!.articles!![0].title
                    top_author.text = response.body()!!.articles!![0].author

                    webHotURL = response.body()!!.articles!![0].url

                    val removeFirstItem = response.body()!!.articles

                    removeFirstItem!!.removeAt(0)

                    adapter = ListNewsAdapter(baseContext, removeFirstItem)
                    adapter.notifyDataSetChanged()
                    list_news.adapter = adapter


                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
}