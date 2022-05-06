package com.service_kluch.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.service_kluch.nmedia.dto.Post
import com.service_kluch.nmedia.repository.PostRepositoryInMemory

private val empty = Post(
    id = 0,
    authorName = "",
    content = "",
    published = "",
    likeByMe = false,
    likeCount = 0,
    watchesCount = 0
)

class PostViewModel : ViewModel() {
    private val repository = PostRepositoryInMemory()
    val editedPost = MutableLiveData(empty)

    val liveData: LiveData<List<Post>>
        get() = repository.listData

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

    fun cancelEditing() {
        editedPost.value?.let {
            repository.cancelEditing(it)
        }
        editedPost.value = empty
    }
}
