package com.example.mycity.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mycity.R

data class Category(val id: String, @StringRes val title: Int, @DrawableRes val image: Int)

data class Place(
  val id: String,
  val categoryId: String,
  @StringRes val name: Int,
  @StringRes val address: Int,
  @StringRes val description: Int,
  @DrawableRes val image: Int,
)

object CityData {
  val categories =
    listOf(
      Category("restaurants", R.string.restaurants, R.drawable.zaboy),
      Category("parks", R.string.parks, R.drawable.chudes),
      Category("coffee", R.string.coffee, R.drawable.muus),
      Category("cinema", R.string.cinema, R.drawable.yubileiny),
      Category("malls", R.string.malls, R.drawable.laplandiya),
    )
  val places =
    listOf(
      Place(
        "zaboy",
        "restaurants",
        R.string.zaboy_name,
        R.string.zaboy_address,
        R.string.zaboy_description,
        R.drawable.zaboy,
      ),
      Place(
        "munchen",
        "restaurants",
        R.string.munchen_name,
        R.string.munchen_address,
        R.string.munchen_description,
        R.drawable.munchen,
      ),
      Place(
        "port",
        "restaurants",
        R.string.port_name,
        R.string.port_address,
        R.string.port_description,
        R.drawable.port,
      ),
      Place(
        "chudes",
        "parks",
        R.string.chudes_name,
        R.string.chudes_address,
        R.string.chudes_description,
        R.drawable.chudes,
      ),
      Place(
        "bor",
        "parks",
        R.string.bor_name,
        R.string.bor_address,
        R.string.bor_description,
        R.drawable.bor,
      ),
      Place(
        "kuzpark",
        "parks",
        R.string.kuzpark_name,
        R.string.kuzpark_address,
        R.string.kuzpark_description,
        R.drawable.kuzpark,
      ),
      Place(
        "surf",
        "coffee",
        R.string.surf_name,
        R.string.surf_address,
        R.string.surf_description,
        R.drawable.surf,
      ),
      Place(
        "muus",
        "coffee",
        R.string.muus_name,
        R.string.muus_address,
        R.string.muus_description,
        R.drawable.muus,
      ),
      Place(
        "dusham",
        "coffee",
        R.string.dusham_name,
        R.string.dusham_address,
        R.string.dusham_description,
        R.drawable.dusham,
      ),
      Place(
        "kosmos",
        "cinema",
        R.string.kosmos_name,
        R.string.kosmos_address,
        R.string.kosmos_description,
        R.drawable.kosmos,
      ),
      Place(
        "yubileiny",
        "cinema",
        R.string.yubileiny_name,
        R.string.yubileiny_address,
        R.string.yubileiny_description,
        R.drawable.yubileiny,
      ),
      Place(
        "starmax",
        "cinema",
        R.string.starmax_name,
        R.string.starmax_address,
        R.string.starmax_description,
        R.drawable.starmax,
      ),
      Place(
        "laplandiya",
        "malls",
        R.string.laplandiya_name,
        R.string.laplandiya_address,
        R.string.laplandiya_description,
        R.drawable.laplandiya,
      ),
      Place(
        "letocity",
        "malls",
        R.string.letocity_name,
        R.string.letocity_address,
        R.string.letocity_description,
        R.drawable.letocity,
      ),
      Place(
        "promenad",
        "malls",
        R.string.promenad_name,
        R.string.promenad_address,
        R.string.promenad_description,
        R.drawable.promenad,
      ),
    )

  fun placesIn(categoryId: String) = places.filter { it.categoryId == categoryId }
}
