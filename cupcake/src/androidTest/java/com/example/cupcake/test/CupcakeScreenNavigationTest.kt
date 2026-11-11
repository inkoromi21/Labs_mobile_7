package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.*
import com.example.cupcake.R
import com.example.cupcake.ui.OrderViewModel
import org.junit.*
import org.junit.Assert.*

class CupcakeScreenNavigationTest {
  @get:Rule val rule = createAndroidComposeRule<ComponentActivity>()
  private lateinit var nav: TestNavHostController
  private lateinit var model: OrderViewModel

  @Before
  fun setup() {
    model = OrderViewModel()
    rule.setContent {
      nav = TestNavHostController(LocalContext.current)
      nav.navigatorProvider.addNavigator(ComposeNavigator())
      CupcakeApp(model, nav)
    }
  }

  private fun route(screen: CupcakeScreen) {
    rule.runOnIdle { nav.assertCurrentRouteName(screen.name) }
  }

  private fun click(id: Int) = rule.onNodeWithStringId(id).performClick()

  private fun flavor() {
    click(R.string.one_cupcake)
    click(R.string.chocolate)
  }

  private fun pickup() {
    flavor()
    click(R.string.next)
    rule.onNodeWithText(model.uiState.value.pickupOptions.last()).performClick()
  }

  private fun summary() {
    pickup()
    click(R.string.next)
  }

  private fun back() {
    rule.onNodeWithContentDescription(rule.activity.getString(R.string.back_button)).performClick()
  }

  private fun cancel() {
    click(R.string.cancel)
    route(CupcakeScreen.Start)
    rule.runOnIdle {
      assertEquals(0, model.uiState.value.quantity)
      assertNull(nav.previousBackStackEntry)
    }
  }

  @Test
  fun startsAtStart() {
    route(CupcakeScreen.Start)
  }

  @Test
  fun startHasNoBackButton() {
    rule
      .onNodeWithContentDescription(rule.activity.getString(R.string.back_button))
      .assertDoesNotExist()
  }

  @Test
  fun quantityOpensFlavor() {
    click(R.string.one_cupcake)
    route(CupcakeScreen.Flavor)
  }

  @Test
  fun flavorBackReturnsToStart() {
    flavor()
    back()
    route(CupcakeScreen.Start)
  }

  @Test
  fun flavorCancelResetsOrder() {
    flavor()
    cancel()
  }

  @Test
  fun flavorNextOpensPickup() {
    flavor()
    click(R.string.next)
    route(CupcakeScreen.Pickup)
  }

  @Test
  fun pickupBackReturnsToFlavor() {
    pickup()
    back()
    route(CupcakeScreen.Flavor)
  }

  @Test
  fun pickupCancelResetsOrder() {
    pickup()
    cancel()
  }

  @Test
  fun pickupNextOpensSummary() {
    summary()
    route(CupcakeScreen.Summary)
    rule.onNodeWithText(rule.activity.getString(R.string.chocolate)).assertIsDisplayed()
  }

  @Test
  fun summaryCancelResetsOrder() {
    summary()
    cancel()
  }

  @Test
  fun cannotContinueWithoutFlavor() {
    click(R.string.one_cupcake)
    rule.onNodeWithText(rule.activity.getString(R.string.next)).assertIsNotEnabled()
  }
}
