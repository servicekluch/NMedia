package com.service_kluch.nmedia

data class Post(
    val id: Long,
    val authorName: String,
    val content: String,
    val published: String,
    var likeByMe: Boolean = false,
    var likeCount: Int = 0,
    var shareCount: Int = 0,
    var watchesCount: Int = 0
)
