package universe.account

import universe.CsvSeparator.*
import universe.DataWriter
import java.io.FileWriter
import java.nio.file.Path

class AccountWriter(private val accountFilePath: Path) : DataWriter<Account> {

    @Throws(AccountWriterException::class)
    override fun writeData(account: Account) {
        appendAccountToFile(accountFilePath, account)
    }

}

@Throws(AccountWriterException::class)
override fun overrideAllData(accounts: List<Account>) {
    accounts.forEach { account ->
        appendAccountToFile(account)
    }
}

private fun appendAccountToFile(account: Account) {
    FileWriter(accountFilePath.toFile())
            .use { accountFileWriter ->
                accountFileWriter.append(account.number.toString())
                accountFileWriter.append(COMMA.separator)
                accountFileWriter.append(account.balance.toString())
                accountFileWriter.append(COMMA.separator)
                accountFileWriter.append(account.owner)
                accountFileWriter.append(NEW_LINE.separator)
            }
}