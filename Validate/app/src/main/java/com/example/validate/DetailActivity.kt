package com.example.validate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.validate.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_STUDENT_ID = "extra_student_id"
        const val EXTRA_CLASS = "extra_class"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val empty = "Chưa có thông tin"
        with(binding) {
            tvName.text = intent.getStringExtra(EXTRA_NAME) ?: empty
            tvEmail.text = intent.getStringExtra(EXTRA_EMAIL) ?: empty
            tvPhone.text = intent.getStringExtra(EXTRA_PHONE) ?: empty
            tvStudentId.text = intent.getStringExtra(EXTRA_STUDENT_ID) ?: empty
            tvClass.text = intent.getStringExtra(EXTRA_CLASS) ?: empty
            btnGoRegister.setOnClickListener {
                startActivity(Intent(this@DetailActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}