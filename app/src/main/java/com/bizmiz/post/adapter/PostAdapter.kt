package com.bizmiz.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bizmiz.post.databinding.ItemBinding
import com.bizmiz.post.model.PostModel

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private var isRemove = false
    inner class PostViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(postModel: PostModel, position: Int) {
            binding.txtUserId.text = "UserId: ${postModel.userId}"
            binding.txtPostId.text = "PostId: ${postModel.id}"
            binding.txtTitle.text = "Title: ${postModel.title}"
            binding.txtBody.text = "Body: ${postModel.body}"
            binding.delete.setOnClickListener {
                postDelete.invoke(postModel,position)
            }
            binding.edit.setOnClickListener {
                postEdit.invoke(postModel,position)
            }
        }

    }

    var itemDelete: (position: Int) -> Unit = {}
    fun setOnItemDeleteListener(itemDelete: (position: Int) -> Unit) {
        this.itemDelete = itemDelete
    }

    var postDelete: (postModel: PostModel,position:Int) -> Unit = { _, _ ->  }
    fun setOnPostDeleteListener(postDelete: (postModel: PostModel,position:Int) -> Unit) {
        this.postDelete = postDelete
    }

    var postEdit: (postModel: PostModel,position:Int) -> Unit = {_,_ ->}
    fun setOnPostEditListener(postEdit: (postModel: PostModel,position:Int) -> Unit) {
        this.postEdit = postEdit
    }

    var list: ArrayList<PostModel> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.populateModel(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}