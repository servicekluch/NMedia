package com.service_kluch.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.service_kluch.nmedia.db.AppDb
import com.service_kluch.nmedia.dto.Post
import com.service_kluch.nmedia.repository.PostRepository
import com.service_kluch.nmedia.repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    authorName = "",
    content = "",
    published = "",
    likeByMe = false,
    likeCount = 0,
    watchesCount = 0
)


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    val liveData = repository.getAll()

    private val editedPost = MutableLiveData(empty)


    fun likeById(id: Long) {
        repository.likeById(id)
    }
    fun shareById(id: Long) {
        repository.shareById(id)
    }
    fun removeById(id: Long) {
        repository.removeById(id)
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (text == editedPost.value?.content) {
            return
        }
        editedPost.value = editedPost.value?.copy(content = text)
    }

    fun save() {
        editedPost.value?.let {
            repository.save(it)
        }
        editedPost.value = empty
    }

    fun edit (post: Post) {
        editedPost.value = post
    }

    fun getUri(post: Post): Boolean {
        return repository.isVideo(post)
    }
}
