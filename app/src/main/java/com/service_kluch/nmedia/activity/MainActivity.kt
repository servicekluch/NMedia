package com.service_kluch.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.DividerItemDecoration
import com.service_kluch.nmedia.R
import com.service_kluch.nmedia.adapter.PostAdapter
import com.service_kluch.nmedia.adapter.OnInteractionListener
import com.service_kluch.nmedia.viewmodel.PostViewModel
import com.service_kluch.nmedia.databinding.ActivityMainBinding
import com.service_kluch.nmedia.dto.Post
import com.service_kluch.nmedia.util.AndroidUtils.hideKeyboard


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(
            object: OnInteractionListener {
                override fun onEditClicked(post: Post) {
                    binding.editGroup.visibility = Group.VISIBLE
                    viewModel.edit(post)
                }

                override fun onRemoveClicked(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLikeClicked(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClicked(post: Post) {
                    viewModel.shareById(post.id)
                }

            }
        )

        binding.listPost.adapter = adapter
        binding.listPost.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        viewModel.liveData.observe(this, adapter::submitList)
        viewModel.editedPost.observe(this) {
            binding.textContent.setText(it.content)
            binding.editableText.setText(it.content)
            if (it.content.isNotBlank()) {
                binding.textContent.requestFocus()
            }
        }

        binding.buttonSave.setOnClickListener{
            val text = binding.textContent.text?.toString().orEmpty()
            if (text.isBlank()) {
                Toast.makeText(this, getString(R.string.empty_post), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.changeContent(text)
            viewModel.save()
            binding.textContent.clearFocus()
            it.hideKeyboard()
            binding.editGroup.visibility = Group.GONE
        }
        binding.buttonCancel.setOnClickListener{
            viewModel.cancelEditing()
            binding.textContent.clearFocus()
            it.hideKeyboard()
            binding.editGroup.visibility = Group.GONE
        }
    }
}
