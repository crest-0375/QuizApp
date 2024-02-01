package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.Constants.QuestionList
import com.example.quizapp.Constants.ResponseResult
import com.example.quizapp.ViewModel.QuizViewModel
import com.example.quizapp.ViewModel.QuizViewModelFactory
import com.example.quizapp.databinding.ActivityQuestionsBinding
import org.jsoup.Jsoup

class QuestionsActivity : AppCompatActivity() {
    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: ActivityQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_QuizApp)
        setContentView(binding.root)

        val selectedCategory = intent.getIntExtra("selectedCategory", 0)
        val quizViewModelFactory = QuizViewModelFactory(selectedCategory)
        viewModel = ViewModelProvider(this, quizViewModelFactory)[QuizViewModel::class.java]


        setViews()

        binding.btnSubmit.setOnClickListener {
            if (binding.btnSubmit.text == "SUBMIT") {
                binding.option1.isEnabled = false
                binding.option2.isEnabled = false
                binding.option3.isEnabled = false
                binding.correctOption.isEnabled = false
                if (viewModel.selectedId != null) {
                    val selectedView = binding.root.findViewById<TextView>(viewModel.selectedId!!)

                    if (selectedView == binding.correctOption) {
                        viewModel.increaseResult()
                        binding.correctOption.setBackgroundResource(R.drawable.correct_option_background)
                    } else {
                        binding.correctOption.setBackgroundResource(R.drawable.correct_option_background)
                        selectedView.setBackgroundResource(R.drawable.false_option_background)
                    }
                } else
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                if (viewModel.currentPosition == 9)
                    binding.btnSubmit.text = "FINISH"
                else
                    binding.btnSubmit.text = "NEXT"
            } else if (binding.btnSubmit.text == "NEXT") {
                if (viewModel.selectedId != null) {
                    val selectedView = binding.root.findViewById<TextView>(viewModel.selectedId!!)
                    selectedView.setBackgroundResource(R.drawable.options_background)
                }
                viewModel.currentPosition++
                viewModel.selectedId = null
                binding.option1.isEnabled = true
                binding.option2.isEnabled = true
                binding.option3.isEnabled = true
                binding.correctOption.isEnabled = true
                binding.btnSubmit.isEnabled = false
                binding.correctOption.setBackgroundResource(R.drawable.options_background)
                binding.btnSubmit.text = "SUBMIT"
                setViews()
            } else if (binding.btnSubmit.text == "FINISH") {
                val name = intent.getStringExtra("user_name")
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("result", viewModel.getResult())
                startActivity(intent)
                finish()
            }
        }

        binding.correctOption.setOnClickListener { selectOptions(it) }
        binding.option1.setOnClickListener { selectOptions(it) }
        binding.option2.setOnClickListener { selectOptions(it) }
        binding.option3.setOnClickListener { selectOptions(it) }
    }

    private fun selectOptions(view: View) {
        if (viewModel.selectedId != null) {
            val selectedView = binding.root.findViewById<TextView>(viewModel.selectedId!!)
            if (selectedView == view) {
                viewModel.selectedId = null
                binding.btnSubmit.isEnabled = false
                view.setBackgroundResource(R.drawable.options_background)
            } else {
                selectedView?.setBackgroundResource(R.drawable.options_background)
                viewModel.selectedId = view.id
                binding.btnSubmit.isEnabled = true
                view.setBackgroundResource(R.drawable.selected_background)
            }
        } else {
            viewModel.selectedId = view.id
            binding.btnSubmit.isEnabled = true
            view.setBackgroundResource(R.drawable.selected_background)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        viewModel.quizQuestion.observe(this, Observer {

            when (it) {
                is ResponseResult.Failure -> {
                    Log.d("TAG", "Static Questions")
                    binding.wholeScrollView.visibility = View.VISIBLE
                    binding.btnSubmit.visibility = View.VISIBLE
                    binding.progressBarLoading.visibility = View.GONE
                    val questionListValue = QuestionList.getQuestions()
                    val currentQuestion = questionListValue[viewModel.currentPosition]
                    binding.question.text = decodeHtmlEntity(currentQuestion.question)
                    binding.progressHorizontal.progress = viewModel.currentPosition + 1
                    binding.ProgressCount.text = "${viewModel.currentPosition + 1}/10"
                    shuffleOptions()
                    binding.correctOption.text =
                        decodeHtmlEntity(currentQuestion.correctAns)
                    binding.option1.text =
                        decodeHtmlEntity(currentQuestion.incAns1)
                    binding.option2.text =
                        decodeHtmlEntity(currentQuestion.incAns2)
                    binding.option3.text =
                        decodeHtmlEntity(currentQuestion.incAns3)

                }

                is ResponseResult.Loading -> {
                    binding.wholeScrollView.visibility = View.GONE
                    binding.btnSubmit.visibility = View.GONE
                    binding.progressBarLoading.visibility = View.VISIBLE
                }

                is ResponseResult.Success -> {
                    if (it.data.isNotEmpty()) {
                        binding.wholeScrollView.visibility = View.VISIBLE
                        binding.btnSubmit.visibility = View.VISIBLE
                        binding.progressBarLoading.visibility = View.GONE
                        val questionListValue = it.data
                        val currentQuestion = questionListValue[viewModel.currentPosition]
                        binding.question.text = decodeHtmlEntity(currentQuestion.question)
                        binding.progressHorizontal.progress = viewModel.currentPosition + 1
                        binding.ProgressCount.text = "${viewModel.currentPosition + 1}/10"
                        shuffleOptions()
                        binding.correctOption.text =
                            decodeHtmlEntity(currentQuestion.correct_answer)
                        binding.option1.text =
                            decodeHtmlEntity(currentQuestion.incorrect_answers[0])
                        binding.option2.text =
                            decodeHtmlEntity(currentQuestion.incorrect_answers[1])
                        binding.option3.text =
                            decodeHtmlEntity(currentQuestion.incorrect_answers[2])
                    }
                }
            }
        })
    }

    private fun decodeHtmlEntity(htmlString: String): String {
        val doc = Jsoup.parse(htmlString)
        return doc.text()
    }

    private fun shuffleOptions() {
        val textViews: MutableList<TextView> = mutableListOf(
            binding.correctOption,
            binding.option1,
            binding.option2,
            binding.option3
        )

        textViews.shuffle()

        binding.optionsContainer.removeAllViews()

        for (textView in textViews) {
            binding.optionsContainer.addView(textView)
        }
    }
}