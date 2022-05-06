package com.service_kluch.nmedia.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.service_kluch.nmedia.dto.Post
import com.service_kluch.nmedia.R
import com.service_kluch.nmedia.databinding.ItemPostBinding

class PostViewHolder(
    private val binding: ItemPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind (post: Post) {
        with(binding) {
            avatar.setImageResource(R.drawable.ic_launcher_netology)
            authorName.text = post.authorName
            data.text = post.published
            content.text = post.content

            likeImageButtonId.isChecked = post.likeByMe
            likeImageButtonId.text = post.likeCount.toFormattedString()
            shareImageButtonId.text = post.shareCount.toFormattedString()
            watchesImageViewId.text = post.watchesCount.toFormattedString()

            likeImageButtonId.setOnClickListener {
                onInteractionListener.onLikeClicked(post)
            }

            shareImageButtonId.setOnClickListener {
                onInteractionListener.onShareClicked(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemoveClicked(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEditClicked(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

        }
    }
}

private fun Long.toFormattedString() = when (this) {
    in 0..999 -> this.toString()
    in 1_000..9_999 -> this.roundToThousandsWithOneDecimal().toString() + "K"
    in 10_000..999_999 -> (this / 1_000).toString() + "K"
    in 1_000_000..9_999_999 -> (this / 1_000).roundToThousandsWithOneDecimal().toString() + "M"
    in 10_000_000..999_999_999 -> (this / 1_000_000).toString() + "M"
    in 1_000_000_000..Int.MAX_VALUE -> (this / 1_000_000).roundToThousandsWithOneDecimal()
        .toString() + "B"
    else -> "0"
}

private fun Long.roundToThousandsWithOneDecimal(): Double =
    (this / 100).toDouble() / 10
