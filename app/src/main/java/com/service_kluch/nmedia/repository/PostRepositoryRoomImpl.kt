package com.service_kluch.nmedia.repository

import androidx.lifecycle.Transformations
import com.service_kluch.nmedia.dao.PostDaoRoom
import com.service_kluch.nmedia.dto.Post
import com.service_kluch.nmedia.entity.PostEntity

class PostRepositoryRoomImpl(
    private val daoRoom: PostDaoRoom,
) : PostRepository {
    override fun getAll() = Transformations.map(daoRoom.getAll()) { list ->
        list.map {
            it.toDto()
        }
    }

    override fun likeById(id: Long) {
        daoRoom.likeById(id)
    }

    override fun shareById(id: Long) {
        daoRoom.shareById(id)
    }

    override fun save(post: Post) {
        daoRoom.save(PostEntity.fromDto(post))
    }

    override fun removeById(id: Long) {
        daoRoom.removeById(id)
    }
}