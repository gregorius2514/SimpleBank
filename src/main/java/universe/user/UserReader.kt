package universe.user

import com.google.common.base.Preconditions.checkArgument
import universe.CsvSeparator.COMMA
import universe.DataReader
import java.io.FileReader
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserReader(private val usersFilePath: Path, private val birthDateFormater: DateTimeFormatter) : DataReader<User> {

    init {
        checkArgument(usersFilePath.toFile().exists(), "Expected exists users file in path [${usersFilePath.toString()}]");
    }

    override fun readData(): List<User> {
        val users = ArrayList<User>();

        FileReader(usersFilePath.toFile())
                .use { usersReader ->

                    usersReader
                            .readLines()
                            .forEach { line ->
                                val user = readUser(line)
                                users.add(user)
                            }
                }
        return users
    }

    private fun readUser(userRecord: String): User {
        val (login, password, firstname, lastname, birthdate) = userRecord.split(COMMA.separator)
        val userBirthDate = LocalDate.parse(birthdate, birthDateFormater)

        return User(login, password, firstname, lastname, userBirthDate)
    }
}