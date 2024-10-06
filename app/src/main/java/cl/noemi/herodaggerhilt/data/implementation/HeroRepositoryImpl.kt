package cl.noemi.herodaggerhilt.data.implementation

import cl.noemi.herodaggerhilt.data.network.ApiResponseState
import cl.noemi.herodaggerhilt.data.network.ApiServiceHero
import cl.noemi.herodaggerhilt.data.network.doNetworkCall
import cl.noemi.herodaggerhilt.domain.mapper.HeroMapper
import cl.noemi.herodaggerhilt.domain.model.SuperHero
import cl.noemi.herodaggerhilt.domain.repository.HeroRepository
import javax.inject.Inject

class HeroRepositoryImpl @Inject constructor(
    private val apiServiceHero: ApiServiceHero
): HeroRepository {
    override suspend fun getSuperHero(): ApiResponseState<List<SuperHero>> {
        return doNetworkCall {
            val response = apiServiceHero.getHeroes()
            val mapper = HeroMapper()
            mapper.dtoToSuperHero(response)
        }
    }
}