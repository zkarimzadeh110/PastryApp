package com.example.pastry.ui.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.pastry.R
import com.example.pastry.ui.viewModel.LoginViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen( modifier: Modifier = Modifier,onSignUpClick: () -> Unit = {},onLoginClick: () -> Unit = {}, viewModel: LoginViewModel = hiltViewModel()) {
    var userName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    if (viewModel.uiState.isLoggedIn) {
        onLoginClick()
    }
    Box(modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp).clip(shape = RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp))
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(R.drawable.macarons),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Not a member?")
                    TextButton(onClick = { onSignUpClick() }) {
                        Text("Sign up",style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "Log in",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(64.dp))
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                placeholder = {
                    Text("Username")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .padding(
                        start = 24.dp, end = 24.dp
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text("Password")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .padding(
                        start = 24.dp, end = 24.dp
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
            Spacer(modifier = Modifier.height(64.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 24.dp, end = 24.dp),
                onClick = { viewModel.login(userName, password) },
                shape = MaterialTheme.shapes.large
            ) { Text("Log in") }
    }
    if (viewModel.uiState.networkRequestInfo.loading) {
        Surface(
            modifier = Modifier.wrapContentSize().align(Alignment.Center),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            CircularProgressIndicator(0.75f,Modifier.padding(24.dp))
        }
    }
 }
}
@Preview(showBackground = true, heightDp = 800, widthDp = 500, name = "Login preview" )
@Composable
fun LoginPreview() {
   /// LoginScreen()
}