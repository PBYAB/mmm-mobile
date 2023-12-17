package com.example.mmm_mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

@Composable
fun SearchScreen() {
    val query = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Search for products and recipes")
        OutlinedTextField(
            value = query.value,
            onValueChange = { query.value = it },
            label = { Text("Search") },
            singleLine = true,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )

        // TODO: Display search results
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    MmmmobileTheme {
        SearchScreen()
    }

}