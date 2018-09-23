package universe.account

import com.google.common.base.Preconditions.checkArgument
import universe.CsvSeparator.COMMA
import universe.CsvSeparator.NEW_LINE
import universe.DataWriter
import java.io.FileWriter
import java.nio.file.Path

class AccountWriter(private val accountsFilePath: Path) : DataWriter<Account> {

    init {
        checkArgument(accountsFilePath.toFile().exists(), "Expected existing accounts file in path [${accountsFilePath.toString()}]")
    }

    @Throws(AccountWriterException::class)
    override fun writeData(account: Account) {
        appendAccountToFile(account)
    }

    @Throws(AccountWriterException::class)
    override fun overrideAllData(accounts: List<Account>) {
        accounts.forEach { account ->
            appendAccountToFile(account)
        }
    }

    private fun appendAccountToFile(account: Account) {
        FileWriter(accountsFilePath.toFile())
                .use { accountFileWriter ->
                    accountFileWriter.append(account.number.toString())
                    accountFileWriter.append(COMMA.separator)
                    accountFileWriter.append(account.balance.toString())
                    accountFileWriter.append(COMMA.separator)
                    accountFileWriter.append(account.owner)
                    accountFileWriter.append(NEW_LINE.separator)
                }
    }
}