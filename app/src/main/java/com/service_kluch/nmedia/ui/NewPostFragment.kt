package com.service_kluch.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.service_kluch.nmedia.databinding.FragmentNewPostBinding
import com.service_kluch.nmedia.util.CompanionArg.Companion.textArg
import com.service_kluch.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater)
        arguments?.textArg?.let {
            binding.editText.setText(it)
        }

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        binding.editText.requestFocus()

        binding.checkButton.setOnClickListener {
            val text = binding.editText.text.toString()

            if (text.isNotBlank()) {
                viewModel.changeContent(text)
                viewModel.save()

            }
            findNavController().navigateUp()
        }
        return binding.root
    }

}