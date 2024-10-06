package cl.noemi.herodaggerhilt.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cl.noemi.herodaggerhilt.R
import cl.noemi.herodaggerhilt.domain.model.SuperHero
import cl.noemi.herodaggerhilt.presentation.components.CardView
import cl.noemi.herodaggerhilt.presentation.components.CenteredProgressIndicator
import cl.noemi.herodaggerhilt.presentation.viewmodel.HeroViewModel

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: HeroViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val superHeroes = viewModel.superHeroesMap.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSuperHeroes()
    }

    if (isLoading) {
        CenteredProgressIndicator()
    } else {
        MainScreenContent(
            superHeroesMap = superHeroes.value,
            onHeroSelected = { heroId ->
                viewModel.selectHeroById(heroId)
                navController.navigate("detailsScreen")
            }
        )
    }

}

@Composable
private fun MainScreenContent(
    superHeroesMap: Map<Int, SuperHero>,
    onHeroSelected: (Int) -> Unit
) {
    val superHeroes = superHeroesMap.values.toList()
    var searchText by remember { mutableStateOf("") }
    val filteredSuperHeroes = superHeroes.filter { hero ->
        hero.heroName.contains(searchText, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(text = stringResource(R.string.search)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface.copy(alpha = 1.0f),
                unfocusedContainerColor = colorScheme.surface.copy(alpha = 1.0f),
                unfocusedIndicatorColor = colorScheme.primary.copy(alpha = 0.8f),
                unfocusedLabelColor = colorScheme.primary.copy(alpha = 0.8f),
            )

        )
        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            items(filteredSuperHeroes) {
                CardView(superHero = it, onItemClick = { heroId ->
                    onHeroSelected(heroId)
                })
            }
        })
    }
}



