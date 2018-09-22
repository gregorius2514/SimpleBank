package pl.universe.account

import com.google.common.base.Preconditions
import com.google.common.base.Preconditions.checkArgument
import com.google.common.base.Strings
import com.google.common.base.Strings.isNullOrEmpty
import java.util.Objects.nonNull

data class Account(val number: Int, val balance: Int, val owner: String)