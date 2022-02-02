package com.bizmiz.post

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bizmiz.post.adapter.PostAdapter
import com.bizmiz.post.databinding.ActivityMainBinding
import com.bizmiz.post.model.PostModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postAdapter = PostAdapter()
        binding.btnSearch.setOnClickListener {
            mainViewModel.getPosts(binding.etUserId.text.toString().toInt())
        }
        binding.fabAdd.setOnClickListener {
            showDialog()
        }
        binding.recView.adapter = postAdapter
        getPosts()
        postAdapter.setOnPostDeleteListener { post, position ->
            mainViewModel.deletePost(post, position)
        }
        postAdapter.setOnItemDeleteListener { position ->
            postAdapter.list.removeAt(position)
            postAdapter.notifyDataSetChanged()

        }
        postAdapter.setOnPostEditListener { post, position ->
            val customLayout = layoutInflater.inflate(R.layout.dialog_item_edit, null)
            val postId = customLayout.findViewById<EditText>(R.id.etPostId)
            val userId = customLayout.findViewById<EditText>(R.id.etUserId)
            val title = customLayout.findViewById<EditText>(R.id.etTitle)
            val body = customLayout.findViewById<EditText>(R.id.etBody)
            val btnCancel = customLayout.findViewById<Button>(R.id.btnCancel)
            val btnSave = customLayout.findViewById<Button>(R.id.btnSave)
            val dialog = AlertDialog.Builder(this)
                .setView(customLayout)
                .setCancelable(false)
                .create()
            dialog.show()
            postId.setText(post.id.toString())
            userId.setText(post.userId.toString())
            title.setText(post.title)
            body.setText(post.body)
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnSave.setOnClickListener {
                mainViewModel.editPost(
                    PostModel(
                        userId.text.toString().toInt(),
                        postId.text.toString().toInt(),
                        title.text.toString(),
                        body.text.toString()
                    ), position
                )
                dialog.dismiss()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPosts() {
        mainViewModel.getPost.observe(this, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    postAdapter.list = (it.data as ArrayList<PostModel>?)!!
                }
                ResourceState.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceState.POST -> {
                    Snackbar.make(binding.root, "Malumotlar saqlandi", Snackbar.LENGTH_SHORT).show()
                    it.model?.let { it1 -> postAdapter.list.add(count, it1) }
                    postAdapter.notifyDataSetChanged()
                    count++
                }
                ResourceState.DELETE -> {
                    Snackbar.make(
                        binding.root,
                        "postId:${it.list?.get(1)?.toString()} deleted",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    it.list?.get(0)?.let { it1 -> postAdapter.itemDelete.invoke(it1) }
                }
                ResourceState.PUT->{
                    val post:PostModel = it.listPost?.get(0) as PostModel
                    Snackbar.make(
                        binding.root,
                        "userId:${post.userId} edited",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    val position = it.listPost[1] as Int
                    postAdapter.list[position] = post
                    postAdapter.notifyItemChanged(position)
                }
            }
        })
    }

    private fun pushPost(userId: Int, title: String, body: String) {
        mainViewModel.pushPost(PostModel(userId, null, title, body))
    }

    private fun showDialog() {
        val customLayout = layoutInflater.inflate(R.layout.dialog_item, null)
        val userId = customLayout.findViewById<EditText>(R.id.etUserId)
        val title = customLayout.findViewById<EditText>(R.id.etTitle)
        val body = customLayout.findViewById<EditText>(R.id.etBody)
        val btnCancel = customLayout.findViewById<Button>(R.id.btnCancel)
        val btnSave = customLayout.findViewById<Button>(R.id.btnSave)
        val dialog = AlertDialog.Builder(this)
            .setView(customLayout)
            .setCancelable(false)
            .create()
        dialog.show()
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnSave.setOnClickListener {
            if (userId.text.isNotEmpty() && title.text.isNotEmpty() && body.text.isNotEmpty()) {
                pushPost(
                    userId.text.trim().toString().toInt(),
                    title.text.trim().toString(),
                    body.text.trim().toString()
                )
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Qatorlarni to'ldiring", Toast.LENGTH_SHORT).show()
            }
        }
    }
}