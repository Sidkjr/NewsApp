package com.example.newsapp.Interface

import com.example.newsapp.model.News
import com.example.newsapp.model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {
    @get: GET(value = "v2/top-headlines/sources?apiKey=YOUR_API_KEY")
    val sources: Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url: String): Call<News>
}