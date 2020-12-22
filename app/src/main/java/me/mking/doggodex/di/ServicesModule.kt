package me.mking.doggodex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.mking.doggodex.data.services.DogBreedsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object ServicesModule {

    @Provides
    fun providesDogBreedsDataStore(
        okHttpClient: OkHttpClient
    ): DogBreedsService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://dog.ceo/api/")
            .build()
            .create(DogBreedsService::class.java)
    }

}