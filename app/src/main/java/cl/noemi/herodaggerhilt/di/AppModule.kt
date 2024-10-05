package cl.noemi.herodaggerhilt.di

import cl.noemi.herodaggerhilt.data.implementation.HeroRepositoryImpl
import cl.noemi.herodaggerhilt.domain.repository.HeroRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindHeroRepository(repository: HeroRepositoryImpl): HeroRepository
}