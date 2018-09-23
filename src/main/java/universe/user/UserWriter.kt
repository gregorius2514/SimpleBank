package universe.user

import com.google.common.base.Preconditions.checkArgument
import universe.CsvSeparator.COMMA
import universe.CsvSeparator.NEW_LINE
import java.io.FileWriter
import java.nio.file.Path
import java.time.format.DateTimeFormatter

class UserWriter(private val usersFilePath: Path, private val birthdateFormatter: DateTimeFormatter) : universe.DataWriter<User> {
    init {
        checkArgument(usersFilePath.toFile().exists(), "Expected existing users file in path [${usersFilePath.toString()}]")
    }

    override fun writeData(user: User) {
        appendUserToFile(user)
    }

    override fun overrideAllData(users: List<User>) {
        users
                .forEach { user ->
                    appendUserToFile(user)
                }
    }

    private fun appendUserToFile(user: User) {
        FileWriter(usersFilePath.toFile())
                .use { usersWriter ->

                    usersWriter.append(user.login)
                    usersWriter.append(COMMA.separator)
                    usersWriter.append(user.password)
                    usersWriter.append(COMMA.separator)
                    usersWriter.append(user.firstname)
                    usersWriter.append(COMMA.separator)
                    usersWriter.append(user.lastname)
                    usersWriter.append(COMMA.separator)
                    usersWriter.append(user.getBirthdateAsFormattedString(birthdateFormatter))
                    usersWriter.append(NEW_LINE.separator)

                }

    }
}