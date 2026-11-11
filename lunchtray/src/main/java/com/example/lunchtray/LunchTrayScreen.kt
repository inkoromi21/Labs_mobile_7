package com.example.lunchtray

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.ui.*

enum class LunchTrayScreen(@StringRes val title: Int) {
  Start(R.string.app_name),
  Entree(R.string.choose_entree),
  SideDish(R.string.choose_side_dish),
  Accompaniment(R.string.choose_accompaniment),
  Checkout(R.string.order_checkout),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp(
  viewModel: OrderViewModel = viewModel(),
  navController: NavHostController = rememberNavController(),
) {
  val entry by navController.currentBackStackEntryAsState()
  val screen = LunchTrayScreen.valueOf(entry?.destination?.route ?: LunchTrayScreen.Start.name)
  val state by viewModel.uiState.collectAsState()
  val cancel = {
    viewModel.resetOrder()
    navController.popBackStack(LunchTrayScreen.Start.name, false)
    Unit
  }
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(stringResource(screen.title)) },
        navigationIcon = {
          if (navController.previousBackStackEntry != null) {
            IconButton(onClick = { navController.navigateUp() }) {
              Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back_button))
            }
          }
        },
      )
    }
  ) { padding ->
    Box(
      Modifier.fillMaxSize().padding(padding),
      contentAlignment = androidx.compose.ui.Alignment.TopCenter,
    ) {
      NavHost(
        navController,
        LunchTrayScreen.Start.name,
        Modifier.widthIn(max = 600.dp).fillMaxSize(),
      ) {
        composable(LunchTrayScreen.Start.name) {
          StartOrderScreen(
            onStartOrderButtonClicked = { navController.navigate(LunchTrayScreen.Entree.name) },
            modifier = Modifier.fillMaxSize(),
          )
        }
        composable(LunchTrayScreen.Entree.name) {
          EntreeMenuScreen(
            DataSource.entreeMenuItems,
            cancel,
            { navController.navigate(LunchTrayScreen.SideDish.name) },
            viewModel::updateEntree,
            Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
          )
        }
        composable(LunchTrayScreen.SideDish.name) {
          SideDishMenuScreen(
            DataSource.sideDishMenuItems,
            cancel,
            { navController.navigate(LunchTrayScreen.Accompaniment.name) },
            viewModel::updateSideDish,
            Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
          )
        }
        composable(LunchTrayScreen.Accompaniment.name) {
          AccompanimentMenuScreen(
            DataSource.accompanimentMenuItems,
            cancel,
            { navController.navigate(LunchTrayScreen.Checkout.name) },
            viewModel::updateAccompaniment,
            Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
          )
        }
        composable(LunchTrayScreen.Checkout.name) {
          CheckoutScreen(
            state,
            cancel,
            cancel,
            Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
          )
        }
      }
    }
  }
}
