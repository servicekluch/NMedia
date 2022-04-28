package com.service_kluch.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.service_kluch.nmedia.adapter.PostAdapter
import com.service_kluch.nmedia.viewmodel.PostViewModel
import com.service_kluch.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(myBinding.root)

        val myViewModel: PostViewModel by viewModels()
        val adapter = PostAdapter (
            { myViewModel.likeById(it.id) },
            { myViewModel.shareById(it.id) }
        )


        myBinding.recyclerViewConstraint.adapter = adapter
        myViewModel.liveData.observe(this@MainActivity) {
            adapter.submitList(it)
        }

    }
}
