package com.example.newsapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Common.ISO8601DateParser
import com.example.newsapp.Interface.ItemClickListener
import com.example.newsapp.ListNewsActivity
import com.example.newsapp.NewsDetailsActivity
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.model.WebSite
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_layout.view.*
import kotlinx.android.synthetic.main.source_news_layout.view.*
import java.text.ParseException
import java.util.Date

class ListNewsAdapter(val context: Context, val articlelist: MutableList<Article>):
    RecyclerView.Adapter<ListNewsAdapter.ListNewsViewHolder>() {
    class ListNewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var itemClickListener: ItemClickListener

        var article_title = itemView.article_title
        var article_time = itemView.article_time
        var article_image = itemView.article_image

        init {
            itemView.setOnClickListener(this)
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }
        override fun onClick(v: View?) {
            itemClickListener.onClick(v!!, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        return ListNewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return articlelist.size
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        Picasso.get().load(articlelist[position].urlToImage).into(holder.article_image)

        if (articlelist[position].title!!.length > 65) {
            holder.article_title.text = articlelist[position].title!!.substring(0, 65) + "..."
        }
        else {
            holder.article_title.text = articlelist[position].title!!
        }

        if (articlelist[position].publishedAt != null) {
            var date: Date? = null
            try {
                date = ISO8601DateParser.parse(articlelist[position].publishedAt!!)
            } catch (ex: ParseException) {
                ex.printStackTrace()

            }
            holder.article_time.setReferenceTime(date!!.time)
        }

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val details = Intent(context, NewsDetailsActivity::class.java)
                details.putExtra("webURL", articlelist[position].url)
                details.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(details)
            }
        })
    }
}