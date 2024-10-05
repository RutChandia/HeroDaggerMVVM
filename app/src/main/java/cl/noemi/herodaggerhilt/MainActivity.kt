package cl.noemi.herodaggerhilt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.noemi.herodaggerhilt.presentation.navgraph.HeroNav
import cl.noemi.herodaggerhilt.ui.theme.HeroDaggerHiltTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeroDaggerHiltTheme {
                HeroNav()
            }
        }
    }
}