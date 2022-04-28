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
            authorName.setText(post.authorName)
            data.setText(post.published)
            content.setText(post.content)


            likeImageButtonId.setImageResource(if (post.likeByMe) R.drawable.icon_like_liked else R.drawable.icon_like_not_liked)
            likeCounterTextViewId.setText(post.likeCount.toFormattedString())
            likeImageButtonId.setOnClickListener {
                onPostLiked(post)
            }


            shareCounterTextViewId.setText(post.shareCount.toFormattedString())
            shareImageButtonId.setOnClickListener {
                onPostShared(post)
            }

            watchesCounterTextViewId.setText(post.watchesCount.toFormattedString())

        }
    }
}

private fun Int.toFormattedString() = when (this) {
    in 0..999 -> this.toString()
    in 1_000..9_999 -> this.roundToThousandsWithOneDecimal().toString() + "K"
    in 10_000..999_999 -> (this / 1_000).toString() + "K"
    in 1_000_000..9_999_999 -> (this / 1_000).roundToThousandsWithOneDecimal().toString() + "M"
    in 10_000_000..999_999_999 -> (this / 1_000_000).toString() + "M"
    in 1_000_000_000..Int.MAX_VALUE -> (this / 1_000_000).roundToThousandsWithOneDecimal()
        .toString() + "B"
    else -> "0"
}

private fun Int.roundToThousandsWithOneDecimal(): Double =
    (this / 100).toDouble() / 10