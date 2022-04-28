package com.service_kluch.nmedia.repository

import androidx.lifecycle.LiveData
import com.service_kluch.nmedia.dto.Post

interface PostRepository {
    val listData: LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
}