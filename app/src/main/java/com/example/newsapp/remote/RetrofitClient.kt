package com.example.newsapp.remote

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url

object RetrofitClient {
    private var retrofit: Retrofit? = null
    fun getClient(baseUrl: String): Retrofit {
        if(retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
}