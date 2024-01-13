package com.example.mmm_mobile.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.R
import com.example.mmm_mobile.room.entity.IngredientUnit
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.models.RecipeDTO


@Composable
fun AddRecipeScreen() {

        var recipeName by remember { mutableStateOf("") }
        var recipeInstructions by remember { mutableStateOf("") }
        var recipeServings by remember { mutableStateOf("") }
        var recipeCaloriesPerServing by remember { mutableStateOf("") }
        var ingredients by remember { mutableStateOf(listOf(Ingredient())) }

        Card(
            modifier = Modifier.padding(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            val mediumPadding = 10.dp
            Column(
                verticalArrangement = Arrangement.spacedBy(mediumPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(mediumPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.add_recipe_title),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    style = typography.displayMedium
                )
                Text(
                    text = stringResource(R.string.instructions),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    style = typography.titleMedium,
                )
                OutlinedTextField(
                    value = recipeName,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = {
                            newValue -> recipeName = newValue
                    },
                    label = { Text(stringResource(R.string.enter_recipe_name),fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { }
                    )
                )

                OutlinedTextField(
                    value = recipeInstructions,
                    singleLine = false, // Ustaw singleLine na false, aby pozwolić na wieloliniowy tekst
                    shape = shapes.large,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Ustawienie wagi, aby Textfield rozszerzał się w miarę potrzeb
                    onValueChange = {
                            newValue -> recipeInstructions = newValue
                    },
                    label = { Text(stringResource(R.string.enter_recipe_instructions),fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { }
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = recipeCaloriesPerServing,
                        singleLine = true,
                        shape = shapes.large,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = mediumPadding),
                        onValueChange = {
                                newValue -> recipeCaloriesPerServing = newValue
                        },
                        label = { Text(stringResource(R.string.enter_calories),fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,) },
                        isError = false,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number // Ustawienie inputType na liczbowy
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { }
                        )
                    )

                    OutlinedTextField(
                        value = recipeServings,
                        singleLine = true,
                        shape = shapes.large,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = mediumPadding),
                        onValueChange = {
                                newValue -> recipeServings = newValue
                        },
                        label = { Text(stringResource(R.string.enter_servings),fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,) },
                        isError = false,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number // Ustawienie inputType na liczbowy
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {}
                        )
                    )
                }

                ingredients.forEachIndexed { index, ingredient ->
                    IngredientRow(
                        ingredient = ingredient,
                        onIngredientChange = { newIngredient ->
                            ingredients = ingredients.toMutableList().also {
                                it[index] = newIngredient
                            }
                        },
                        onRemoveIngredient = {
                            ingredients = ingredients.toMutableList().also {
                                it.removeAt(index)
                            }
                        }
                    )
                }

                AddIngredientRow(
                    onAddIngredient = {
                        ingredients = ingredients.toMutableList().also {
                            it.add(Ingredient())
                        }
                    }
                )


                Button(
                    modifier = Modifier
                        .padding(top = mediumPadding),
                    onClick = {
                    }

                ) {
                    Text(
                        text = stringResource(R.string.add_recipe_button),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }


data class Ingredient(
    var name: String = "",
    var quantity: String = "",
    var unit: String = ""
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_DropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    val unitList = IngredientUnit.entries.map { it.name }
    val firstUnit = unitList[0]
    var selectedText by remember { mutableStateOf(firstUnit) }


        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                unitList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item, fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
}


@Composable
fun IngredientRow(
    ingredient: Ingredient,
    onIngredientChange: (Ingredient) -> Unit,
    onRemoveIngredient: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = ingredient.name,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier
                .weight(2f)
                .padding(end = 8.dp),
            onValueChange = { newValue ->
                onIngredientChange(ingredient.copy(name = newValue))
            },
            label = { Text(stringResource(R.string.enter_ingredient_name),fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,) },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        OutlinedTextField(
            value = ingredient.quantity,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp),
            onValueChange = { newValue ->
                onIngredientChange(ingredient.copy(quantity = newValue))
            },
            label = { Text(stringResource(R.string.enter_ingredient_quantity),fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,) },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Demo_DropDownMenu()
        }

        IconButton(
            onClick = {
                onRemoveIngredient()
                keyboardController?.hide()
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun AddIngredientRow(onAddIngredient: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { onAddIngredient() },
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = stringResource(R.string.add_ingredient))
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AddRecipePreview() {
//    MmmmobileTheme {
//        AddRecipe(
//            modifier = Modifier.fillMaxSize(),
//        )
//    }
//}
