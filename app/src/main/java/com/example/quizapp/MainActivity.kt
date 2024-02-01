package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_QuizApp)
        setContentView(binding.root)

        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_menu_popup_item, categories)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        var selectedCategory = 0

        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                Log.d("TAG", (position + 8).toString())
                selectedCategory = position + 8
            }


        binding.btnStart.setOnClickListener {
            if (binding.tvName.text?.isNotBlank() == true) {
                val intent = Intent(this, QuestionsActivity::class.java)
                val name = binding.tvName.text.toString()
                intent.putExtra("user_name", name)
                intent.putExtra("selectedCategory", selectedCategory)
                startActivity(intent)
            } else
                Toast.makeText(this, "Please Enter your name", Toast.LENGTH_SHORT).show()
        }
    }
}