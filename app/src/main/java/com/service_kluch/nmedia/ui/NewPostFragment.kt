package com.service_kluch.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.service_kluch.nmedia.R
import com.service_kluch.nmedia.databinding.FragmentNewPostBinding
import com.service_kluch.nmedia.util.AndroidUtils.hideKeyboard
import com.service_kluch.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    companion object {
        private const val TEXT_KEY = "TEXT_KEY"
        var Bundle.textArg: String?
            set(value) = putString(TEXT_KEY, value)
            get() = getString(TEXT_KEY)
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )
        arguments?.textArg
            ?.let(binding.editText::setText)


        binding.checkButton.setOnClickListener{
            if (binding.editText.text.isBlank()) {
                Toast.makeText(activity, R.string.error_empty_content, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                viewModel.changeContent(binding.editText.text.toString())
                viewModel.save()
                requireView().hideKeyboard()
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}