plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.plugin.compose")
}

android {
  namespace = "com.example.mycity"
  compileSdk = 37

  defaultConfig {
    applicationId = "com.example.mycity"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildFeatures {
    compose = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  val composeBom = platform("androidx.compose:compose-bom:2025.08.00")
  implementation(composeBom)
  implementation("androidx.activity:activity-compose:1.10.1")
  implementation("androidx.compose.material3:material3")
  implementation("androidx.compose.material3:material3-window-size-class")
  implementation("androidx.compose.material:material-icons-extended")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
  implementation("androidx.navigation:navigation-compose:2.9.5")
  implementation("androidx.compose.ui:ui-tooling-preview")
  debugImplementation("androidx.compose.ui:ui-tooling")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation(composeBom)
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  androidTestImplementation("androidx.test:runner:1.6.2")
  androidTestImplementation("androidx.test.ext:junit:1.2.1")
  androidTestImplementation("androidx.navigation:navigation-testing:2.9.5")
  debugImplementation("androidx.compose.ui:ui-test-manifest")
}
