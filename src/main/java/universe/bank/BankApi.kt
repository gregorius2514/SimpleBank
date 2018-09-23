package universe.bank

import universe.account.Account
import universe.account.AccountReader
import universe.account.AccountWriter
import universe.user.User
import universe.user.UserReader
import universe.user.UserWriter
import java.time.format.DateTimeFormatter

//TODO Remove Api from class name
//FIXME Too many arguments
class BankApi(private val userWriter: UserWriter, private val userReader: UserReader, private val accountWriter: AccountWriter, private val accountReader: AccountReader) {

    companion object {
        private val userBirthdateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        private const val ACCOUNTS_FILE_NAME = "accounts.csv"
        private const val USERS_FILE_NAME = "users.csv"
    }

    val users: List<User>
    val accounts: List<Account>
    var userLogin = ""

    init {
        users = ArrayList<User>()
        accounts = ArrayList<Account>()
    }

    fun login(login: String, password: String): User {
        users
                .forEach { user ->
                    if (user.login.equals(login) && user.password.equals(password)) {
                        return user
                    }
                }
        throw UserNotFoundException("Invalid login or password for user login [${login}]")
    }

    fun addUser(newUser: User): User {
        users
                .forEach { user ->
                    if (user.login.equals(newUser.login)) {
                        throw UserExistsException("User with login [${newUser.login}] already exists")
                    }
                }
        userWriter.writeData(newUser)

        //TODO retur newUser when writing to file is redundant but in other scenarios is good practice therefor We should use good practice convenion and return new added user
        return newUser
    }

    fun addAccount(account: Account): Account {
        accounts
                .forEach {  }
    }
}

//TODO Change name
enum class AccountsMarkers {
    ACCOUNT_EXISTS,
    ACCOUNT_NOT_EXISTS,
    ACCOUNT_HASNT_MONEY
}
