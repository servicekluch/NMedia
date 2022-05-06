package com.service_kluch.nmedia.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.service_kluch.nmedia.PostViewModel
import com.service_kluch.nmedia.R
import com.service_kluch.nmedia.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        with(binding) {

            like_image_button_id.setOnClickListener {
                viewModel.like()
            }
            share_image_button_id.setOnClickListener {
                viewModel.share()
            }
            viewModel.data.observe(this@MainActivity) { post ->
                authorName.text = post.authorName
                data.text = post.published
                content.text = post.content
                like_image_button_id.setImageResource(
                    if (post.likeByMe) R.drawable.icon_like_liked else R.drawable.icon_like_not_liked
                )
                like_counter_text_view_id.text = post.likeCount.toFormattedString()
                share_counter_text_view_id.text = post.shareCount.toFormattedString()
                watches_counter_text_view_id.text = post.watchesCount.toFormattedString()
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


}
