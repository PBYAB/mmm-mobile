package com.example.mmm_mobile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun AddProductScreen() {

    val novaGroupOptions = listOf("1", "2", "3", "4", "5")
    var name by remember { mutableStateOf("") }
    var barcode by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var novaGroup by remember { mutableStateOf(novaGroupOptions) }
    var nutriment by remember { mutableStateOf(Nutriment()) }
    var fiberPer100g by remember { mutableStateOf(nutriment.fiberPer100g) }
    var saltPer100g by remember { mutableStateOf(nutriment.saltPer100g) }
    var sugarPer100g by remember { mutableStateOf(nutriment.sugarsPer100g) }
    var proteinsPer100g by remember { mutableStateOf(nutriment.proteinsPer100g) }
    var caloriesPer100g by remember { mutableStateOf(nutriment.energyKcalPer100g) }
    var sodiumPer100g by remember { mutableStateOf(nutriment.sodiumPer100g) }
    var fatPer100g by remember { mutableStateOf(nutriment.fatPer100g) }

    var ingredients by remember { mutableStateOf(listOf(ProductIngredient())) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val brands = mutableListOf(
        Brand("Basketball"),
        Brand("Rugby"),
        Brand("Football"),
        Brand("MMA"),
        Brand("Motorsport"),
        Brand("Snooker"),
        Brand("Tennis"),
    )

    Card(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        val mediumPadding = 5.dp
        val nutrimentPadding = 15.dp
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
                onValueChange = { newValue ->
                    name = newValue
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
                onValueChange = { newValue ->
                    barcode = newValue
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
//            Button(onClick = {
//                val intent = Intent(this, BarcodeActivity::class.java)
//                startActivity(intent)
//            }) {
//
//            }

            OutlinedTextField(
                value = quantity,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = { newValue ->
                    quantity = newValue
                },
                label = { Text(stringResource(R.string.enter_product_quantity)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                SearchableExpandedDropDownMenu(
                    listOfItems = brands,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = (stringResource(R.string.enter_product_brand))) },
                    onDropDownItemSelected = { item ->
                        item.name
                    },
                    dropdownItem = { test ->
                        DropDownItem(test = test)
                    },
                    defaultItem = {
                        Log.e("DEFAULT_ITEM", it.name)
                    },
                    onSearchTextFieldClicked = {
                        keyboardController?.show()
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                SearchableExpandedDropDownMenu(
                    listOfItems = brands,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = (stringResource(R.string.enter_product_allergen))) },
                    onDropDownItemSelected = { item ->
                        item.name
                    },
                    dropdownItem = { test ->
                        DropDownItem(test = test)
                    },
                    defaultItem = {
                        Log.e("DEFAULT_ITEM", it.name)
                    },
                    onSearchTextFieldClicked = {
                        keyboardController?.show()
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                SearchableExpandedDropDownMenu(
                    listOfItems = brands,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = (stringResource(R.string.enter_product_category))) },
                    onDropDownItemSelected = { item ->
                        item.name
                    },
                    dropdownItem = { test ->
                        DropDownItem(test = test)
                    },
                    defaultItem = {
                        Log.e("DEFAULT_ITEM", it.name)
                    },
                    onSearchTextFieldClicked = {
                        keyboardController?.show()
                    }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                SearchableExpandedDropDownMenu(
                    listOfItems = brands,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = (stringResource(R.string.enter_product_country))) },

                    onDropDownItemSelected = { item ->
                        item.name
                    },
                    dropdownItem = { test ->
                        DropDownItem(test = test)
                    },
                    defaultItem = {
                        Log.e("DEFAULT_ITEM", it.name)
                    },
                    onSearchTextFieldClicked = {
                        keyboardController?.show()
                    }
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 10.dp),
                text = stringResource(R.string.product_nutriment_information),
                textAlign = TextAlign.Center,
                style = typography.titleMedium,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = caloriesPer100g,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(start = nutrimentPadding)
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = { newValue ->
                        caloriesPer100g = newValue
                    },
                    label = { Text(stringResource(R.string.enter_product_nutriment_calorie)) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    )
                )
                OutlinedTextField(
                    value = fatPer100g,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(start = nutrimentPadding)
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = { newValue ->
                        fatPer100g = newValue
                    },
                    label = { Text(stringResource(R.string.enter_product_nutriment_fat)) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = fiberPer100g,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(start = nutrimentPadding)
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = { newValue ->
                        fiberPer100g = newValue
                    },
                    label = { Text(stringResource(R.string.enter_product_nutriment_fiber)) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    )
                )

                OutlinedTextField(
                    value = saltPer100g,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(start = nutrimentPadding)
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = { newValue ->
                        saltPer100g = newValue
                    },
                    label = { Text(stringResource(R.string.enter_product_nutriment_salt)) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = proteinsPer100g,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(start = nutrimentPadding)
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = { newValue ->
                        proteinsPer100g = newValue
                    },
                    label = { Text(stringResource(R.string.enter_product_nutriment_proteins)) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    )
                )

                OutlinedTextField(
                    value = sugarPer100g,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(start = nutrimentPadding)
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = { newValue ->
                        sugarPer100g = newValue
                    },
                    label = { Text(stringResource(R.string.enter_product_nutriment_sugar)) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = sodiumPer100g,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(start = nutrimentPadding)
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = { newValue ->
                        sodiumPer100g = newValue
                    },
                    label = { Text(stringResource(R.string.enter_product_nutriment_sodium)) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Demo_DropDownMenu2("Nutri score", NutriScore.values().map { it.name })
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Demo_DropDownMenu2("Nova Group", novaGroup)
                }

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


            ElevatedButtonExample(
                onClick = {
                }
            )
        }
    }
}


data class ProductIngredient(
    var name: String = "",
)


@Composable
fun DropDownItem(test: Brand) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize(),
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Text(test.name)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_DropDownMenu2(
    name: String,
    list: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    val scoreList = list
    val firstScore = name
    var selectedText by remember { mutableStateOf(firstScore) }


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
            scoreList.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ElevatedButtonExample(onClick: () -> Unit) {
    ElevatedButton({onClick()}){
        Text("Dodaj")
    }
}

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
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
        IconButton(
            onClick = { onAddIngredient() },
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = stringResource(R.string.add_ingredient)
            )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun <T> SearchableExpandedDropDownMenu(
    modifier: Modifier = Modifier,
    listOfItems: List<T>,
    enable: Boolean = true,
    readOnly: Boolean = true,
    placeholder: @Composable (() -> Unit) = { Text(text = "Brand") },
    openedIcon: ImageVector = Icons.Outlined.KeyboardArrowUp,
    closedIcon: ImageVector = Icons.Outlined.KeyboardArrowDown,
    parentTextFieldCornerRadius: Dp = 12.dp,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    onDropDownItemSelected: (T) -> Unit = {},
    dropdownItem: @Composable (T) -> Unit,
    isError: Boolean = false,
    showDefaultSelectedItem: Boolean = false,
    defaultItemIndex: Int = 0,
    defaultItem: (T) -> Unit,
    onSearchTextFieldClicked: () -> Unit,
) {
    var selectedOption by rememberSaveable { mutableStateOf<T?>(null) }
    var searchedOption by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(listOfItems) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 530.dp
    val density = LocalDensity.current

    if (showDefaultSelectedItem && selectedOption == null) {
        selectedOption = listOfItems.getOrNull(defaultItemIndex)
    }

    val maxHeight = remember(itemHeights.toMap()) {
        if (itemHeights.keys.toSet() != listOfItems.indices.toSet()) {
            return@remember baseHeight
        }
        val baseHeightInt = with(density) { baseHeight.toPx().toInt() }

        var sum = with(density) { DropdownMenuVerticalPadding.toPx().toInt() } * 2
        for ((_, itemSize) in itemHeights.toSortedMap()) {
            sum += itemSize
            if (sum >= baseHeightInt) {
                return@remember with(density) { (sum - itemSize / 2).toDp() }
            }
        }
        baseHeight
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            if (expanded || (selectedOption == null && !showDefaultSelectedItem)) {
                placeholder()
            } else {
                defaultItem(selectedOption!!)
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = colors,
            value = selectedOption?.toString() ?: "",
            readOnly = readOnly,
            enabled = enable,
            onValueChange = {},
            placeholder = { },
            trailingIcon = {
                IconToggleButton(
                    checked = expanded,
                    onCheckedChange = {
                        expanded = it
                    },
                ) {
                    if (expanded) {
                        Icon(
                            imageVector = openedIcon,
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            imageVector = closedIcon,
                            contentDescription = null,
                        )
                    }
                }
            },
            shape = RoundedCornerShape(parentTextFieldCornerRadius),
            isError = isError,
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        keyboardController?.show()
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                expanded = !expanded
                            }
                        }
                    }
                },
        )
        if (expanded) {
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .requiredSizeIn(maxHeight = maxHeight),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    onSearchTextFieldClicked()
                },
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .focusRequester(focusRequester),
                        value = searchedOption,
                        onValueChange = { selectedSport ->
                            searchedOption = selectedSport
                            // Update filteredItems when search text changes
                            filteredItems = listOfItems.filter {
                                it.toString().contains(
                                    searchedOption,
                                    ignoreCase = true,
                                )
                            }
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                        },
                        placeholder = {
                            Text(text = "Search")
                        },
                        interactionSource = remember { MutableInteractionSource() }
                            .also { interactionSource ->
                                LaunchedEffect(interactionSource) {
                                    focusRequester.requestFocus()
                                    interactionSource.interactions.collect {
                                        if (it is PressInteraction.Release) {
                                            onSearchTextFieldClicked()
                                        }
                                    }
                                }
                            },
                    )

                    filteredItems.forEach { selectedItem ->
                        var isChecked by remember { mutableStateOf(selectedItem == selectedOption) }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    isChecked = !isChecked
                                    selectedOption = if (isChecked) selectedItem else null
                                }
                                .padding(10.dp), // Add padding for better spacing
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = null,
                                modifier = Modifier.weight(0.2f)
                            )

                            Spacer(modifier = Modifier.width(120.dp)) // Add space between Checkbox and DropdownMenuItem

                            DropdownMenuItem(
                                modifier = Modifier.weight(0.5f),
                                text = {
                                    // Add your text content here
                                    Text(selectedItem.toString())
                                },
                                onClick = {
                                    keyboardController?.hide()
                                    selectedOption = selectedItem
                                    onDropDownItemSelected(selectedItem)
                                    searchedOption = ""
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


private val DropdownMenuVerticalPadding = 5.dp
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AddProductPreview() {
//    MmmmobileTheme {
//        AddProduct(
//            modifier = Modifier.fillMaxSize(),
//        )
//    }
//}
