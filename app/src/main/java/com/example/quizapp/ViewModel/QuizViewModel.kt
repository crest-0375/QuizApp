package com.example.quizapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.Constants.ResponseResult
import com.example.quizapp.Data.Result
import com.example.quizapp.Repository.QuizRepository
import kotlinx.coroutines.launch

class QuizViewModel(selectedCategory: Int) : ViewModel() {
    private val repository = QuizRepository()
    var quizQuestion = MutableLiveData<ResponseResult<List<Result>>>(ResponseResult.Loading(true))

    private var result = 0
    var currentPosition = 0
    var selectedId: Int? = null

    init {
        viewModelScope.launch {
            val hashMap = HashMap<String, String>()
            hashMap["amount"] = "10"
            if (selectedCategory in 9..32)
                hashMap["category"] = selectedCategory.toString()
//            hashMap["difficulty"] = "easy"
            hashMap["type"] = "multiple"
            quizQuestion.postValue(repository.getQuestionList(hashMap))
        }
    }

    fun increaseResult() {
        this.result++
    }

    fun getResult(): Int {
        return result
    }

}