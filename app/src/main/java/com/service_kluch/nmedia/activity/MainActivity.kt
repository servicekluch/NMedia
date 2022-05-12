package com.service_kluch.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.service_kluch.nmedia.R
import com.service_kluch.nmedia.adapter.PostAdapter
import com.service_kluch.nmedia.adapter.OnInteractionListener
import com.service_kluch.nmedia.viewmodel.PostViewModel
import com.service_kluch.nmedia.databinding.ActivityMainBinding
import com.service_kluch.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    val viewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PostAdapter(
            object : OnInteractionListener {
                override fun onEditClicked(post: Post) {
                    val intent = Intent(this@MainActivity, NewPostActivity::class.java).apply {
                        action = "post.content"
                        putExtra("post.text", post.content)
                    }
                    newPostLauncher.launch(intent)

                    viewModel.edit(post)
                }

                override fun onRemoveClicked(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLikeClicked(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClicked(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(shareIntent)
                    viewModel.shareById(post.id)
                }

                override fun onVideoClicked(post: Post) {
                    if (viewModel.getUri(post)) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.video)))
                    }
                }
            }
        )

        binding.listPost.adapter = adapter
        binding.listPost.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        this.viewModel.liveData.observe(this, adapter::submitList)
        binding.fab.setOnClickListener {
            val intent = Intent(this, NewPostActivity::class.java)
            newPostLauncher.launch(intent)
        }
    }
    val newPostLauncher  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            return@registerForActivityResult
        }
        result.data?.getStringExtra(Intent.EXTRA_TEXT)?.let {
            viewModel.changeContent(it)
            viewModel.save()
        }
    }
}
