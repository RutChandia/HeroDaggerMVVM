package cl.noemi.herodaggerhilt.domain.model

data class SuperHero(
    val id: Int,
    val heroName: String,
    val fullName: String,
    val aliases: List<String>,
    val alterEgos: String,
    val groupAffiliation: String,
    val alignment: String,
    val imageSmall: String,
    val imageMedium: String,
    val powerStats: Powerstats,
    val occupation: String
)

data class Powerstats(
    val combat: Int,
    val durability: Int,
    val intelligence: Int,
    val power: Int,
    val speed: Int,
    val strength: Int
)