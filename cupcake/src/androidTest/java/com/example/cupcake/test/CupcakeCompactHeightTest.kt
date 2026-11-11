package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import com.example.cupcake.R
import com.example.cupcake.ui.OrderViewModel
import org.junit.Rule
import org.junit.Test

class CupcakeCompactHeightTest {
  @get:Rule val rule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun shortWindowOrderCanBeCompletedAndCancelled() {
    lateinit var nav: TestNavHostController
    val model = OrderViewModel()
    rule.setContent {
      nav = TestNavHostController(LocalContext.current)
      nav.navigatorProvider.addNavigator(ComposeNavigator())
      Box(Modifier.fillMaxWidth().height(240.dp)) { CupcakeApp(model, nav) }
    }

    rule.onNodeWithStringId(R.string.one_cupcake).performScrollTo().performClick()
    rule.onNodeWithStringId(R.string.chocolate).performScrollTo().performClick()
    rule.onNodeWithStringId(R.string.next).performScrollTo().performClick()
    rule.onNodeWithText(model.uiState.value.pickupOptions.last()).performScrollTo().performClick()
    rule.onNodeWithStringId(R.string.next).performScrollTo().performClick()
    rule.runOnIdle { nav.assertCurrentRouteName(CupcakeScreen.Summary.name) }
    rule.onNodeWithStringId(R.string.cancel).performScrollTo().performClick()
    rule.runOnIdle { nav.assertCurrentRouteName(CupcakeScreen.Start.name) }
  }
}
