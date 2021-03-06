package com.service_kluch.nmedia.dao

import com.service_kluch.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post): Post
}