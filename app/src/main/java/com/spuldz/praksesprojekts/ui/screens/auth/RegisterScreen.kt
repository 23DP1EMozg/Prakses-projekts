package com.spuldz.praksesprojekts.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spuldz.praksesprojekts.core.models.FeedbackType
import com.spuldz.praksesprojekts.core.models.UpdateProperty
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.TextMdUnderline
import com.spuldz.praksesprojekts.ui.theme.TextSm
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun RegisterScreen(
    onNavigateToLoginScreen: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val theme = LocalTheme.current
    val feedback by viewModel.feedback.collectAsStateWithLifecycle()
    val registerForm by viewModel.registerForm.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = sizing.dp5)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp30),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sign Up",
                style = TextLg,
                color = theme.Text
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(sizing.dp30, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = feedback.message,
                style = TextSm,
                color = if (feedback.feedbackType == FeedbackType.ERROR)
                    theme.Error else theme.Primary
            )
            Input(
                "Enter Username",
                "Username",
                value = registerForm.username
            ) { value ->
                viewModel.updateRegisterForm(UpdateProperty.USERNAME, value) }
            Input(
                "Enter Password",
                "Password",
                value = registerForm.password
            ) { value ->
                viewModel.updateRegisterForm(UpdateProperty.PASSWORD, value) }
            Input(
                "Enter Password Again",
                "Password Again",
                value = registerForm.passwordAgain
            ) { value ->
                viewModel.updateRegisterForm(UpdateProperty.PASSWORD_AGAIN, value) }
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(sizing.dp64)
                    .background(theme.Primary),
                onClick = { viewModel.createUser() }
            ) {
                Text(
                    text = "Register",
                    style = TextMd,
                    color = theme.Text
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.clickable{ onNavigateToLoginScreen() },
                    text = "Already have a profile? Sign In!",
                    style = TextMdUnderline,
                    color = theme.Text
                )
            }
        }
    }
}
