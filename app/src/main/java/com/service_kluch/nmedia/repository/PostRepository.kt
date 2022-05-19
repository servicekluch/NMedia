package com.service_kluch.nmedia.repository

import androidx.lifecycle.LiveData
import com.service_kluch.nmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}