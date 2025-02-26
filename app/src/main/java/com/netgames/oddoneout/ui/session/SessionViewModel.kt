package com.netgames.oddoneout.ui.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.netgames.oddoneout.ui.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        _user.value = authRepository.getUser()
    }

    fun signOut() {
        authRepository.signOut()
        _user.value = null
    }

}