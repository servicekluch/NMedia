package com.service_kluch.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView
import com.service_kluch.nmedia.dto.Post
import com.service_kluch.nmedia.R
import com.service_kluch.nmedia.databinding.ItemPostBinding

class PostViewHolder (
    private val binding: ItemPostBinding,
    private val onPostLiked: (Post) -> Unit,
    private val onPostShared: (Post) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind (post: Post) {
        with(binding) {
            avatar.setImageResource(R.drawable.ic_launcher_foreground)
            authorName.text = post.authorName
            data.text = post.published
            content.text = post.content


            likeImageButtonId.setImageResource(if (post.likeByMe) R.drawable.icon_like_liked else R.drawable.icon_like_not_liked)
            likeCounterTextViewId.text = post.likeCount.toFormattedString()
            likeImageButtonId.setOnClickListener {
                onPostLiked(post)
            }


            shareCounterTextViewId.text = post.shareCount.toFormattedString()
            shareImageButtonId.setOnClickListener {
                onPostShared(post)
            }

            watchesCounterTextViewId.text = post.watchesCount.toFormattedString()

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