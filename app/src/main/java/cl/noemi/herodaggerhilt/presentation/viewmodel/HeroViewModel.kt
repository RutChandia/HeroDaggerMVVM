package cl.noemi.herodaggerhilt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.noemi.herodaggerhilt.data.network.ApiResponseState
import cl.noemi.herodaggerhilt.domain.model.SuperHero
import cl.noemi.herodaggerhilt.domain.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HeroViewModel @Inject constructor(
    private val repository: HeroRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _superHeroesMap = MutableStateFlow<Map<Int, SuperHero>>(emptyMap())
    val superHeroesMap: StateFlow<Map<Int, SuperHero>> = _superHeroesMap

    private val _selectedHero = MutableStateFlow<SuperHero?>(null)
    val selectedHero: StateFlow<SuperHero?> = _selectedHero

    fun loadSuperHeroes() {
        if (!_isLoading.value) {
            getSuperHeroes()
        }
    }

    private fun getSuperHeroes() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSuperHero()
            withContext(Dispatchers.Main) {
                if (response is ApiResponseState.Success) {
                    updateSuperHeroMap(response.data)
                }
                _isLoading.value = false
            }
        }
    }

    private fun updateSuperHeroMap(superHeroList: List<SuperHero>) {
        val superHeroMap = superHeroList.associateBy { it.id }
        _superHeroesMap.value = superHeroMap
    }

    fun selectHeroById(heroId: Int) {
        _selectedHero.value = _superHeroesMap.value[heroId]
    }
}