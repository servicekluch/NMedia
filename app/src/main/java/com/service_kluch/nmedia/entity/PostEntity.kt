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
    val likeByMe: Boolean,
    val likeCount: Long = 0,
    val shareCount: Long = 0,
    val watchesCount: Long = 0,
    val video: String = ""
) {
    fun toDto() = Post(
        id, authorName, published, content, likeByMe,
        likeCount, shareCount, watchesCount, video
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.authorName, dto.published, dto.content, dto.likeByMe,
                dto.likeCount, dto.shareCount, dto.watchesCount, dto.video)
    }
}