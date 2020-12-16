package me.mking.doggodex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.mking.doggodex.data.datastores.DogBreedsDataStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object DataStoreModule {

    @Provides
    fun providesDogBreedsDataStore(
        okHttpClient: OkHttpClient
    ): DogBreedsDataStore {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://dog.ceo/api/")
            .build()
            .create(DogBreedsDataStore::class.java)
    }

}