package universe.bank

import io.vavr.API.printf
import universe.bank.MenuOption.*
import universe.user.User
import java.util.*

class BankApp(val bankApi: BankApi, val keyboardInput: Scanner) {

    var appRunning = true
    var userLogedin = false
    lateinit var logedinUser: User

    fun applicationLoop(appRunning: Boolean) {
        while (appRunning) {
            printUserMenu()

            val userChoiceCommand = readUserMenuChoice(keyboardInput)
            selectUserMenu(userChoiceCommand)
        }
    }

    private fun printUserMenu() {
        System.out.println("1 - Login")
        System.out.println("2 - Add user")
        System.out.println("3 - Add account")
        System.out.println("4 - Show users")
        System.out.println("5 - Show accounts")
        System.out.println("6 - Transfer money")
        System.out.println("9 - Logout")
        System.out.println("0 - Exit")
    }

    private fun readUserMenuChoice(keyboardInput: Scanner): MenuOption {
        return when (keyboardInput.nextLine()) {
            "1" -> LOGIN
            "2" -> ADD_USER
            "3" -> ADD_ACCOUNT
            "4" -> SHOW_USERS
            "5" -> SHOW_ACCOUNTS
            "6" -> TRANSFER_MONEY
            "9" -> LOGOUT
            "0" -> EXIT
            else -> UNKNOWN

        }
    }

    private fun selectUserMenu(userChoiceCommand: MenuOption) {
        when (userChoiceCommand) {
            ADD_ACCOUNT -> {
                addAccount()
            }
            ADD_USER -> {
                addUser()
            }
            EXIT -> {
                println("App shutdown")
                userLogedin = false
                appRunning = false
            }
            LOGIN -> {
                loginUser()
            }
            LOGOUT -> {
                userLogedin = false
                println("User [${logedinUser.login}] successfully logout")
            }
            SHOW_ACCOUNTS -> {
                showAccounts()
            }
            SHOW_USERS -> {
                showUsers()
            }
            TRANSFER_MONEY -> {
                transferMoney()
            }
            UNKNOWN -> println("Unknown user choice")
        }

    }

    private fun transferMoney() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showUsers() {
        // todo `ADMIN` user can print all users details but login user can print only own details
    }

    private fun showAccounts() {
        // todo `ADMIN` user can print all account but login user can print only own account details
    }

    private fun loginUser() {
        printf("User name or login: ")
        var userLogin = keyboardInput.nextLine()

        printf("User password: ")
        val userPassword = keyboardInput.nextLine()

        try {
            logedinUser = bankApi.login(userLogin, userPassword)
            userLogedin = true

            printf("Successfully login")
        } catch (userNotFoundException: UserNotFoundException) {
            print("DEBUG Error occurre when user try to login [$userLogin], [${userNotFoundException.message}]. ")
            printf("User [$userLogin] not found. Incorrect login or password.")
        }
    }

    private fun addUser() {
        // todo only `ADMIN` user can add new user to system

    }

    private fun addAccount() {
        // todo only `ADMIN` user can add new accounts to system
    }
}

enum class MenuOption {
    ADD_ACCOUNT,
    ADD_USER,
    EXIT,
    LOGIN,
    LOGOUT,
    SHOW_ACCOUNTS,
    SHOW_USERS,
    TRANSFER_MONEY,
    UNKNOWN
}

fun main(args: Array<String>) {
    val bankApi = BankApi()
    val keyboardInput = Scanner(System.in)

    val bankApp = BankApp(bankApi, keyboardInput)
}

