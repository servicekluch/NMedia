package com.service_kluch.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.service_kluch.nmedia.R
import com.service_kluch.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editText.requestFocus()
        intent?.let {
            if (it.action != "post.content")
                return@let
            val text = it.getStringExtra("post.text")
            binding.editText.setText(text.toString())
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root,
                    R.string.error_empty_content,
                    BaseTransientBottomBar.LENGTH_INDEFINITE
                )
                    .setAction(android.R.string.ok) {
                        finish()
                    }
                    .show()
                return@let
            }

        }
        binding.checkButton.setOnClickListener{
            val intent = Intent()
            if (binding.editText.text.isBlank()) {
                Toast.makeText(this, getString(R.string.empty_post), Toast.LENGTH_SHORT)
                    .show()
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.editText.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}