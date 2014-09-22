package client;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import login.AccountReader;
import login.AccountWriter;
import login.DefaultAccount;
import login.DefaultUser;
import login.UserReader;
import login.UserWriter;
import api.Account;
import api.CsvReader;
import api.CsvWriter;
import api.User;

public class BankFasade {
	private static final String ACCOUNTS = "accounts.csv";
	private static final String USERS = "users.csv";
	private static final int ACCOUNT_EXIST = 1;
	private static final int ACCOUNT_NOT_EXIST = -1;
	private static final int ACCOUNT_HASNT_MONEY = 0;

	private CsvWriter userWriter;
	private CsvReader userReader;
	private CsvWriter accountWriter;
	private CsvReader accountReader;

	private List<User> users;
	private List<Account> accounts;
	private String userLogin;

	public BankFasade() throws Exception {
		userWriter = new UserWriter(USERS);
		userReader = new UserReader(USERS);
		accountWriter = new AccountWriter(ACCOUNTS);
		accountReader = new AccountReader(ACCOUNTS);

		users = readUsers();
		accounts = readAccounts();
	}

	/*
	 * Login user and store login user for further use
	 */
	public boolean login(String login, String password) {
		boolean userExist = false;

		for (User u : users) {
			if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
				userExist = true;
				userLogin = u.getLogin();
				break;
			}
		}
		return userExist;
	}

	public boolean addUser(String login, String password, String firstname,
			String lastname, Date birthdate) {
		boolean userExist = false;

		for (User u : users) {
			if (u.getLogin().equals(login)) {
				userExist = true;
				break;
			}
		}

		if (!userExist) {
			User user = new DefaultUser(login, password, firstname, lastname,
					birthdate);
			userWriter.writeData(user);
		}
		return userExist;
	}

	public boolean addAccount(int number, int balance) {
		boolean accountExist = false;

		for (Account a : accounts) {
			if (a.getNumber() == number)
				accountExist = true;
		}

		if (!accountExist) {
			Account account = new DefaultAccount(number, balance, userLogin);
			accountWriter.writeData(account);
		}
		return accountExist;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public int transferMoney(int accountNumber, int countOfMoney)
			throws Exception {

		int accountExist = ACCOUNT_NOT_EXIST;
		List<Account> userAccounts = findUserAccounts();
		Account destinationAccount = findAccount(accountNumber);

		if (userAccounts.size() > 0 && destinationAccount != null) {
			Account userAccount = userAccounts.get(0);
			int userBalance = userAccount.getBalance();

			if (userBalance == 0)
				return ACCOUNT_HASNT_MONEY;
			if (userBalance < countOfMoney)
				countOfMoney = userBalance;

			int updatedUserBalance = userBalance - countOfMoney;
			userAccount.setBalance(updatedUserBalance);
	
			int currentAccoutBalance = destinationAccount.getBalance();
			currentAccoutBalance = currentAccoutBalance + countOfMoney;
			
			updateAccountBalance(userAccount, updatedUserBalance);
			updateAccountBalance(destinationAccount, currentAccoutBalance);
			accountExist = ACCOUNT_EXIST;
		}
		return accountExist;
	}
	
	private void updateAccountBalance(Account destinationAccount, int newBalance) throws Exception {
		accounts.remove(destinationAccount);
		cleanAccountsFile();
		destinationAccount.setBalance(newBalance);
		accounts.add(destinationAccount);
		accountWriter.updateFile((List<Object>) (List<?>) accounts);
		
	}

	private Account findAccount(int accountNumber) {
		Account searchAccount = null;

		for (Account a : accounts) {
			if (a.getNumber() == accountNumber) {
				searchAccount = a;
				break;
			}
		}
		return searchAccount;
	}

	private List<Account> findUserAccounts() {
		List<Account> userAccounts = new ArrayList<Account>();

		for (Account a : accounts) {
			if (a.getOwner().equals(userLogin)) {
				userAccounts.add(a);
			}
		}
		return userAccounts;
	}

	private void cleanAccountsFile() {
		try {
			FileWriter fw = new FileWriter(ACCOUNTS);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<User> readUsers() {
		return (List<User>) (List<?>) userReader.readData();
	}

	private List<Account> readAccounts() {
		return (List<Account>) (List<?>) accountReader.readData();
	}
}
