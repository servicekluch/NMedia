package com.service_kluch.nmedia

data class Post(
    val id: Long,
    val authorName: String,
    val content: String,
    val published: String,
    val likeByMe: Boolean = false,
    val likeCount: Int = 0,
    val shareCount: Int = 0,
    val watchesCount: Int = 0
)
