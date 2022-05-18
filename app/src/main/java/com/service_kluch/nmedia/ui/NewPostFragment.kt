package com.service_kluch.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.service_kluch.nmedia.databinding.FragmentNewPostBinding
import com.service_kluch.nmedia.util.AndroidUtils.hideKeyboard
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
        binding.editText.requestFocus()

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        binding.checkButton.setOnClickListener {
            if (!binding.editText.text.isNullOrBlank()){
                val content = binding.editText.text.toString()
                viewModel.changeContent(content)
                viewModel.save()
                requireView().hideKeyboard()
            }
            findNavController().navigateUp()
        }
        return binding.root
    }

}