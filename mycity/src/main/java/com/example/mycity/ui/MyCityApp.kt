package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.mycity.R
import com.example.mycity.data.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityApp(windowSize: WindowWidthSizeClass, model: CityViewModel = viewModel()) {
  val state by model.uiState.collectAsState()
  val nav = rememberNavController()
  val entry by nav.currentBackStackEntryAsState()
  val route = entry?.destination?.route ?: "categories"
  val expanded = windowSize == WindowWidthSizeClass.Expanded
  val category = CityData.categories.first { it.id == state.categoryId }
  val selected = CityData.places.first { it.id == state.placeId }
  fun openCategory(id: String) {
    model.selectCategory(id)
    nav.navigate("places") {
      popUpTo("categories")
      launchSingleTop = true
    }
  }
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            if (expanded || route == "categories") stringResource(R.string.city)
            else if (route == "details") stringResource(selected.name)
            else stringResource(category.title)
          )
        },
        navigationIcon = {
          if (!expanded && route != "categories") {
            IconButton(onClick = { nav.navigateUp() }) {
              Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
            }
          }
        },
      )
    }
  ) { padding ->
    Row(Modifier.fillMaxSize().padding(padding)) {
      if (expanded) {
        PermanentDrawerSheet(Modifier.width(220.dp)) {
          Text(
            stringResource(R.string.choose_category),
            Modifier.padding(20.dp),
            style = MaterialTheme.typography.titleMedium,
          )
          CityData.categories.forEach { c ->
            NavigationDrawerItem(
              label = { Text(stringResource(c.title)) },
              selected = c.id == state.categoryId,
              onClick = { openCategory(c.id) },
              modifier = Modifier.padding(horizontal = 8.dp),
            )
          }
        }
      } else if (windowSize == WindowWidthSizeClass.Medium) {
        val icons =
          listOf(
            Icons.Default.Restaurant,
            Icons.Default.Park,
            Icons.Default.LocalCafe,
            Icons.Default.Theaters,
            Icons.Default.ShoppingBag,
          )
        NavigationRail {
          CityData.categories.forEachIndexed { i, c ->
            NavigationRailItem(
              selected = c.id == state.categoryId && route != "categories",
              onClick = { openCategory(c.id) },
              icon = { Icon(icons[i], stringResource(c.title)) },
            )
          }
        }
      }
      NavHost(nav, "categories", Modifier.weight(1f)) {
        composable("categories") {
          if (expanded) {
            CityPanes(state, model::selectPlace)
          } else {
            LazyColumn(
              contentPadding = PaddingValues(16.dp),
              verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
              item {
                Text(
                  stringResource(R.string.choose_category),
                  style = MaterialTheme.typography.titleLarge,
                )
              }
              items(CityData.categories, key = { it.id }) { c ->
                Card(onClick = { openCategory(c.id) }, modifier = Modifier.fillMaxWidth()) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                      painterResource(c.image),
                      null,
                      Modifier.size(104.dp),
                      contentScale = ContentScale.Crop,
                    )
                    Column(Modifier.padding(16.dp)) {
                      Text(stringResource(c.title), style = MaterialTheme.typography.titleMedium)
                      Text(
                        stringResource(R.string.place_count),
                        style = MaterialTheme.typography.bodyMedium,
                      )
                    }
                  }
                }
              }
            }
          }
        }
        composable("places") {
          if (expanded) CityPanes(state, model::selectPlace)
          else
            PlaceList(
              state.categoryId,
              state.placeId,
              { id ->
                model.selectPlace(id)
                nav.navigate("details")
              },
            )
        }
        composable("details") {
          if (expanded) CityPanes(state, model::selectPlace) else PlaceDetails(selected)
        }
      }
    }
  }
}

@Composable
private fun CityPanes(state: CityUiState, select: (String) -> Unit) {
  Row(Modifier.fillMaxSize()) {
    PlaceList(state.categoryId, state.placeId, select, Modifier.weight(0.45f))
    VerticalDivider()
    PlaceDetails(CityData.places.first { it.id == state.placeId }, Modifier.weight(0.55f))
  }
}

@Composable
private fun PlaceList(
  category: String,
  selected: String,
  select: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
  ) {
    items(CityData.placesIn(category), key = { it.id }) { place ->
      Card(
        onClick = { select(place.id) },
        modifier = Modifier.fillMaxWidth(),
        colors =
          CardDefaults.cardColors(
            containerColor =
              if (place.id == selected) MaterialTheme.colorScheme.primaryContainer
              else MaterialTheme.colorScheme.surfaceContainer
          ),
      ) {
        Image(
          painterResource(place.image),
          null,
          Modifier.fillMaxWidth().height(160.dp),
          contentScale = ContentScale.Crop,
        )
        Column(Modifier.padding(16.dp)) {
          Text(stringResource(place.name), style = MaterialTheme.typography.titleLarge)
          Text(stringResource(place.address), style = MaterialTheme.typography.bodyMedium)
        }
      }
    }
  }
}

@Composable
private fun PlaceDetails(place: Place, modifier: Modifier = Modifier) {
  LazyColumn(
    modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    item {
      Image(
        painterResource(place.image),
        stringResource(place.name),
        Modifier.fillMaxWidth().aspectRatio(1.6f),
        contentScale = ContentScale.Crop,
      )
    }
    item { Text(stringResource(place.name), style = MaterialTheme.typography.headlineMedium) }
    item { Text(stringResource(place.address), style = MaterialTheme.typography.titleMedium) }
    item { Text(stringResource(place.description), style = MaterialTheme.typography.bodyLarge) }
  }
}
