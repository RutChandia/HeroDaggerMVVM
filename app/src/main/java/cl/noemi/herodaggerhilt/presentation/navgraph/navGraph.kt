package cl.noemi.herodaggerhilt.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.noemi.herodaggerhilt.presentation.screens.DetailsScreen
import cl.noemi.herodaggerhilt.presentation.screens.MainScreen

@Composable
fun HeroNav(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") { MainScreen(navController) }
        composable(
            "detailsScreen/{heroId}",
            arguments = listOf(navArgument("heroId") { type = NavType.IntType })
        ) { backStackEntry ->
            val heroId = backStackEntry.arguments?.getInt("heroId") ?: -1
            DetailsScreen(heroId = heroId)
        }
    }
}
