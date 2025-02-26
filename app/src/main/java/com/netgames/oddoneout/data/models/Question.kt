package com.netgames.oddoneout.data.models

import com.google.firebase.firestore.PropertyName

data class Question(
    @get:PropertyName("difficulty") @set:PropertyName("difficulty")
    var difficulty: String = "",

    @get:PropertyName("correctAnswer") @set:PropertyName("correctAnswer")
    var correctAnswer: String = "",

    @get:PropertyName("category") @set:PropertyName("category")
    var category: String = "",

    @get:PropertyName("images") @set:PropertyName("images")
    var images: List<String> = emptyList()
)
