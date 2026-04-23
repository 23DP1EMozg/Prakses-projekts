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
import androidx.compose.runtime.LaunchedEffect
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
fun LoginScreen(
    onNavigateToRegisterScreen: () -> Unit,
    onLogin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val theme = LocalTheme.current
    val loginForm by viewModel.loginForm.collectAsStateWithLifecycle()
    val feedback by viewModel.feedback.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (feedback.feedbackType == FeedbackType.ERROR) {
            viewModel.resetFeedback()
        }
    }

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
                text = "Sign In",
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
                label ="Enter Username",
                placeholder = "Username",
                value = loginForm.username
            ) { value ->
                viewModel.updateLoginForm(UpdateProperty.USERNAME, value)
            }
            Input(
                label = "Enter Password",
                placeholder = "Password",
                value = loginForm.password
            ) { value ->
                viewModel.updateLoginForm(UpdateProperty.PASSWORD, value)
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(sizing.dp64)
                    .background(theme.Primary),
                onClick = { viewModel.login(onLogin) }
            ) {
                Text(
                    text = "Login",
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
                    modifier = Modifier.clickable{ onNavigateToRegisterScreen() },
                    text = "Don't have a profile? Sign Up!",
                    style = TextMdUnderline,
                    color = theme.Text
                )
            }
        }
    }
}
