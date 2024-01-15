package com.example.mmm_mobile.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mmm_mobile.R
import com.example.mmm_mobile.room.entity.IngredientUnit
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.IngredientApi
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.models.CreateRecipeRequest
import org.openapitools.client.models.IngredientListItem
import org.openapitools.client.models.RecipeIngredientForm


@Composable
fun AddRecipeScreen() {

    val viewModel: AddRecipeViewModel = viewModel()
    var recipeName by rememberSaveable { mutableStateOf("") }
    var recipeInstructions by rememberSaveable { mutableStateOf("") }
    var recipeServings by rememberSaveable { mutableStateOf("") }
    var recipeTime by rememberSaveable { mutableStateOf("") }
    var recipeCaloriesPerServing by rememberSaveable { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf(Ingredient("","",RecipeIngredientForm.Unit.G))) }
    var ingredient by remember { mutableStateOf(Ingredient("","",RecipeIngredientForm.Unit.G)) }
    var test by remember {
        mutableStateOf(RecipeIngredientForm(0.0,0,RecipeIngredientForm.Unit.G))
    }
    val recipeIngredientState by viewModel.recipeIngredients.collectAsState(initial = emptyList())
    val recipeIngredientList= emptyList<RecipeIngredientForm>().toMutableList()

    Card(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        val mediumPadding = 8.dp
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(mediumPadding)
                .fillMaxSize()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.add_recipe_title),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                style = typography.displayMedium,
                modifier = Modifier
                    .padding(mediumPadding)
            )
            Text(
                text = stringResource(R.string.instructions),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                style = typography.titleMedium,
                modifier = Modifier
                    .padding(mediumPadding)
            )
            OutlinedTextField(
                value = recipeName,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(mediumPadding),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
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
                    .padding(mediumPadding),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                ),
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = recipeCaloriesPerServing,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(mediumPadding)
                        .weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    onValueChange = {
                            newValue -> recipeCaloriesPerServing = newValue
                    },
                    label = { Text(stringResource(R.string.enter_calories),fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,) },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
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

                OutlinedTextField(
                    value = recipeTime,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = mediumPadding),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                    onValueChange = {
                            newValue -> recipeTime = newValue
                    },
                    label = { Text(stringResource(R.string.enter_total_time),fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,) },
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

            ingredients.forEachIndexed { index, ingredient ->
                IngredientRow(
                    ingredient = ingredient,
                    onIngredientChange = { newIngredient ->
                        ingredients =
                            ingredients.toMutableList().apply { this[index] = newIngredient }
                    },
                    onRemoveIngredient = {
                        ingredients = ingredients.toMutableList().apply { recipeIngredientList.removeAt(index) }
                    },
                    onIngredientSelected = { selectedIngredient ->
                        val newRecipeIngredient = RecipeIngredientForm(
                            selectedIngredient.amount.toDouble(),
                            selectedIngredient.ingredientId,
                            selectedIngredient.unit ?: RecipeIngredientForm.Unit.G
                        )
                        recipeIngredientList += newRecipeIngredient
                    }
                )
            }

            // Przycisk "Dodaj składnik"
            AddIngredientRow {
                ingredients += Ingredient("", "", RecipeIngredientForm.Unit.G)

                test = RecipeIngredientForm(0.0,0,RecipeIngredientForm.Unit.G)
            }
            val context = androidx.compose.ui.platform.LocalContext.current

            Button(
                modifier = Modifier
                    .padding(top = mediumPadding),
                onClick = {
                    if (isFormValid(
                            recipeInstructions,
                            recipeCaloriesPerServing,
                            recipeName,
                            recipeServings,
                            recipeTime,
                            recipeIngredientList
                        )
                    ) {
                        val createRecipeRequest = CreateRecipeRequest(
                            recipeInstructions,
                            null,
                            recipeIngredientList,
                            recipeCaloriesPerServing.toDouble() ?: 0.0,
                            recipeName ?: "",
                            recipeServings.toInt() ?: 0,
                            recipeTime.toInt() ?: 0
                        )
                        Log.e("RECIPE", createRecipeRequest.toString())
                    } else {
                        Toast.makeText(context, "UZUPELNIJ WSZYSTKIE POLA!!!", Toast.LENGTH_LONG)
                            .show()
                    }
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
private fun isFormValid(
    recipeInstructions: String,
    recipeCaloriesPerServing: String,
    recipeName: String,
    recipeServings: String,
    recipeTime: String,
    recipeIngredientList: MutableList<RecipeIngredientForm>, ): Boolean {
    return recipeInstructions.isNotBlank() &&
            recipeName.isNotBlank() &&
            recipeIngredientList.isNullOrEmpty() &&
            recipeCaloriesPerServing.toDouble()!=0.0 &&
            recipeServings.toInt() !=0 &&
            recipeTime.toInt() != 0
}



data class Ingredient(
    var name: String = "",
    var quantity: String = "",
    var unit: RecipeIngredientForm.Unit
)




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_DropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    val unitList = IngredientUnit.entries.map { it.name }
    val firstUnit = unitList[0]
    var selectedText by rememberSaveable { mutableStateOf(firstUnit) }


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
    onRemoveIngredient: () -> Unit,
    onIngredientSelected: (RecipeIngredientForm) -> Unit
) {

    val viewModel: AddRecipeViewModel = viewModel()
    val recipeIngredientState by viewModel.recipeIngredients.collectAsState(initial = emptyList())
    var recipeIngredient = IngredientListItem(0, "")
    var isDoneClicked by remember { mutableStateOf(false) }
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) {

        viewModel.fetchRecipeIngredients()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        val selectedIngredient = SearchOneItemDropDownMenu(
            setOfItems = recipeIngredientState,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            displayText = { brand -> brand.name ?: "" },
            filterItems = { items, searchText -> items.filter { it.name?.contains(searchText, true) == true } },
            placeholder = { Text(text = (stringResource(R.string.enter_recipe_product)),fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,) },
            onDropDownItemSelected = { selectedBrands ->
                if (selectedBrands != null) {
                    recipeIngredient = selectedBrands
                }
            },
            dropdownItem = { brand ->
                DropDownItem(item = brand) {
                    Text(text = it.name ?: "", fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)
                }
            },
            defaultItem = {
                it.name?.let { it1 -> Log.e("DEFAULT_ITEM", it1) }
            },
            onSearchTextFieldClicked = {
                keyboardController?.show()
            },
        )
        if (selectedIngredient != null) {
            recipeIngredient = selectedIngredient
        }
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = ingredient.quantity,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier
                .weight(1.1f)
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
            modifier = Modifier.weight(1.3f)
        ) {
            Demo_DropDownMenu()
        }



        IconButton(
            onClick = {
                if (isIngredientFormValid(ingredient.quantity,recipeIngredient.id,ingredient.unit)) {
                    if (isDoneClicked) {
                        onRemoveIngredient()
                    } else {
                        val selectedIngredientForm = RecipeIngredientForm(
                            ingredient.quantity.toDouble(),
                            recipeIngredient.id,
                            ingredient.unit ?: RecipeIngredientForm.Unit.G
                        )
                        onIngredientSelected(selectedIngredientForm)
                        keyboardController?.hide()
                    }
                    isDoneClicked = !isDoneClicked
                } else {
                    Toast.makeText(context, "UZUPELNIJ WSZYSTKIE DANE SKLADNIKA!!!", Toast.LENGTH_LONG).show()
                }
                      },
            modifier = Modifier.weight(0.8f)
        ) {
            if (isDoneClicked) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.delete),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
private fun isIngredientFormValid(
    quantity: String,
    id: Long,
    unit: RecipeIngredientForm.Unit): Boolean {
    return quantity.isNotBlank() &&
            id.toInt() != 0 &&
            unit.value.isNotBlank()
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

class AddRecipeViewModel(
    private val ingredientApi: IngredientApi = IngredientApi(),
    private val recipeApi: RecipeApi = RecipeApi(),
) : ViewModel() {

    private val _recipeIngredients = MutableStateFlow<List<IngredientListItem>>(emptyList())
    val recipeIngredients: StateFlow<List<IngredientListItem>> = _recipeIngredients

    fun fetchRecipeIngredients() {
        viewModelScope.launch {
            try {
                val pageRecipeIngredientDTO = withContext(Dispatchers.IO) {
                    ingredientApi.findAll(0,100)
                }
                val recipeIngredients = pageRecipeIngredientDTO.content

                recipeIngredients?.let { _recipeIngredients.emit(it) }
            } catch (e: Exception) {
                Log.e("AddRecipeViewModel", "Error fetching recipe ingredients", e)
            }
        }
    }

    fun addRecipe(createRecipeRequest: CreateRecipeRequest) {
        viewModelScope.launch {
            try {
                recipeApi.createRecipe(createRecipeRequest)
            } catch (e: Exception) {
                Log.e("AddRecipeViewModel", "Error adding recipe", e)
            }
        }
    }
}


@Composable
fun <T: IngredientListItem> SearchOneItemDropDownMenu(
    modifier: Modifier = Modifier,
    displayText: (IngredientListItem) -> String,
    filterItems: (List<T>, String) -> List<T>,
    setOfItems: List<T>,
    enable: Boolean = true,
    readOnly: Boolean = true,
    placeholder: @Composable (() -> Unit) = {
        Text(
            text = "Select Items",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium
        )
    },
    parentTextFieldCornerRadius: Dp = 12.dp,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    onDropDownItemSelected: (IngredientListItem?) -> Unit = {},
    dropdownItem: @Composable (IngredientListItem) -> Unit,
    isError: Boolean = false,
    showDefaultSelectedItem: Boolean = false,
    defaultItemIndex: Int = 0,
    defaultItem: (T) -> Unit,
    onSearchTextFieldClicked: () -> Unit,
): IngredientListItem? {

    var selectedOption by remember { mutableStateOf<IngredientListItem?>(null) }
    var searchedOption by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(expanded) }
    var filteredItems by remember { mutableStateOf(setOfItems) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 530.dp
    val density = LocalDensity.current
    filteredItems = filterItems(setOfItems, searchedOption)

    if (showDefaultSelectedItem && selectedOption == null) {
        selectedOption = setOfItems.getOrNull(defaultItemIndex)
    }

    val maxHeight = remember(itemHeights.toMap()) {
        if (itemHeights.keys.toSet() != setOfItems.indices.toSet()) {
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
        ) { if (expanded || (selectedOption == null && !showDefaultSelectedItem)) {
            placeholder()
        } else {
            // Display selected item
            selectedOption?.let { selectedItem ->
                dropdownItem(selectedItem)
            }
        }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = colors,
            value = selectedOption?.let { displayText(it) } ?: "",
            readOnly = readOnly,
            enabled = enable,
            onValueChange = {},
            placeholder = {},
            trailingIcon = {
                IconToggleButton(
                    checked = isExpanded,
                    onCheckedChange = {
                        isExpanded = it
                        expanded = it
                    },
                ) {
                    if (isExpanded) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowUp,
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
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
                                isExpanded = !isExpanded
                                expanded = isExpanded
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
                        onValueChange = { selectedItem ->
                            searchedOption = selectedItem
                            filteredItems = setOfItems.filter {
                                displayText(it).contains(
                                    searchedOption,
                                    ignoreCase = true,
                                )
                            }
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                        },
                        placeholder = {
                            Text(
                                text = "Search",
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Medium
                            )
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
                        val isChecked = selectedOption == selectedItem

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedOption = if (isChecked) {
                                        null
                                    } else {
                                        selectedItem
                                    }
                                }
                                .padding(10.dp), // Add padding for better spacing
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = null,
                                modifier = Modifier.weight(0.2f)
                            )

                            Spacer(modifier = Modifier.width(100.dp)) // Add space between Checkbox and DropdownMenuItem

                            DropdownMenuItem(
                                modifier = Modifier.weight(0.5f),
                                text = {
                                    // Add your text content here
                                    Text(
                                        displayText(selectedItem),
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                onClick = {
                                    keyboardController?.hide()
                                    selectedOption = selectedItem
                                    searchedOption = selectedItem.toString()
                                    onDropDownItemSelected(selectedOption)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    return selectedOption
}