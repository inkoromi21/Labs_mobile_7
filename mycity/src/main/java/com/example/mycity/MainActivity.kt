package com.example.mycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.ui.graphics.Color
import com.example.mycity.ui.MyCityApp

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MaterialTheme(
        colorScheme =
          lightColorScheme(
            primary = Color(0xFF356859),
            primaryContainer = Color(0xFFD3EBDD),
            secondaryContainer = Color(0xFFE8EFE8),
          )
      ) {
        MyCityApp(windowSize = calculateWindowSizeClass(this).widthSizeClass)
      }
    }
  }
}
