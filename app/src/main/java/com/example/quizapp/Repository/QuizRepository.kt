package com.example.quizapp.Repository

import android.util.Log
import com.example.quizapp.Api.RetrofitInstance
import com.example.quizapp.Constants.ResponseResult
import com.example.quizapp.Data.Result

class QuizRepository() {
    private var apiService = RetrofitInstance.apiService

    suspend fun getQuestionList(hashMap: Map<String, String>): ResponseResult<List<Result>> {
        return try {
            val response = apiService.getQuestionListFromApi(hashMap)
            Log.d("TAG", response.toString())
            if (response.isSuccessful && response.body()!!.response_code == 0) {
                (ResponseResult.Success(response.body()!!.results))
            } else {
                (ResponseResult.Failure("Response is null"))
            }
        } catch (e: Exception) {
            Log.d("TAG", e.toString())
            (ResponseResult.Failure(e.message.toString()))
        }
    }
}