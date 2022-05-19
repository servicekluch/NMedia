package com.service_kluch.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.service_kluch.nmedia.R
import com.service_kluch.nmedia.databinding.FragmentPostBinding
import com.service_kluch.nmedia.util.CompanionArg.Companion.longArg
import com.service_kluch.nmedia.viewmodel.PostViewModel
import com.service_kluch.nmedia.util.CompanionArg.Companion.textArg

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        with(binding.postContent) {
            viewModel.liveData.observe(viewLifecycleOwner) { posts ->
                val post = posts.find { it.id == arguments?.longArg }
                if (post != null) {

                        authorName.text = post.authorName
                        data.text = post.published
                        content.text = post.content
                        watchesImageViewId.text = post.viewedCount.toString()
                        likeImageButtonId.text = post.likedCount.toString()
                        shareImageButtonId.text = post.sharedCount.toString()
                        likeImageButtonId.isChecked = post.likedByMe

                        if (post.videoUrl == "") {
                            binding.postContent.videoGroup.visibility = View.GONE
                        }

                        menu.setOnClickListener {
                            PopupMenu(it.context, it).apply {
                                inflate(R.menu.options_post)
                                setOnMenuItemClickListener { item ->
                                    when (item.itemId) {
                                        R.id.edit -> {
                                            viewModel.edit(post)
                                            findNavController().navigate(
                                                R.id.action_postFragment_to_newPostFragment,
                                                Bundle().apply {
                                                    textArg = post.content
                                                }
                                            )
                                            true
                                        }
                                        R.id.remove -> {
                                            findNavController().navigate(
                                                R.id.action_postFragment_to_feedFragment
                                            )
                                            viewModel.removeById(post.id)
                                            true
                                        }
                                        else -> false
                                    }
                                }
                            }.show()
                        }
                        likeImageButtonId.setOnClickListener {
                            viewModel.likeById(post.id)
                        }

                        shareImageButtonId.setOnClickListener {
                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, post.content)
                                type = "text/plain"
                            }
                            startActivity(Intent.createChooser(intent, getString(R.string.chooser_share_post)))
                            viewModel.shareById(post.id)
                        }

                        video.setOnClickListener {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl)))
                        }
                    }
                }
            }
    return binding.root
    }
}