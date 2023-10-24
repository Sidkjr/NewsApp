package com.example.newsapp.Common

import com.example.newsapp.Interface.NewsService
import com.example.newsapp.remote.RetrofitClient

object Common {
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = ""

    val newsService: NewsService
    get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source: String): String {
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")

            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }
}