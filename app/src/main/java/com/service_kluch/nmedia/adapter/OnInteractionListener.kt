package com.service_kluch.nmedia.adapter

import com.service_kluch.nmedia.dto.Post

interface OnInteractionListener {
    fun onEditClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
}