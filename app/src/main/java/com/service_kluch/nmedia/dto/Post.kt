package com.service_kluch.nmedia.dto

data class Post(
    val id: Long,
    val authorName: String,
    val content: String,
    val published: String,
    val likeByMe: Boolean = false,
    val likeCount: Long = 0,
    val shareCount: Long = 0,
    val watchesCount: Long = 0
)
