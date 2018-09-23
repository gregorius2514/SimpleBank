package universe.account

import com.google.common.base.Preconditions.checkArgument
import universe.CsvSeparator.COMMA
import universe.DataReader
import java.io.FileReader
import java.lang.Integer.parseInt
import java.nio.file.Path
import java.util.*


class AccountReader(private val accountsFilePath: Path) : DataReader<Account> {

    init {
        checkArgument(accountsFilePath.toFile().exists(), "Expected existing accounts file in path [${accountsFilePath.toString()}]")
    }

    override fun readData(): List<Account> {
        val accounts = ArrayList<Account>()

        FileReader(accountsFilePath.toFile())
                .use { accountReader ->
                    accountReader
                            .readLines()
                            .forEach { line ->
                                val account = readAccount(line)
                                accounts.add(account)
                            }
                }
        return accounts
    }

    private fun readAccount(accountRecord: String): Account {
        val (number, balance, owner) = accountRecord.split(COMMA.separator)

        return Account(parseInt(number), parseInt(balance), owner)
    }

}