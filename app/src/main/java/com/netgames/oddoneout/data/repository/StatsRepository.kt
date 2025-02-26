package com.netgames.oddoneout.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.netgames.oddoneout.data.models.UserStats
import com.netgames.oddoneout.ui.auth.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface StatsRepository {
    suspend fun getUserStats() : UserStats?
    suspend fun updateUserStats(isCorrect: Boolean)
}

class StatsRepositoryImpl @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore) : StatsRepository {

    private val usersCollection = firestore.collection("users")

    override suspend fun getUserStats(): UserStats? {
        val userId = firebaseAuth.currentUser?.uid ?: return null
        return try {
           val snapshot = usersCollection.document(userId).get().await()
            snapshot.toObject(UserStats::class.java) ?: UserStats(0, 0)
        } catch (ex : Exception) {
            Log.i("netanel","$ex")
            null
        }
    }

    override suspend fun updateUserStats(isCorrect : Boolean) {
        val userId = firebaseAuth.currentUser?.uid ?: return
        val userStats = getUserStats() ?: UserStats(0, 0)

        userStats.gamesPlayed += 1
        if (isCorrect) userStats.correctAnswers += 1

        usersCollection.document(userId).set(userStats)
    }
}