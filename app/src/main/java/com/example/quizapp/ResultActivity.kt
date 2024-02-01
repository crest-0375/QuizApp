package com.example.quizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_QuizApp)
        setContentView(binding.root)


        val name = intent.getStringExtra("name")
        val result = intent.getIntExtra("result", 0)

        binding.tvResultScore.text = result.toString()
        if (result > 6) {
            binding.tvResultName.text = "Wohoo! $name"
            binding.tvResultScore.setBackgroundResource(R.drawable.correct_option_background)
        } else if (result > 3) {
            binding.tvResultName.text = "Hmm! $name"
        } else {
            binding.tvResultName.text = "Opps! $name"
            binding.tvResultScore.setBackgroundResource(R.drawable.false_option_background)
        }

        binding.btnStartOver.setOnClickListener {
            finish()
        }
    }
}