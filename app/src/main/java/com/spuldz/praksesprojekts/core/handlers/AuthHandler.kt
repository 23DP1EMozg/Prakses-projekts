package com.spuldz.praksesprojekts.core.handlers

import com.spuldz.praksesprojekts.core.models.Feedback
import com.spuldz.praksesprojekts.core.models.FeedbackType
import com.spuldz.praksesprojekts.core.models.RegisterForm

class AuthHandler {
    fun isPasswordValid(password: String?) : Boolean {
        if (password == null) return false
        return password.length >= 7
    }

    fun isUsernameValid(username: String?) : Boolean {
        if (username == null) return false
        return username.length >= 7
    }

    fun isUserValid(registerForm: RegisterForm) : Feedback{
        if (!isUsernameValid(registerForm.username)) {
            return Feedback(
                message = "username must be at least 7 symbols long",
                feedbackType = FeedbackType.ERROR
            )
        }

        if (!isPasswordValid(registerForm.password)) {
            return Feedback(
                message = "password must be at least 7 symbols long",
                feedbackType = FeedbackType.ERROR
            )
        }

        if (registerForm.password != registerForm.passwordAgain) {
            return Feedback(
                message = "password don't match",
                feedbackType = FeedbackType.ERROR
            )
        }

        return Feedback(
            "user created!",
            feedbackType = FeedbackType.SUCCESS
        )
    }
}
