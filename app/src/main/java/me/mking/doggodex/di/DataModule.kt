package me.mking.doggodex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import me.mking.doggodex.data.repositories.DogBreedRepositoryImpl
import me.mking.doggodex.domain.repositories.DogBreedRepository

@Module
@InstallIn(ActivityComponent::class)
abstract class DataModule {

    @Binds
    abstract fun providesDogBreedRepository(
        dogBreedRepository: DogBreedRepositoryImpl
    ) : DogBreedRepository
}