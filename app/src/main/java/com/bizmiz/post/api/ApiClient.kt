package com.bizmiz.post.api

import com.bizmiz.post.util.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        lateinit var gson: Gson
        lateinit var retrofit: Retrofit
        fun getPost():Retrofit{
      if (!::gson.isInitialized){
          gson = GsonBuilder()
              .setLenient()
              .create()
      }
            if (!::retrofit.isInitialized){
                retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }
    }
}