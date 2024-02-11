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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.example.mmm_mobile.models.Nutriment
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.AllergenApi
import org.openapitools.client.apis.BrandApi
import org.openapitools.client.apis.CategoryApi
import org.openapitools.client.apis.CountryApi
import org.openapitools.client.apis.ProductApi
import org.openapitools.client.apis.ProductIngredientApi
import org.openapitools.client.models.AllergenDTO
import org.openapitools.client.models.BrandDTO
import org.openapitools.client.models.CategoryDTO
import org.openapitools.client.models.CountryDTO
import org.openapitools.client.models.CreateNutrimentRequest
import org.openapitools.client.models.CreateProductIngredientAnalysisRequest
import org.openapitools.client.models.CreateProductRequest
import org.openapitools.client.models.ProductIngredientDTO

@Composable
fun AddProductScreen(
    onAddProductClick: (Long) -> Unit,
    snackbarHostState: SnackbarHostState
) {

    val viewModel: AddProductViewModel = viewModel()
    val brandsState by viewModel.brands.collectAsState(initial = emptyList())
    val allergensState by viewModel.allergens.collectAsState(initial = emptyList())
    val categoriesState by viewModel.categories.collectAsState(initial = emptyList())
    val countriesState by viewModel.countries.collectAsState(initial = emptyList())
    val productIngredientState by viewModel.productIngredients.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchBrands()
        viewModel.fetchAllergens()
        viewModel.fetchCategories()
        viewModel.fetchCountries()
        viewModel.fetchProductIngredients()
    }

    val novaGroupOptions = listOf("1", "2", "3", "4", "5")
    val nutriScoreOptions = listOf("A", "B", "C", "D", "E")
    var name by rememberSaveable { mutableStateOf("") }
    var barcode by rememberSaveable { mutableStateOf("") }
    var quantity by rememberSaveable { mutableStateOf("") }
    var selectedCategories = emptySet<CategoryDTO>()
    var selectedCountries = emptySet<CountryDTO>()
    var selectedAllergens = emptySet<AllergenDTO>()
    var selectedBrands = emptySet<BrandDTO>()
    var selectedProductIngredients = emptySet<ProductIngredientDTO>()
    val palmOil = rememberSaveable { mutableStateOf(false) }
    val vegan = rememberSaveable { mutableStateOf(false) }
    val vegetarian = rememberSaveable { mutableStateOf(false) }
    var productIngredients by rememberSaveable { mutableStateOf("") }
    var novaGroup by remember { mutableStateOf("") }
    var nutriScore by remember { mutableStateOf("") }
    val nutriment by remember { mutableStateOf(Nutriment()) }
    var fiberPer100g by rememberSaveable { mutableStateOf(nutriment.fiberPer100g) }
    var saltPer100g by rememberSaveable { mutableStateOf(nutriment.saltPer100g) }
    var sugarPer100g by rememberSaveable { mutableStateOf(nutriment.sugarsPer100g) }
    var proteinsPer100g by rememberSaveable { mutableStateOf(nutriment.proteinsPer100g) }
    var caloriesPer100g by rememberSaveable { mutableStateOf(nutriment.energyKcalPer100g) }
    var sodiumPer100g by rememberSaveable { mutableStateOf(nutriment.sodiumPer100g) }
    var fatPer100g by rememberSaveable { mutableStateOf(nutriment.fatPer100g) }
    val keyboardController = LocalSoftwareKeyboardController.current

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
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                style = typography.displayMedium
            )
            Text(
                text = stringResource(R.string.product_instructions),
                textAlign = TextAlign.Center,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                style = typography.titleMedium,
            )
            OutlinedTextField(
                value = name,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = { newValue ->
                    name = newValue
                },
                label = {
                    Text(
                        stringResource(R.string.enter_product_name),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                    )
                },
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
                singleLine = false,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = { newValue ->
                    barcode = newValue
                },
                label = {
                    Text(
                        stringResource(R.string.enter_barcode), fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                    )
                },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            val context = androidx.compose.ui.platform.LocalContext.current


            OutlinedTextField(
                value = quantity,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = { newValue ->
                    quantity = newValue
                },
                label = {
                    Text(
                        stringResource(R.string.enter_product_quantity),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                    )
                },
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
                selectedBrands = SearchableExpandedDropDownMenu(
                    setOfItems = brandsState,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    displayText = { brand -> brand.name },
                    filterItems = { items, searchText ->
                        items.filter {
                            it.name.contains(
                                searchText,
                                true
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            text = (stringResource(R.string.enter_product_brand)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    onDropDownItemSelected = { selectedBrands ->
                        for (brand in selectedBrands) {
                            Log.d("Selected Brands", brand.name)
                        }
                    },
                    dropdownItem = {
                        Text(
                            text = (stringResource(R.string.enter_product_brand)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    defaultItem = {
                        it.name.let { it1 -> Log.e("DEFAULT_ITEM", it1) }
                    },
                    onSearchTextFieldClicked = {
                        keyboardController?.show()
                    }
                )
            }

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
            ) {
                selectedAllergens = SearchableExpandedDropDownMenu(
                    setOfItems = allergensState,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    displayText = { allergen -> allergen.name },
                    filterItems = { items, searchText ->
                        items.filter {
                            it.name.contains(
                                searchText,
                                true
                            ) == true
                        }
                    },
                    placeholder = {
                        Text(
                            text = (stringResource(R.string.enter_product_allergen)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    onDropDownItemSelected = { selectedAllergens ->
                        for (allergen in selectedAllergens) {
                            Log.d("Selected Allergens", allergen.name)
                        }
                    },
                    dropdownItem = { _ ->
                        Text(
                            text = (stringResource(R.string.enter_product_allergen)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    defaultItem = {
                        it.name.let { it1 -> Log.e("DEFAULT_ITEM", it1) }
                    },
                    onSearchTextFieldClicked = {
                        keyboardController?.show()
                    }
                )
            }


            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
            ) {
                selectedCategories = SearchableExpandedDropDownMenu(
                    setOfItems = categoriesState,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    displayText = { category -> category.name },
                    filterItems = { items, searchText ->
                        items.filter {
                            it.name.contains(
                                searchText,
                                true
                            ) == true
                        }
                    },
                    placeholder = {
                        Text(
                            text = (stringResource(R.string.enter_product_category)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    onDropDownItemSelected = { selectedCategories ->
                        for (category in selectedCategories) {
                            Log.d("Selected Categories", category.name)
                        }
                    },
                    dropdownItem = { _ ->
                        Text(
                            text = (stringResource(R.string.enter_product_category)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    defaultItem = {
                        it.name.let { it1 -> Log.e("DEFAULT_ITEM", it1) }
                    },
                    onSearchTextFieldClicked = {
                        keyboardController?.show()
                    }
                )
            }

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
            ) {
                selectedCountries = SearchableExpandedDropDownMenu(
                    setOfItems = countriesState,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    displayText = { country -> country.name },
                    filterItems = { items, searchText ->
                        items.filter {
                            it.name.contains(
                                searchText,
                                true
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            text = (stringResource(R.string.enter_product_country)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },

                    onDropDownItemSelected = { selectedCountries ->
                        for (country in selectedCountries) {
                            Log.d("Selected Country", country.name)
                        }
                    },
                    dropdownItem = { _ ->
                        Text(
                            text = (stringResource(R.string.enter_product_country)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    defaultItem = {
                        it.name.let { it1 -> Log.e("DEFAULT_ITEM", it1) }
                    },
                    onSearchTextFieldClicked = {
                        keyboardController?.show()
                    }
                )
            }

            CheckboxWithLabel(stringResource(R.string.palm_oil),palmOil) { isChecked -> palmOil.value = isChecked }
            CheckboxWithLabel(stringResource(R.string.vegan), vegan) { isChecked -> vegan.value = isChecked }
            CheckboxWithLabel(stringResource(R.string.vegetarian), vegetarian) { isChecked ->
                vegetarian.value = isChecked
            }

            OutlinedTextField(
                value = productIngredients,
                singleLine = false,
                shape = shapes.large,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                onValueChange = { newValue ->
                    productIngredients = newValue
                },
                label = {
                    Text(
                        stringResource(R.string.enter_product_ingredients),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                    )
                },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                )
            )


            Text(
                modifier = Modifier
                    .padding(top = 10.dp),
                text = stringResource(R.string.product_nutriment_information),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    onValueChange = { newValue ->
                        caloriesPer100g = newValue
                    },
                    label = {
                        Text(
                            stringResource(R.string.enter_product_nutriment_calorie),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    isError = false,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                        }
                    )
                )
                OutlinedTextField(
                    value = fatPer100g,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .padding(start = nutrimentPadding)
                        .weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    onValueChange = { newValue ->
                        fatPer100g = newValue
                    },
                    label = {
                        Text(
                            stringResource(R.string.enter_product_nutriment_fat),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    },
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    onValueChange = { newValue ->
                        fiberPer100g = newValue
                    },
                    label = {
                        Text(
                            stringResource(R.string.enter_product_nutriment_fiber),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    onValueChange = { newValue ->
                        saltPer100g = newValue
                    },
                    label = {
                        Text(
                            stringResource(R.string.enter_product_nutriment_salt),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    onValueChange = { newValue ->
                        proteinsPer100g = newValue
                    },
                    label = {
                        Text(
                            stringResource(R.string.enter_product_nutriment_proteins),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    onValueChange = { newValue ->
                        sugarPer100g = newValue
                    },
                    label = {
                        Text(
                            stringResource(R.string.enter_product_nutriment_sugar),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    onValueChange = { newValue ->
                        sodiumPer100g = newValue
                    },
                    label = {
                        Text(
                            stringResource(R.string.enter_product_nutriment_sodium),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    },
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
                    Demo_DropDownMenu2(
                        stringResource(R.string.nutri_score),
                        nutriScoreOptions,
                        onItemSelected = { nutriScore = it }
                    )
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Demo_DropDownMenu2(
                        stringResource(R.string.nova_group),
                        novaGroupOptions,
                        onItemSelected = { novaGroup = it }
                    )
                }

            }

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
            ) {
                selectedProductIngredients = SearchableExpandedDropDownMenu(
                    setOfItems = productIngredientState,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surface,
                        unfocusedContainerColor = colorScheme.surface,
                        disabledContainerColor = colorScheme.surface,
                    ),
                    displayText = { productIngredient -> productIngredient.name },
                    filterItems = { items, searchText ->
                        items.filter {
                            it.name.contains(
                                searchText,
                                true
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            text = (stringResource(R.string.enter_product_ingredients)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },

                    onDropDownItemSelected = { _ ->
                        for (country in selectedCountries) {
                            Log.d("Selected Country", country.name)
                        }
                    },
                    dropdownItem = { _ ->
                        Text(
                            text = (stringResource(R.string.enter_product_ingredients)),
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    defaultItem = {
                        it.name.let { it1 -> Log.e("DEFAULT_ITEM", it1) }
                    },
                    onSearchTextFieldClicked = {
                        keyboardController?.show()
                    }
                )
            }


            ElevatedButtonExample(
                onClick = {
                    if(isProductValid(selectedAllergens,barcode, selectedBrands, selectedCategories, selectedCountries, productIngredients, selectedProductIngredients,name, novaGroup, nutriScore, caloriesPer100g, fatPer100g,fiberPer100g,proteinsPer100g, saltPer100g, sodiumPer100g, sugarPer100g, quantity)){
                    val createProductRequest = CreateProductRequest(
                        selectedAllergens.map { it.id }.toSet() as Set<Long> ?: emptySet<Long>(),
                        barcode,
                        selectedBrands.map { it.id }.toSet() as Set<Long> ?: emptySet<Long>(),
                        selectedCategories.map { it.id }.toSet() as Set<Long> ?: emptySet<Long>(),
                        selectedCountries.map { it.id }.toSet() as Set<Long> ?: emptySet<Long>(),
                        CreateProductIngredientAnalysisRequest(palmOil.value, productIngredients ?: "", vegan.value, vegetarian.value),
                        selectedProductIngredients.map { it.id }.toSet() as Set<Long> ?: emptySet<Long>(),
                        name ?: "",
                        novaGroup.toInt() ?: 1,
                        mapNutriScoreToNumber(nutriScore) ?: 1,
                        CreateNutrimentRequest(
                            caloriesPer100g.toDouble() ?: 0.0,
                            fatPer100g.toDouble() ?: 0.0,
                            fiberPer100g.toDouble() ?: 0.0,
                            proteinsPer100g.toDouble() ?: 0.0,
                            saltPer100g.toDouble() ?: 0.0,
                            sodiumPer100g.toDouble() ?: 0.0,
                            sugarPer100g.toDouble() ?: 0.0
                        ),
                        quantity ?: ""
                    )

                    viewModel.addProduct(
                        createProductRequest,
                        snackbarHostState,
                        onAddProduct = onAddProductClick
                    )
                    } else {
                        Toast.makeText(context, context.getText(R.string.valitation_error).toString(), Toast.LENGTH_LONG)
                            .show()
                    }

                }
            )
        }
    }
}

fun mapNutriScoreToNumber(nutriScore: String): Int {
    val upperCaseScore = nutriScore.toUpperCase()
    if (upperCaseScore.length == 1 && upperCaseScore in "ABCDE") {
        return upperCaseScore[0] - 'A' + 1
    } else {
        throw IllegalArgumentException("Invalid Nutri-Score: $nutriScore")
    }
}

private fun isProductValid(
    selectedAllergens: Set<AllergenDTO>,
    barcode: String,
    selectedBrands: Set<BrandDTO>,
    selectedCategories: Set<CategoryDTO>,
    selectedCountries: Set<CountryDTO>,
    productIngredients: String,
    selectedProductIngredients: Set<ProductIngredientDTO>,
    name: String,
    novaGroup: String,
    nutriScore: String,
    caloriesPer100g: String,
    fatPer100g: String,
    fiberPer100g: String,
    proteinsPer100g: String,
    saltPer100g: String,
    sodiumPer100g: String,
    sugarPer100g: String,
    quantity: String
): Boolean {
    return selectedAllergens.isNotEmpty() &&
            barcode.isNotBlank() &&
            selectedBrands.isNotEmpty() &&
            selectedCountries.isNotEmpty() &&
            selectedCategories.isNotEmpty() &&
            selectedProductIngredients.isNotEmpty() &&
            productIngredients.isNotBlank() &&
            name.isNotBlank() &&
            novaGroup.isNotBlank() &&
            nutriScore.isNotBlank() &&
            caloriesPer100g.isNotBlank() &&
            fatPer100g.isNotBlank() &&
            proteinsPer100g.isNotBlank() &&
            saltPer100g.isNotBlank() &&
            sodiumPer100g.isNotBlank() &&
            sugarPer100g.isNotBlank() &&
            quantity.isNotBlank() &&
            fiberPer100g.isNotBlank()
}

@Composable
fun CheckboxWithLabel(
    label: String,
    checked: MutableState<Boolean>,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label, fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium
        )
        Checkbox(
            checked = checked.value,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}


@Composable
fun <T> DropDownItem(item: T, content: @Composable (T) -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize(),
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        content(item)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_DropDownMenu2(
    name: String,
    list: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val scoreList = list
    val firstScore = name
    var selectedText by rememberSaveable { mutableStateOf(firstScore) }

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
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            scoreList.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    onClick = {
                        try {
                            selectedText = item
                            onItemSelected(item)
                            expanded = false
                        } catch (_: NumberFormatException) {
                            Log.e("DropdownMenu", "Unable to parse $item to number")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ElevatedButtonExample(onClick: () -> Unit) {
    ElevatedButton({ onClick() }) {
        Text(
            stringResource(R.string.add_button), fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
        )
    }
}


@Composable
fun <T> SearchableExpandedDropDownMenu(
    modifier: Modifier = Modifier,
    displayText: (T) -> String,
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
    onDropDownItemSelected: (Set<T>) -> Unit = {},
    dropdownItem: @Composable (T) -> Unit,
    isError: Boolean = false,
    showDefaultSelectedItem: Boolean = false,
    defaultItemIndex: Int = 0,
    defaultItem: (T) -> Unit,
    onSearchTextFieldClicked: () -> Unit,
): Set<T> {

    var selectedOptions by rememberSaveable { mutableStateOf<Set<T>>(emptySet()) }
    var searchedOption by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(setOfItems) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 530.dp
    val density = LocalDensity.current
    filteredItems = filterItems(setOfItems, searchedOption)

    if (showDefaultSelectedItem && selectedOptions.isEmpty()) {
        selectedOptions = setOfItems.take(defaultItemIndex + 1).toSet()
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
        ) {
            if (expanded || (selectedOptions.isEmpty() && !showDefaultSelectedItem)) {
                placeholder()
            } else {
                // Display selected items
                selectedOptions.forEach { selectedOption ->
                    dropdownItem(selectedOption)
                }
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = colors,
            value = selectedOptions.joinToString(", ", transform = displayText),
            readOnly = readOnly,
            enabled = enable,
            onValueChange = {},
            placeholder = {},
            trailingIcon = {
                IconToggleButton(
                    checked = expanded,
                    onCheckedChange = {
                        expanded = it
                    },
                ) {
                    if (expanded) {
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
                        val isChecked =
                            selectedOptions.contains(selectedItem)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (isChecked) {
                                        selectedOptions = selectedOptions - selectedItem
                                    } else {
                                        selectedOptions = selectedOptions + selectedItem
                                    }
                                }
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = null,
                                modifier = Modifier.weight(0.2f)
                            )

                            Spacer(modifier = Modifier.width(100.dp))

                            DropdownMenuItem(
                                modifier = Modifier.weight(0.5f),
                                text = {
                                    Text(
                                        displayText(selectedItem),
                                        fontFamily = poppinsFontFamily,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                onClick = {
                                    keyboardController?.hide()
                                    onDropDownItemSelected(selectedOptions)
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
    return selectedOptions
}


class AddProductViewModel(
    private val brandsApi: BrandApi = BrandApi(),
    private val countryApi: CountryApi = CountryApi(),
    private val allergenApi: AllergenApi = AllergenApi(),
    private val categoryApi: CategoryApi = CategoryApi(),
    private val productApi: ProductApi = ProductApi(),
    private val productIngredientApi: ProductIngredientApi = ProductIngredientApi()

) : ViewModel() {

    private val _brands = MutableStateFlow<List<BrandDTO>>(emptyList())
    val brands: StateFlow<List<BrandDTO>> = _brands
    private val _allergens = MutableStateFlow<List<AllergenDTO>>(emptyList())
    val allergens: StateFlow<List<AllergenDTO>> = _allergens
    private val _categories = MutableStateFlow<List<CategoryDTO>>(emptyList())
    val categories: StateFlow<List<CategoryDTO>> = _categories
    private val _countries = MutableStateFlow<List<CountryDTO>>(emptyList())
    val countries: StateFlow<List<CountryDTO>> = _countries
    private val _productIngredients = MutableStateFlow<List<ProductIngredientDTO>>(emptyList())
    val productIngredients: StateFlow<List<ProductIngredientDTO>> = _productIngredients


    fun fetchBrands() {
        viewModelScope.launch {
            try {
                val pageBrandDTO = withContext(Dispatchers.IO) {
                    brandsApi.getAllBrands(0, 50)
                }
                val brands = pageBrandDTO.content

                brands?.let { _brands.emit(it) }
            } catch (e: Exception) {
                Log.e("AddProductViewModel", "Error fetching brands", e)
            }

        }
    }

    fun fetchAllergens() {
        viewModelScope.launch {
            try {
                val pageAllergenDTO = withContext(Dispatchers.IO) {
                    allergenApi.listAllergens(0, 50)
                }

                // Assuming PageBrandDTO has a property 'items' which is a List<BrandDTO>
                val allergens = pageAllergenDTO.content

                allergens?.let { _allergens.emit(it) }
            } catch (e: Exception) {
                Log.e("AddProductViewModel", "Error fetching allergens", e)
            }

        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val pageCategoryDTO = withContext(Dispatchers.IO) {
                    categoryApi.getCategories(0, 50)
                }
                val categories = pageCategoryDTO.content

                categories?.let { _categories.emit(it) }
            } catch (e: Exception) {
                Log.e("AddProductViewModel", "Error fetching categories", e)
            }

        }
    }

    fun fetchCountries() {
        viewModelScope.launch {
            try {
                val pageCountriesDTO = withContext(Dispatchers.IO) {
                    countryApi.getCountries(0, 50)
                }

                val countries = pageCountriesDTO.content

                countries?.let { _countries.emit(it) }
            } catch (e: Exception) {
                Log.e("AddProductViewModel", "Error fetching countries", e)
            }

        }
    }

    fun fetchProductIngredients() {
        viewModelScope.launch {
            try {
                val pageProductIngredientDTO = withContext(Dispatchers.IO) {
                    productIngredientApi.getProductIngredients(0, 50)
                }

                val productIngredients = pageProductIngredientDTO.content

                productIngredients?.let { _productIngredients.emit(it) }
            } catch (e: Exception) {
                Log.e("AddProductViewModel", "Error fetching product ingredients", e)
            }

        }
    }

    fun addProduct(
        createProductRequest: CreateProductRequest,
        snackbarHostState: SnackbarHostState,
        onAddProduct: (Long) -> Unit
    ) {
        viewModelScope.launch {

            try {
                val response = productApi.createProductWithHttpInfo(createProductRequest)

                if (response.statusCode == 201) {
                    viewModelScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Product ${createProductRequest.name} added",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short
                        )
                    }

                    val location = response.headers["Location"]?.firstOrNull()
                    val productId = location?.substringAfterLast("/")?.toLongOrNull()
                    Log.d("AddRecipeViewModel", "Headers: ${response.headers}")

                    productId?.let {
                        onAddProduct(productId)
                    }

                } else {
                    viewModelScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Error adding product ${createProductRequest.name}",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("AddProductViewModel", "Error adding product", e)
            }
        }
    }
}

val DropdownMenuVerticalPadding = 5.dp