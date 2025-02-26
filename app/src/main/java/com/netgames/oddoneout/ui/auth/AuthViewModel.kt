package com.netgames.oddoneout.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _user = MutableLiveData<FirebaseUser?>()
    private val _isLoading = MutableLiveData<Boolean>(false)
    val user: LiveData<FirebaseUser?> = _user
    val isLoading : LiveData<Boolean> = _isLoading

    fun checkUser() {
        _user.value = authRepository.getUser()
    }

    fun signInWithGoogle(idToken : String) {
        _isLoading.value = true
        viewModelScope.launch {
            val user = authRepository.signInWithGoogle(idToken)
            _user.value = user
            _isLoading.value = false
        }
    }

    fun signOut() {
        authRepository.signOut()
        _user.value = null
    }
}