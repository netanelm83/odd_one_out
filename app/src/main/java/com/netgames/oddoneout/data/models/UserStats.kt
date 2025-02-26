package com.netgames.oddoneout.data.models

data class UserStats(
    var gamesPlayed : Int,
    var correctAnswers : Int, ) {
    val accuracy : Double
        get() = if (gamesPlayed > 0) (correctAnswers.toDouble() / gamesPlayed) * 100 else 0.0

    constructor() : this(0, 0)

}