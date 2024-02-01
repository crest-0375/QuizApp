package com.example.quizapp.Api

import com.example.quizapp.Data.QuestionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("api.php")
    suspend fun getQuestionListFromApi(
        @QueryMap hashMap: Map<String,String>,
    ): Response<QuestionResponse>

}