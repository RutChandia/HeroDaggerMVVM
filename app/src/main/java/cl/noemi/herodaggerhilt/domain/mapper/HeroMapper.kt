package cl.noemi.herodaggerhilt.domain.mapper

import cl.noemi.herodaggerhilt.data.dto.HeroDTO
import cl.noemi.herodaggerhilt.domain.model.Powerstats
import cl.noemi.herodaggerhilt.domain.model.SuperHero

class HeroMapper {
    fun dtoToSuperHero(listDTO: List<HeroDTO>): List<SuperHero> {
        return listDTO.map { heroDTO ->
            SuperHero(
                id = heroDTO.id ?: 1,
                heroName = heroDTO.name ?: "",
                fullName = heroDTO.biography?.fullName ?: "",
                aliases = heroDTO.biography?.aliases ?: emptyList(),
                alterEgos = heroDTO.biography?.alterEgos ?: "",
                groupAffiliation = heroDTO.connections?.groupAffiliation ?: "",
                alignment = heroDTO.biography?.alignment ?: "",
                imageSmall = heroDTO.images?.sm ?: "",
                imageMedium = heroDTO.images?.md ?: "",
                powerStats = Powerstats(
                    combat = heroDTO.powerstats?.combat ?: 0,
                    durability = heroDTO.powerstats?.durability ?: 0,
                    intelligence = heroDTO.powerstats?.intelligence ?: 0,
                    power = heroDTO.powerstats?.power ?: 0,
                    speed = heroDTO.powerstats?.speed ?: 0,
                    strength = heroDTO.powerstats?.strength ?: 0
                ),
                occupation = heroDTO.work?.occupation ?: ""
            )
        }
    }
}