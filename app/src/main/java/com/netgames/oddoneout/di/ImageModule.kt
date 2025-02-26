package com.netgames.oddoneout.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // ✅ Make ImageLoader available globally
object ImageLoaderModule {

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25) // ✅ Use 25% of available memory
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache")) // ✅ Set disk cache location
                    .maxSizeBytes(50L * 1024 * 1024) // ✅ 50MB cache limit
                    .build()
            }
            .crossfade(true)
            .build()
    }
}

// ✅ Define an EntryPoint to manually retrieve ImageLoader
@EntryPoint
@InstallIn(SingletonComponent::class)
interface ImageLoaderEntryPoint {
    fun getImageLoader(): ImageLoader
}

// ✅ Function to retrieve ImageLoader from Hilt
fun provideImageLoader(context: Context): ImageLoader {
    val entryPoint = EntryPoints.get(context.applicationContext, ImageLoaderEntryPoint::class.java)
    return entryPoint.getImageLoader()
}
