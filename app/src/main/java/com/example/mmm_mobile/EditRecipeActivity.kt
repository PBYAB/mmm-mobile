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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

class EditRecipeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MmmmobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EditRecipe()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipe(modifier: Modifier = Modifier) {
    var recipeName = "";
    var recipeInstructions = "";
    var recipeServings = "";
    var recipeCaloriesPerServing = "";

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
        ) {
            Text(
                text = stringResource(R.string.edit_recipe_title),
                style = typography.displayMedium
            )
            Text(
                text = stringResource(R.string.edit_instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium,
            )
            OutlinedTextField(
                value = "",
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> recipeName = newValue
                },
                label = { Text(stringResource(R.string.edit_recipe_name)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                )
            )

            OutlinedTextField(
                value = "",
                singleLine = false, // Ustaw singleLine na false, aby pozwolić na wieloliniowy tekst
                shape = shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Ustawienie wagi, aby Textfield rozszerzał się w miarę potrzeb
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                onValueChange = {
                        newValue -> recipeInstructions = newValue
                },
                label = { Text(stringResource(R.string.edit_recipe_instructions)) },
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
                    value = "",
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = mediumPadding),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = {
                            newValue -> recipeCaloriesPerServing = newValue
                    },
                    label = { Text(stringResource(R.string.edit_calories)) },
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
                    value = "",
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = mediumPadding),
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                    onValueChange = {
                            newValue -> recipeServings = newValue
                    },
                    label = { Text(stringResource(R.string.edit_servings)) },
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

            Button(
                modifier = Modifier
                    .padding(top = mediumPadding),
                onClick = {
                    recipeName = recipeName
                    recipeInstructions = recipeInstructions
                    val recipeCaloriesPerServingInt = recipeCaloriesPerServing.toIntOrNull() ?: 0
                    val recipeServingsInt = recipeServings.toIntOrNull() ?: 0
                }

            ) {
                Text(
                    text = stringResource(R.string.edit_recipe_button),
                    fontSize = 16.sp
                )
            }
        }
    }
}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun EditRecipePreview() {
//    MmmmobileTheme {
//        EditRecipe(
//            modifier = Modifier.fillMaxSize(),
//        )
//    }
//}