package com.bizmiz.post.api

import com.bizmiz.post.model.PostModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // serverdan malumotlarni olish
    @GET("posts?")
    fun getPosts(
        @Query("userId") userId: Int
    ): Call<List<PostModel>>

    // serverga malumotlarni yozish
    @POST("posts")
    fun pushPost(
        @Body postModel: PostModel
    ): Call<PostModel>

//    @FormUrlEncoded
//    @POST("posts")
//    fun pushPostParameters(
//        @Field("userId") userId: Int,
//        @Field("id") id: Int,
//        @Field("title") title: String,
//        @Field("body") body: String,
//    ): Call<PostModel>

    // serverdagi post ning userId si biz kiritgan userId ga teng bo'lgan postni malumotlarini yangilaydi
    @PATCH("posts/{userId}")
    fun getPostPutByUserId(
        @Path("userId") userId: Int, @Body postModel: PostModel
    ): Call<PostModel>

    // serverdagi post ning userId si biz kiritgan userId ga teng bo'lgan postni o'chiradi
    @DELETE("posts/{userId}")
    fun getPostDeleteByUserId(
        @Path("userId") userId: Int
    ): Call<PostModel>
}