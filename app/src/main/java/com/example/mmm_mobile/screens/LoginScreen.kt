package com.example.mmm_mobile.screens


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mmm_mobile.R
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import com.example.mmm_mobile.viewmodel.LoginViewModel
import org.openapitools.client.infrastructure.ApiClient


@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onMoveToRegistrationClick: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    viewModel.checkLoginStatus(onLoginSuccess = onLoginClick)


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id =R.string.login),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
            Log.d("LoginScreen", "Email: ${viewModel.email.value}")
            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it},
                label = {
                    Text(
                        stringResource(R.string.email),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .testTag("email_field"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )

            Log.d("LoginScreen", "Password: ${viewModel.password.value}")
            PasswordInput(
                password = viewModel.password.value,
                type = stringResource(R.string.password),
                onValueChange = { viewModel.password.value = it}
            )


            Button(
                onClick = {
                    viewModel.login(
                        onLoginSuccess = {
                            viewModel.checkLoginStatus(onLoginSuccess = onLoginClick)
                        },
                        onLoginFailure = {
                            Toast.makeText(context, "Login failed. Try again", Toast.LENGTH_LONG).show()
                        }
                    )
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .testTag("login_button"),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text("Log in", fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Button(
                onClick = {
                    onMoveToRegistrationClick()
                },
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    "Go to Registration",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun PasswordInput(
    password: String,
    type: String = stringResource(R.string.password),
    onValueChange: (String) -> Unit = {}
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { newValue ->
            onValueChange(newValue)
                        },
        label = {
            Text(
                type,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = if (passwordVisibility) Icons.Filled.Lock else Icons.Filled.Info,
                    contentDescription = if (passwordVisibility) stringResource(R.string.hide_password) else stringResource(
                        R.string.show_password
                    ),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .testTag("password_field"),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    )
}