package com.service_kluch.nmedia.dto

data class Post(
    val id: Long,
    val authorName: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likedCount: Long = 0,
    val sharedCount: Long = 0,
    val viewedCount: Long = 0,
    val videoUrl: String
)
