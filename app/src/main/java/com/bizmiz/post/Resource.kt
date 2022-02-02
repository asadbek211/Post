package com.bizmiz.post

import com.bizmiz.post.model.PostModel

open class Resource<out T> constructor(
    val status: ResourceState,
    val data: T?,
    val model:PostModel?,
    val listPost:List<Any?>?,
    val list: List<Int?>?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(ResourceState.SUCCESS, data,null, null,null,null)
        }
        fun <T> post(model: PostModel?):Resource<T>{
            return Resource(ResourceState.POST,null,model,null,null,null)
        }
        fun <T> put(listPost:List<Any?>):Resource<T>{
            return Resource(ResourceState.PUT,null,null,listPost,null,null)
        }
        fun <T> delete(list: List<Int?>):Resource<T>{
            return Resource(ResourceState.DELETE,null,null,null,list,null)
        }
        fun <T> error(message: String?): Resource<T> {
            return Resource(ResourceState.ERROR, null,null,null,null,message)
        }

        fun <T> loading(): Resource<T> {
            return Resource(ResourceState.LOADING, null,null, null,null,null)
        }
    }
}

enum class ResourceState {
    LOADING, SUCCESS,POST,PUT,DELETE, ERROR
}