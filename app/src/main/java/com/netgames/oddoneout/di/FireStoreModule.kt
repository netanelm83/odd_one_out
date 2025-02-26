package com.netgames.oddoneout.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.remote.FirestoreChannel
import com.netgames.oddoneout.ui.game.GameRepository
import com.netgames.oddoneout.ui.game.IGameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class FireStoreModule {

    @Provides
    fun provideFireStore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideGameRepository(firestore: FirebaseFirestore) : IGameRepository = GameRepository(firestore)
}