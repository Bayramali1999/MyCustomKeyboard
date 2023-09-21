package com.example.mycustomkeyboard

import com.example.mycustomkeyboard.data.ManipulateSentence
import com.example.mycustomkeyboard.data.SentancePost
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

interface Api {
    @POST("generate")
    fun getRightSentence(sentence: SentancePost): ManipulateSentence
}

class ApiInstance {

    companion object {
        const val BASE_URL = "http://213.230.107.46:10003/"
        private val retrofit =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        val api: Api by lazy {
            retrofit.create(Api::class.java)
        }
    }
}