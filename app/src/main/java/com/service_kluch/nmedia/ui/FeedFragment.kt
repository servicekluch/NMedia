package com.service_kluch.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.service_kluch.nmedia.R
import com.service_kluch.nmedia.adapter.OnInteractionListener
import com.service_kluch.nmedia.adapter.PostAdapter
import com.service_kluch.nmedia.databinding.FragmentFeedBinding
import com.service_kluch.nmedia.dto.Post
import com.service_kluch.nmedia.util.CompanionArg.Companion.longArg
import com.service_kluch.nmedia.util.CompanionArg.Companion.textArg
import com.service_kluch.nmedia.viewmodel.PostViewModel


class FeedFragment : Fragment() {
    val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )
        val adapter = PostAdapter(
            object : OnInteractionListener {
                override fun onEditClicked(post: Post) {
                    findNavController().navigate(
                        R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply {
                            textArg = post.content
                        }
                    )
                    viewModel.edit(post)
                }

                override fun onRemoveClicked(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLikeClicked(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClicked(post: Post) {

                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(intent, getString(R.string.chooser_share_post)))
                    viewModel.shareById(post.id)
                }

                override fun onVideoClicked(post: Post) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(post.video)))
                }
                override fun onPostClicked(post: Post) {
                    findNavController().navigate(R.id.action_feedFragment_to_postFragment,
                        Bundle().apply {
                            longArg = post.id
                        })
                }
            }
        )

        binding.list.adapter = adapter

        viewModel.liveData.observe(viewLifecycleOwner) { posts ->
            val newPost = adapter.itemCount < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        binding.fab.setOnClickListener {

            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}