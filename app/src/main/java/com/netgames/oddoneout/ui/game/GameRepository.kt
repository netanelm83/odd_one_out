package com.netgames.oddoneout.ui.game
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.netgames.oddoneout.data.models.Question
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface IGameRepository {
    suspend fun fetchQuestion(): Question?
    fun hasNextAnswer() : Boolean
}

class GameRepository @Inject constructor(val firestore : FirebaseFirestore) : IGameRepository {
    private val usedQuestions = mutableSetOf<String>()
    private var hasNext = false


    override suspend fun fetchQuestion(): Question? {
        return try {
            val snapshot = firestore.collection("questions").get().await()
            val documents = snapshot.documents

            val availableQuestion = documents.filterNot { it.id in usedQuestions }

            if (availableQuestion.isNotEmpty()) {
                val randomDocuments = availableQuestion.random()
                usedQuestions.add(randomDocuments.id)
                hasNext = availableQuestion.size > 1
                randomDocuments.toObject(Question::class.java)
            } else
                 null
        } catch (e : Exception) {
            Log.i("netanel",e.message.toString())
            null
        }
    }

    override fun hasNextAnswer(): Boolean = hasNext
}