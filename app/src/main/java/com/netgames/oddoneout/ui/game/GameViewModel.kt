package com.netgames.oddoneout.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netgames.oddoneout.data.models.Question
import com.netgames.oddoneout.data.models.UserStats
import com.netgames.oddoneout.data.repository.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    val repository: IGameRepository,
    val statsRepository: StatsRepository) : ViewModel() {

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion : LiveData<Question> = _currentQuestion

    private val _feedback = MutableLiveData<String?>()
    val feedBack : LiveData<String?> = _feedback

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> = _isLoading

    private val _userStats = MutableLiveData<UserStats?>()
    val userStats = _userStats

    init {
        loadNewQuestion()
        loadUserStats()
    }

    private fun loadUserStats() {
        viewModelScope.launch {
            val stats = statsRepository.getUserStats()
            _userStats.postValue(stats)        }
    }

    private fun loadNewQuestion() {
        viewModelScope.launch {
            _isLoading.value = true
            _currentQuestion.value = repository.fetchQuestion()
            _isLoading.value = false
        }
    }

    fun checkAnswer(selectedImage: String) {
        val isCorrect = selectedImage == _currentQuestion.value?.correctAnswer
        if (isCorrect) {
            _feedback.value = "✅ Correct!"
        } else {
            _feedback.value = "❌ Try Again!"
        }
        viewModelScope.launch {
            statsRepository.updateUserStats(isCorrect)
            loadUserStats()
        }
    }

    fun hasNextLevel() {

    }
}