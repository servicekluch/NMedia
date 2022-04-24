package com.service_kluch.nmedia

import androidx.lifecycle.LiveData

interface PostRepository {
    val data: LiveData<Post>
    fun like()
    fun share()
    fun watches()
}