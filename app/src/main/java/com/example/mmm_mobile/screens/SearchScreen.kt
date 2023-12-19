package com.example.mmm_mobile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mmm_mobile.R
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

@Composable
fun SearchScreen() {
    val query = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query.value,
            onValueChange = { query.value = it },
            label = { Text( context.getText(R.string.search_label).toString() ) },
            singleLine = true,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth()
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