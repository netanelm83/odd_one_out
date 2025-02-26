package com.netgames.oddoneout

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.netgames.oddoneout.navigation.Screen
import com.netgames.oddoneout.ui.auth.AuthScreen
import com.netgames.oddoneout.ui.auth.AuthViewModel
import com.netgames.oddoneout.ui.game.GameScreen
import com.netgames.oddoneout.ui.session.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val sessionViewModel : SessionViewModel = hiltViewModel()

            val user by sessionViewModel.user.observeAsState()

            MyApp(navController, user)
        }
    }

}

@Composable
fun MyApp(navController: NavHostController, user: FirebaseUser?) {
    NavHost(
        navController = navController,
        startDestination = if (user == null) Screen.Auth.route else Screen.Game.route
    ) {
        composable(Screen.Auth.route) {
            AuthScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.Game.route) {
            GameScreen(navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the Game!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}
