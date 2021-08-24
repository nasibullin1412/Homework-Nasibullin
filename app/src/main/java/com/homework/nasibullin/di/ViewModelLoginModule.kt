package com.homework.nasibullin.di

import com.homework.nasibullin.repo.LoginRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelLoginModule {
    @Provides
    @ViewModelScoped
    fun provideRepo() =
        LoginRepo()
}