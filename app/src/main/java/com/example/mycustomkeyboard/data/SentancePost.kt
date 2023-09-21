package com.example.mycustomkeyboard.data

data class SentancePost(
    val inputs: String,
    val parameters: Parameters = Parameters(128)
)