package universe.bank

import io.vavr.control.Either
import sun.reflect.generics.reflectiveObjects.NotImplementedException
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
                    if (user.login == newUser.login) {
                        throw UserExistsException("User with login [${newUser.login}] already exists")
                    }
                }
        userWriter.writeData(newUser)

        //TODO return newUser when writing to file is redundant but in other scenarios is good
        // practice therefor We should use good practice convenion and return new added user
        return newUser
    }

    fun addAccount(newAccount: Account): Account {
        accounts
                .forEach { account ->
                    if (account.number == newAccount.number) {
                        throw AccountExistsException("Account ${newAccount.number} already exists")
                    }
                }
        accountWriter.writeData(newAccount)

        return newAccount
    }

    fun transferMoney(accountNumber: Int, moneyToTransfer: Int): Either<String, String> {

        val loggedUserAccount = getLoggedUserAccount()
        val destinationAccount = findDestinationAccount(accountNumber)

        if (loggedUserAccount.balance == 0) {
            return Either.left("Logged user [${loggedUserAccount.owner}] hasn't money. ")
        } else if (loggedUserAccount.balance < moneyToTransfer) {
            val transferedMoney = transferMoneyFromUserAccount(loggedUserAccount, destinationAccount, loggedUserAccount
                    .balance)
            return Either.right("Transfered all user's [${loggedUserAccount.owner}] money [${transferedMoney}]")
        }
        val transferedMoney = transferMoneyFromUserAccount(loggedUserAccount, destinationAccount, moneyToTransfer)
        return Either.right("Transfered money [${transferedMoney}] from account [${loggedUserAccount.number}] to account [${destinationAccount.number}]")
    }

    private fun transferMoneyFromUserAccount(loggedUserAccount: Account, destinationAccount: Account, moneyToTransfer: Int): Int {
        val loggedUserMoney = loggedUserAccount.balance;

        loggedUserAccount.balance = loggedUserAccount.balance - moneyToTransfer
        destinationAccount.balance = destinationAccount.balance + moneyToTransfer

        return (loggedUserAccount.balance - loggedUserMoney) * -1;
    }

    private fun findDestinationAccount(accountNumber: Int): Account {
        throw NotImplementedException()
    }

    private fun getLoggedUserAccount(): Account {
        throw NotImplementedException()
    }
}
