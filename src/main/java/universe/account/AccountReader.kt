package universe.account

import universe.CsvSeparator
import universe.CsvSeparator.*
import universe.DataReader
import java.io.BufferedReader
import java.io.FileReader
import java.lang.Integer.parseInt
import java.nio.file.Path
import java.util.*


class AccountReader(private val accountsFilePath: Path) : DataReader<Account> {

    override fun readData(): List<Account> {
        val accounts = ArrayList<Account>()

        BufferedReader(FileReader(accountsFilePath.toFile()))
                .use { accountReader ->
                    val account = readAccount(accountReader.readLine())
                    accounts.add(account)
                }
        return accounts
    }

    private fun readAccount(accountRecord: String): Account {
        val (number, balance, owner) = accountRecord.split(COMMA.separator)

        return Account(parseInt(number), parseInt(balance), owner)
    }

}