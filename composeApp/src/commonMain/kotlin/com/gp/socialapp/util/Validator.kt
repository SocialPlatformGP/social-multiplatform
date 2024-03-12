package com.gp.auth.util

import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime

object Validator {
    object PasswordValidator {
        fun validateLength(password: String) = password.length >= 8
        fun validateChars(password: String) =
            password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).*\$".toRegex())

        fun validateAll(password: String) = validateLength(password) && validateChars(password)
    }

    object EmailValidator {
        fun validateAll(email: String) =
            email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex())
    }
    object PhoneNumberValidator{
        fun validateAll(phoneNumber: String) =
            phoneNumber.matches("^01[0-2]{1}[0-9]{8}\$".toRegex())
    }
    object NameValidator{
        //min age is 17
        fun validateAll(name: String) =
            name.matches("^\\p{L}+\$".toRegex())
    }
    object BirthDateValidator {
        fun validateAll(birthDate: LocalDateTime) =
            birthDate.year <= LocalDateTime.now().year - 17
    }
}