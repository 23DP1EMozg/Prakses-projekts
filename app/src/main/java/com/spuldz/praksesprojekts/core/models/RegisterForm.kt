package com.spuldz.praksesprojekts.core.models

data class RegisterForm(
    val username: String,
    val password: String,
    val passwordAgain: String
)
