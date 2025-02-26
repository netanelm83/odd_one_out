package com.netgames.oddoneout.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.netgames.oddoneout.data.repository.StatsRepository
import com.netgames.oddoneout.data.repository.StatsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object StatsModule {

    @Provides
    fun provideStatRepository(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore) : StatsRepository = StatsRepositoryImpl(firebaseAuth, firestore)
}