package cl.noemi.herodaggerhilt.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.noemi.herodaggerhilt.R
import cl.noemi.herodaggerhilt.domain.model.SuperHero
import cl.noemi.herodaggerhilt.presentation.components.TableInfo
import cl.noemi.herodaggerhilt.presentation.viewmodel.HeroViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(viewModel: HeroViewModel) {
    val superHero by viewModel.selectedHero.collectAsState()
    DetailsScreenTabs(hero = superHero)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DetailsScreenTabs(hero: SuperHero?) {
    val tabTitles = listOf(
        stringResource(R.string.info), stringResource(R.string.stats)
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        2
    }
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0

    LazyColumn(
        Modifier.fillMaxSize(),
        lazyListState,
    ) {
        item {
            AsyncImage(
                model = hero?.imageMedium,
                contentDescription = stringResource(R.string.hero_image),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.5f
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
                    .height(376.dp)
            )
        }
        item {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = colorScheme.primary,
                contentColor = colorScheme.surfaceBright
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        text = { Text(text = title) }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorScheme.background)
            ) { pageIndex ->
                when (pageIndex) {
                    0 -> ResumeScreen(hero = hero)
                    1 -> StatsScreen(hero = hero)
                }
            }
        }
    }
}

@Composable
private fun ResumeScreen(hero: SuperHero?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .background(color = colorScheme.background)
    ) {
        Text(
            text = hero?.heroName ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                bottom = 16.dp, start = 12.dp, end = 12.dp
            )
        )
        TableInfo(stringResource(R.string.name), hero?.fullName ?: "")
        TableInfo(stringResource(R.string.aliases), hero?.aliases?.joinToString() ?: "")
        TableInfo(stringResource(R.string.alter_egos), hero?.alterEgos.toString())
        TableInfo(stringResource(R.string.occupation), hero?.occupation ?: "")
        TableInfo(stringResource(R.string.group_affiliation), hero?.groupAffiliation ?: "")
        TableInfo(stringResource(R.string.alignment), hero?.alignment?.uppercase() ?: "")
    }
}


@Composable
private fun StatsScreen(hero: SuperHero?) {
    val stat = hero?.powerStats
    val maxValue = 100.0f
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(32.dp)
            .background(color = colorScheme.background)
    ) {
        val statNames = stringArrayResource(id = R.array.stats_names)

        statNames.forEach { statName ->
            Text(
                text = statName,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp)
            )
            val statValue = when (statName) {
                statNames[0] -> stat?.combat
                statNames[1] -> stat?.durability
                statNames[2] -> stat?.intelligence
                statNames[3] -> stat?.power
                statNames[4] -> stat?.speed
                statNames[5] -> stat?.strength
                else -> null
            }
            LinearProgressIndicator(
                progress = statValue?.toFloat()?.div(maxValue) ?: 0.0f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(8.dp)
            )
        }
    }
}


