package com.example.quizapp.Data

data class QuestionResponse(
    val response_code: Int,
    val results: List<Result>
)