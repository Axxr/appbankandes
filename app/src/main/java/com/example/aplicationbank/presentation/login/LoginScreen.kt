package com.example.aplicationbank.presentation.login

import androidx.activity.ComponentActivity
import com.example.aplicationbank.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onNavigateToHome()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Degradado limitado a 305dp
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(305.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF003462),
                            Color(0xFF00C8FF)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.los_andes_logo),
                    contentDescription = "Los Andes Logo",
                    modifier = Modifier.size(250.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Welcome text

            }
        }
        Spacer(modifier = Modifier.height(46.dp))
        Text(
            text = "¡Bienvenido de nuevo!",
            color = Color(0xFF003462),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(46.dp))

        // DNI Input Field
        Text(
            text = "Ingresa tu DNI",
            color = Color(0xFF003462),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
                .padding(horizontal = 24.dp)
        )
        TextField(
            value = state.username,
            onValueChange = viewModel::onUsernameChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFD8DADC), RoundedCornerShape(12.dp)),

            placeholder = { Text("Tu numero de DNI", color = Color(0xFF4D7192)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF003462),
            ),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Contraseña",
            color = Color(0xFF003462),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
                .padding(horizontal = 24.dp)
        )
        // Password Input Field
        TextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFD8DADC), RoundedCornerShape(12.dp)),
            placeholder = { Text("Contraseña", color = Color(0xFF4D7192)) },
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = Color(0xFF003462)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password Text
        TextButton(
            onClick = { /* TODO: Handle forgot password */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "¿Has olvidado tu contraseña?",
                color = Color.Black,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Login Button
        Button(
            onClick = viewModel::onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(50.dp),
            enabled = !state.isLoading && state.username.isNotEmpty() && state.password.isNotEmpty(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (state.username.isNotEmpty() && state.password.isNotEmpty()) Color(0xFF003462) else Color(0xFFB0BEC5),
                contentColor = Color.White
            )
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Ingresar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


