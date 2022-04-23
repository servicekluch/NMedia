package com.service_kluch.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.service_kluch.nmedia.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            authorName = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likeByMe = false,
            likeCount = 999,
            watchesCount = 1_000
        )

        with(binding){
            authorName.text = post.authorName
            data.text = post.published
            content.text = post.content

            like_image_button_id.setOnClickListener {
                post.likeByMe = !post.likeByMe
                like_image_button_id.setImageResource(
                    if (post.likeByMe) R.drawable.icon_like_liked else R.drawable.icon_like_not_liked
                )
                if (post.likeByMe) post.likeCount++ else post.likeCount--
                like_counter_text_view_id.text = post.likeCount.toFormattedString()
            }
            share_image_button_id.setOnClickListener{
                post.shareCount++
                share_counter_text_view_id.text = post.shareCount.toFormattedString()
            }
            watches_counter_text_view_id.text = post.watchesCount.toFormattedString()
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

    private fun Int.roundToThousandsWithOneDecimal(): Long =
        (this / 100).toLong() / 10


}