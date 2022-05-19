package com.service_kluch.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.service_kluch.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorName: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likedCount: Long = 0,
    val sharedCount: Long = 0,
    val viewedCount: Long = 0,
    val videoURL: String = "",
) {
    fun toDto() = Post(
        id, authorName, published, content, likedByMe,
        likedCount, sharedCount, viewedCount, ""
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.authorName, dto.published, dto.content, dto.likedByMe,
                dto.likedCount, dto.sharedCount, dto.viewedCount, dto.videoUrl)
    }
}