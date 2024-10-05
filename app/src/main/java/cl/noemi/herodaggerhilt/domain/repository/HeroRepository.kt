package cl.noemi.herodaggerhilt.domain.repository

import cl.noemi.herodaggerhilt.data.network.ApiResponseState
import cl.noemi.herodaggerhilt.domain.model.SuperHero

interface HeroRepository {
    suspend fun getSuperHero() : ApiResponseState<List<SuperHero>>
}