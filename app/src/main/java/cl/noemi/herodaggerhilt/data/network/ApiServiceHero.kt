package cl.noemi.herodaggerhilt.data.network

import cl.noemi.herodaggerhilt.data.dto.HeroDTO
import retrofit2.http.GET

interface ApiServiceHero {
    @GET("all.json")
    suspend fun getHeroes(): List<HeroDTO>
}