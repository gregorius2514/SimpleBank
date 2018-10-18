package universe.user

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class User(
        val login: String,
        val password: String,
        val firstname: String,
        val lastname: String,
        val birthdate: LocalDate) {

    fun getBirthdateAsFormattedString(
            birthdateFormatter: DateTimeFormatter)
            : String {
        return birthdate.format(birthdateFormatter)
    }
}
