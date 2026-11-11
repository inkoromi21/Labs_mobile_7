package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.*

enum class CupcakeScreen(@StringRes val title: Int) {
  Start(R.string.app_name),
  Flavor(R.string.choose_flavor),
  Pickup(R.string.choose_pickup_date),
  Summary(R.string.order_summary),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
  currentScreen: CupcakeScreen,
  canNavigateBack: Boolean,
  navigateUp: () -> Unit,
  modifier: Modifier = Modifier,
) {
  TopAppBar(
    title = { Text(stringResource(currentScreen.title)) },
    colors =
      TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
      ),
    modifier = modifier,
    navigationIcon = {
      if (canNavigateBack) {
        IconButton(onClick = navigateUp) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back_button))
        }
      }
    },
  )
}

@Composable
fun CupcakeApp(
  viewModel: OrderViewModel = viewModel(),
  navController: NavHostController = rememberNavController(),
) {
  val entry by navController.currentBackStackEntryAsState()
  val screen = CupcakeScreen.valueOf(entry?.destination?.route ?: CupcakeScreen.Start.name)
  val context = LocalContext.current
  val state by viewModel.uiState.collectAsState()
  Scaffold(
    topBar = {
      CupcakeAppBar(
        screen,
        navController.previousBackStackEntry != null,
        { navController.navigateUp() },
      )
    }
  ) { padding ->
    Box(
      Modifier.fillMaxSize().padding(padding),
      contentAlignment = androidx.compose.ui.Alignment.TopCenter,
    ) {
      NavHost(
        navController,
        CupcakeScreen.Start.name,
        Modifier.widthIn(max = 600.dp).fillMaxSize(),
      ) {
        composable(CupcakeScreen.Start.name) {
          StartOrderScreen(
            DataSource.quantityOptions,
            onNextButtonClicked = {
              viewModel.setQuantity(it)
              navController.navigate(CupcakeScreen.Flavor.name)
            },
            modifier = Modifier.fillMaxSize().padding(16.dp),
          )
        }
        composable(CupcakeScreen.Flavor.name) {
          SelectOptionScreen(
            state.price,
            DataSource.flavors.map { context.getString(it) },
            onSelectionChanged = viewModel::setFlavor,
            onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
            onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) },
            modifier = Modifier.fillMaxSize(),
          )
        }
        composable(CupcakeScreen.Pickup.name) {
          SelectOptionScreen(
            state.price,
            state.pickupOptions,
            onSelectionChanged = viewModel::setDate,
            onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
            onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) },
            modifier = Modifier.fillMaxSize(),
          )
        }
        composable(CupcakeScreen.Summary.name) {
          OrderSummaryScreen(
            state,
            onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
            onSendButtonClicked = { subject, summary -> shareOrder(context, subject, summary) },
            modifier = Modifier.fillMaxSize(),
          )
        }
      }
    }
  }
}

private fun cancelOrderAndNavigateToStart(
  viewModel: OrderViewModel,
  navController: NavHostController,
) {
  viewModel.resetOrder()
  navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}

private fun shareOrder(context: Context, subject: String, summary: String) {
  val intent =
    Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_SUBJECT, subject)
      putExtra(Intent.EXTRA_TEXT, summary)
    }
  context.startActivity(Intent.createChooser(intent, context.getString(R.string.new_cupcake_order)))
}
