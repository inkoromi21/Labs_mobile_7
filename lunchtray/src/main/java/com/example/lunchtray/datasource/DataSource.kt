/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lunchtray.datasource

import com.example.lunchtray.model.MenuItem.AccompanimentItem
import com.example.lunchtray.model.MenuItem.EntreeItem
import com.example.lunchtray.model.MenuItem.SideDishItem

/** Map of available menu items to be displayed in the menu fragments. */
object DataSource {

  val entreeMenuItems =
    listOf(
      EntreeItem(
        name = "Цветная капуста",
        description = "Запечённая цветная капуста с хрустящей корочкой",
        price = 7.00,
      ),
      EntreeItem(
        name = "Чили с фасолью",
        description = "Три вида фасоли, тушённые с луком",
        price = 4.00,
      ),
      EntreeItem(
        name = "Паста с грибами",
        description = "Паста с грибами, базиликом и томатами",
        price = 5.50,
      ),
      EntreeItem(
        name = "Острая фасоль",
        description = "Фасоль с овощами, авокадо и пряностями",
        price = 5.50,
      ),
    )

  val sideDishMenuItems =
    listOf(
      SideDishItem(
        name = "Летний салат",
        description = "Томаты, салат, персики и авокадо",
        price = 2.50,
      ),
      SideDishItem(
        name = "Тыквенный суп",
        description = "Суп из запечённой тыквы с перцем",
        price = 3.00,
      ),
      SideDishItem(
        name = "Пряный картофель",
        description = "Картофель, обжаренный с пряностями",
        price = 2.00,
      ),
      SideDishItem(
        name = "Кокосовый рис",
        description = "Рис с кокосовым молоком и лаймом",
        price = 1.50,
      ),
    )

  val accompanimentMenuItems =
    listOf(
      AccompanimentItem(name = "Булочка", description = "Свежая домашняя булочка", price = 0.50),
      AccompanimentItem(
        name = "Ягодное ассорти",
        description = "Клубника, черника и малина",
        price = 1.00,
      ),
      AccompanimentItem(
        name = "Маринованные овощи",
        description = "Домашние маринованные огурцы и морковь",
        price = 0.50,
      ),
    )
}
