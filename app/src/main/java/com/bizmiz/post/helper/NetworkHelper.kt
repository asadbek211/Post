package com.bizmiz.post.helper

import android.util.Log
import com.bizmiz.post.api.ApiService
import com.bizmiz.post.model.PostModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NetworkHelper(apiClient: Retrofit) {
    private var call: ApiService = apiClient.create(ApiService::class.java)
    fun getPosts(
        userId: Int,
        onSuccess: (data: List<PostModel>) -> Unit,
        onFailure: (message: String?) -> Unit
    ) {
        call.getPosts(userId)
            .enqueue(object : Callback<List<PostModel>> {
                override fun onResponse(
                    call: Call<List<PostModel>>,
                    response: Response<List<PostModel>>
                ) {
                    CoroutineScope(Dispatchers.Main).launch {
                        response.body()?.let { onSuccess.invoke(it) }
                    }
                }

                override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                    onFailure(t.localizedMessage)
                }

            })
    }

    fun pushPost(
        postModel: PostModel,
        onSuccess: (model: PostModel) -> Unit,
        onFailure: (message: String?) -> Unit
    ) {
        call.pushPost(postModel)
            .enqueue(object : Callback<PostModel> {
                override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                    response.body()?.let { onSuccess.invoke(response.body()!!) }
                }

                override fun onFailure(call: Call<PostModel>, t: Throwable) {
                    onFailure(t.localizedMessage)
                }

            })
    }

    fun deletePost(
        postModel: PostModel,
        position: Int,
        onSuccess: (list: List<Int?>) -> Unit,
        onFailure: (message: String?) -> Unit
    ) {
        call.getPostDeleteByUserId(postModel.userId)
            .enqueue(object : Callback<PostModel> {
                override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                    onSuccess.invoke(listOf(position, postModel.id))
                }

                override fun onFailure(call: Call<PostModel>, t: Throwable) {
                    onFailure.invoke(t.localizedMessage)
                }

            })
    }

    fun editPost(
        postModel: PostModel,
        position: Int,
        onSuccess: (listPost:List<Any?>) -> Unit,
        onFailure: (message: String?) -> Unit
    ) {
     call.getPostPutByUserId(postModel.userId,postModel)
         .enqueue(object :Callback<PostModel>{
             override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                 Log.d("msg",response.body().toString())
                onSuccess.invoke(listOf(response.body(),position))
             }

             override fun onFailure(call: Call<PostModel>, t: Throwable) {
                 onFailure.invoke(t.localizedMessage)
             }

         })
    }
}