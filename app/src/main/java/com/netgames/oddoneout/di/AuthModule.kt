package com.netgames.oddoneout.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.netgames.oddoneout.ui.auth.AuthRepository
import com.netgames.oddoneout.ui.auth.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideAuthRepository(auth : FirebaseAuth) : AuthRepository = AuthRepositoryImpl(auth)
}