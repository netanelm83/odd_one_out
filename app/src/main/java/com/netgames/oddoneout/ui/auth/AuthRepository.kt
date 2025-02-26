package com.netgames.oddoneout.ui.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    suspend fun signInWithGoogle(idToken : String) : FirebaseUser?
    fun getUser() : FirebaseUser?
    fun signOut()
}

class AuthRepositoryImpl (val firebaseAuth : FirebaseAuth) : AuthRepository {
    override suspend fun signInWithGoogle(idToken: String): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken , null)
        return try {
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            authResult.user
        } catch (ex : Exception) {
            null
        }
    }

    override fun getUser(): FirebaseUser? = firebaseAuth.currentUser

    override fun signOut() {
        firebaseAuth.signOut()
    }

}