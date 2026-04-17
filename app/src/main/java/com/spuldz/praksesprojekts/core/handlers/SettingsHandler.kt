package com.spuldz.praksesprojekts.core.handlers

class SettingsHandler {

    fun validateNumberInput(newValue: String, oldValue: Int, maxLength: Int) : Int{
        var valueCopy = if (newValue == "") "0" else newValue
        if (valueCopy.all { !it.isDigit() }) return oldValue

        if (oldValue == 0) {
            valueCopy = valueCopy.replace("0", "")
        }
        val num = valueCopy.toInt()

        if (num > maxLength) {
            return oldValue
        }

        return num
    }
}
