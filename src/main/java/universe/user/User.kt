package universe.user

import java.time.LocalDate

data class User(
        val login: String,
        val password: String,
        val firstname: String,
        val lastname: String,
        val birthdate: LocalDate)
