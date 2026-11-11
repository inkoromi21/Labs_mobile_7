package com.example.mycity.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mycity.data.CityData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CityUiState(val categoryId: String = "restaurants", val placeId: String = "zaboy")

class CityViewModel(private val savedState: SavedStateHandle) : ViewModel() {
  private val _uiState =
    MutableStateFlow(
      CityUiState(savedState["category"] ?: "restaurants", savedState["place"] ?: "zaboy")
    )
  val uiState = _uiState.asStateFlow()

  fun selectCategory(id: String) {
    val first = CityData.placesIn(id).first()
    update(CityUiState(id, first.id))
  }

  fun selectPlace(id: String) {
    val place = CityData.places.first { it.id == id }
    update(CityUiState(place.categoryId, id))
  }

  private fun update(state: CityUiState) {
    savedState["category"] = state.categoryId
    savedState["place"] = state.placeId
    _uiState.value = state
  }
}
