package com.service_kluch.nmedia.repository

import androidx.lifecycle.MutableLiveData
import com.service_kluch.nmedia.dto.Post

class PostRepositoryInMemory: PostRepository {
    private var nextId = 1L

    override val listData = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1L,
                authorName = "Нетология. Университет интернет-профессий будущего",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                published = "21 мая в 18:3$index",
                likeByMe = false,
                likeCount = 999,
                watchesCount = 1_999
            )
        }
    )

    override fun likeById(id: Long) {
        listData.value = listData.value?.map {
            if (it.id == id) it.copy(
                likeByMe = !it.likeByMe,
                likeCount = if (it.likeByMe) it.likeCount.dec() else it.likeCount.inc()
            )
            else it
        }
    }

    override fun shareById(id: Long) {
        listData.value = listData.value?.map {
            if (it.id == id) it.copy(shareCount = it.shareCount.inc()) else it
        }
    }

    override fun removeById(id: Long) {
        listData.value = listData.value?.filter { it.id != id }
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            listData.value = listOf(post.copy(id = nextId++, authorName = "Me", published = "Now")) +
                    listData.value.orEmpty()
            return
        }
        listData.value = listData.value?.map {
            if (it.id == post.id) it.copy(
                content = post.content
            )
            else it
        }
    }

    override fun cancelEditing(post: Post) {
        listData.value = listData.value?.map {
            it.copy(content = it.content)
        }
    }
}
