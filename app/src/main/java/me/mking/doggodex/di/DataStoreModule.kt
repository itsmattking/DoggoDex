package me.mking.doggodex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.mking.doggodex.data.datastores.DogBreedsDataStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
object DataStoreModule {

    @Provides
    fun providesDogBreedsDataStore(
        okHttpClient: OkHttpClient
    ): DogBreedsDataStore {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://dog.deo/api/")
            .build()
            .create(DogBreedsDataStore::class.java)
    }

}