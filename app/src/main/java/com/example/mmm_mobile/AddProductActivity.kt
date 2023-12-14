package com.example.mmm_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

class AddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MmmmobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddProduct()
                }
            }
        }
    }
}

data class ProductIngredient(
    var name: String = "",
)

data class Nutriment(
    var energyKcalPer100g: String ="",
    var fatPer100g: String ="",
    var fiberPer100g: String ="",
    var proteinsPer100g: String ="",
    var saltPer100g: String ="",
    var sugarsPer100g: String ="",
    var sodiumPer100g: String =""

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun AddProduct(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var barcode by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var nutriScore by remember { mutableStateOf("") }
    var novaGroup by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var nutriment by remember { mutableStateOf(Nutriment()) }
    var ingredients by remember { mutableStateOf(listOf(ProductIngredient())) }

    Card(
        modifier = modifier.padding(10.dp),
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
                text = stringResource(R.string.add_product_title),
                style = typography.displayMedium
            )
            Text(
                text = stringResource(R.string.product_instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium,
            )
            OutlinedTextField(
                value = name,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> name = newValue
                },
                label = { Text(stringResource(R.string.enter_product_name)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                )
            )

            OutlinedTextField(
                value = barcode,
                singleLine = false, // Ustaw singleLine na false, aby pozwolić na wieloliniowy tekst
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> barcode = newValue
                },
                label = { Text(stringResource(R.string.enter_barcode)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                )
            )

            OutlinedTextField(
                value = quantity,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_quantity)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number // Ustawienie inputType na liczbowy
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )

            OutlinedTextField(
                value = country,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_quantity)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )

            OutlinedTextField(
                value = brand,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_brand)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )

            OutlinedTextField(
                value = nutriment.energyKcalPer100g,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_nutriment_name)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )
            OutlinedTextField(
                value = nutriment.fatPer100g,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_nutriment_name)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )

            OutlinedTextField(
                value = nutriment.fiberPer100g,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_nutriment_name)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )
            OutlinedTextField(
                value = nutriment.saltPer100g,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_nutriment_name)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )
            OutlinedTextField(
                value = nutriment.proteinsPer100g,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_nutriment_name)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )
            OutlinedTextField(
                value = nutriment.sugarsPer100g,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_nutriment_name)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )
            OutlinedTextField(
                value = nutriment.sodiumPer100g,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .padding(start = mediumPadding),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_nutriment_name)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = nutriScore,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(end = mediumPadding),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = {
                            newValue -> nutriScore = newValue
                    },
                    label = { Text(stringResource(R.string.enter_nutriscore)) },
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
                    value = novaGroup,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(start = mediumPadding),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = {
                            newValue -> quantity = newValue
                    },
                    label = { Text(stringResource(R.string.enter_product_novaGroup)) },
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
                IngredientRow2(
                    ingredient = ingredient,
                    onIngredientChange = { newIngredient ->
                        ingredients = ingredients.toMutableList().also {
                            it[index] = newIngredient
                        }
                    }
                ) {
                    ingredients = ingredients.toMutableList().also {
                        it.removeAt(index)
                    }
                }
            }

            AddIngredientRow2(
                onAddIngredient = {
                    ingredients = ingredients.toMutableList().also {
                        it.add(ProductIngredient())
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
                    fontSize = 16.sp
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun IngredientRow2(
    ingredient: ProductIngredient,
    onIngredientChange: (ProductIngredient) -> Unit,
    onRemoveIngredient: () -> Unit
) {
    val context = LocalContext.current
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
                .weight(1f)
                .padding(end = 8.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
            onValueChange = { newValue ->
                onIngredientChange(ingredient.copy(name = newValue))
            },
            label = { Text(stringResource(R.string.enter_ingredient_name)) },
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
        IconButton(
            onClick = {
                onRemoveIngredient()
                keyboardController?.hide()
            }
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
fun AddIngredientRow2(onAddIngredient: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onAddIngredient() },
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.add_ingredient))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductPreview() {
    MmmmobileTheme {
        AddProduct(
            modifier = Modifier.fillMaxSize(),
        )
    }
}
