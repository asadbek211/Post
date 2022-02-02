package com.bizmiz.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.post.helper.NetworkHelper
import com.bizmiz.post.model.PostModel

class MainViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val postsList: MutableLiveData<Resource<List<PostModel>>> = MutableLiveData()
    val getPost: LiveData<Resource<List<PostModel>>>
        get() = postsList

    fun getPosts(userId: Int) {
        postsList.value = Resource.loading()
        networkHelper.getPosts(userId, {
            postsList.value = Resource.success(it)
        }, {
            postsList.value = Resource.error(it)
        })
    }

    fun pushPost(postModel: PostModel) {
        postsList.value = Resource.loading()
        networkHelper.pushPost(postModel, {
            postsList.value = Resource.post(it)
        }, {
            postsList.value = Resource.error(it)
        })
    }

    fun deletePost(postModel: PostModel,position:Int) {
        postsList.value = Resource.loading()
        networkHelper.deletePost(postModel,position, {
              postsList.value = Resource.delete(it)
        }, {
          postsList.value = Resource.error(it)
        })
    }
    fun editPost(postModel: PostModel,position:Int) {
        postsList.value = Resource.loading()
        networkHelper.editPost(postModel,position, {
            postsList.value = Resource.put(it)
        }, {
            postsList.value = Resource.error(it)
        })
    }
}