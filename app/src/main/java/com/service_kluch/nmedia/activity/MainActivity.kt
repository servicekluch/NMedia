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
        val myBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(myBinding.recyclerViewConstraint)

        val myViewModel: PostViewModel by viewModels()

        val adapter = PostAdapter(
            object: OnInteractionListener {
                override fun onEditClicked(post: Post) {
                    myBinding.editGroup.visibility = Group.VISIBLE
                    myViewModel.edit(post)
                }

                override fun onRemoveClicked(post: Post) {
                    myViewModel.removeById(post.id)
                }

                override fun onLikeClicked(post: Post) {
                    myViewModel.likeById(post.id)
                }

                override fun onShareClicked(post: Post) {
                    myViewModel.shareById(post.id)
                }

            }
        )

        myBinding.recyclerViewConstraint.adapter = adapter
        myBinding.recyclerViewConstraint.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        myViewModel.liveData.observe(this, adapter::submitList)
        myViewModel.editedPost.observe(this) {
            myBinding.content.setText(it.content)
            myBinding.editableText.setText(it.content)
            if (it.content.isNotBlank()) {
                myBinding.content.requestFocus()
            }
        }

        myBinding.save.setOnClickListener{
            val text = myBinding.content.text?.toString().orEmpty()
            if (text.isBlank()) {
                Toast.makeText(this, getString(R.string.empty_post), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            myViewModel.changeContent(text)
            myViewModel.save()
            myBinding.content.clearFocus()
            it.hideKeyboard()
            myBinding.editGroup.visibility = Group.GONE
        }
        myBinding.cancel.setOnClickListener{
            myViewModel.cancelEditing()
            myBinding.content.clearFocus()
            it.hideKeyboard()
            myBinding.editGroup.visibility = Group.GONE
        }
    }
}
