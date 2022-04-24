package com.service_kluch.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {
    private val repository = PostRepositoryInMemory()
    val data: LiveData<Post>

    get() = repository.data

    fun like() {
        repository.like()
    }
    fun share() {
        repository.share()
    }
    fun watches() {
        repository.watches()
    }
}