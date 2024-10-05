package cl.noemi.herodaggerhilt.di

import cl.noemi.herodaggerhilt.data.network.ApiServiceHero
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiServiceHero {
        return retrofit.create(ApiServiceHero::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://akabab.github.io/superhero-api/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}