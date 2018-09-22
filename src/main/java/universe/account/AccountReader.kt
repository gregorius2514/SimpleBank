package universe.account

import pl.universe.account.Account
import universe.DataReader
import java.io.BufferedReader
import java.io.FileReader
import java.lang.Integer.parseInt
import java.nio.file.Path
import java.util.*


class AccountReader(val accountsFilePath: Path) : DataReader<Account> {

    companion object {
        private const val ACCOUNT_CSV_COLUMNS_SEPARATOR = ","
    }

    override fun readData(): List<Account> {
        val accounts = ArrayList<Account>()

        BufferedReader(FileReader(accountsFilePath.toFile()))
                .use { accountReader ->
                    {
                        val account = readAccount(accountReader.readLine())
                        accounts.add(account)
                    }
                }

        return accounts
    }

    private fun readAccount(accountRecord: String): Account {
        val (number, balance, owner) = accountRecord.split(ACCOUNT_CSV_COLUMNS_SEPARATOR)

        return Account(parseInt(number), parseInt(balance), owner)
    }

}