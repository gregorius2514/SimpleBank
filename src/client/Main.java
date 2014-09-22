package client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import api.Account;
import api.User;

public class Main {
	private BankFasade bankApi;
	private Scanner input;
	private String command;
	private String login;
	private boolean loged = false;
	private boolean clientRunning = true;

	public Main() throws Exception {
		bankApi = new BankFasade();
		input = new Scanner(System.in);
		command = "";
		login = "";
	}

	public void run() throws Exception {
		while (clientRunning) {
			printMenu();
			command = input.nextLine();
			checkOptions(command);
		}
	}

	private void printMenu() {
		System.out.println("1 - Login");
		System.out.println("2 - Add user");
		System.out.println("3 - Add account");
		System.out.println("4 - Show users");
		System.out.println("5 - Show accounts");
		System.out.println("6 - Transfer money");
		System.out.println("9 - Logout");
		System.out.println("0 - Exit");
	}

	private void checkOptions(String option) throws Exception {
		if (command.equals("0")) {
			System.out.println("System Quit");
			clientRunning = false;
		} else if (command.equals("1") && !loged) {
			login();
		} else if (command.equals("9") && loged) {
			logout();
		} else if (command.equals("2") && loged) {
			addUser();
		} else if (command.equals("3") && loged) {
			addAccount();

		} else if (command.equals("4") && loged) {
			printUsers();
		} else if (command.equals("5") && loged) {
			printAccounts();
		} else if (loged && command.equals("6")) {
			doTransfer();
		} else if (!loged
				&& (command.equals("4") || command.equals("5") || command
						.equals("6"))) {
			System.out.println("You do not have access");
		} else if (loged && command.equals("1")) {
			System.out.println("You're already loged in");
		}
	}

	private void doTransfer() throws NumberFormatException, Exception {
		System.out.print("Account number: ");
		String accountNumber = input.nextLine();

		System.out.print("How much: ");
		String countOfMoney = input.nextLine();

		int transferDone = bankApi
				.transferMoney(Integer.parseInt(accountNumber),
						Integer.parseInt(countOfMoney));
		if (transferDone == 1)
			System.out.println("Transaction successed");
		else if (transferDone == -1)
			System.out.println("Wrong account number");
		else if (transferDone == 0)
			System.out.println("You don't have money");
		else
			System.out.println("Transaction error");
	}

	private void printAccounts() {
		for (Account a : bankApi.getAccounts()) {
			System.out.println("-----------");
			System.out.println("Account number:  " + a.getNumber());
			System.out.println("Account balance: " + a.getBalance());
			System.out.println("Account owner:   " + a.getOwner());
		}
	}

	private void printUsers() {
		for (User u : bankApi.getUsers()) {
			System.out.println("-----------");
			System.out.println("Login:     " + u.getLogin());
			System.out.println("Password:  " + u.getPassword());
			System.out.println("Firstname: " + u.getFirstname());
			System.out.println("Lastname:  " + u.getLastname());
			System.out.println("Birthdate: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(u
							.getBirthdate()));
		}
	}

	private void addAccount() {
		System.out.print("Account number: ");
		String number = input.nextLine();

		System.out.print("Account balance: ");
		String balance = input.nextLine();

		boolean accountExist = bankApi.addAccount(Integer.parseInt(number),
				Integer.parseInt(balance));
		if (accountExist)
			System.out.println("Account with this number already exist");
		else
			System.out.println("Account added");
	}

	private void addUser() {
		System.out.print("Login: ");
		String newLogin = input.nextLine();

		System.out.print("Password: ");
		String newPassword = input.nextLine();

		System.out.print("Firstname: ");
		String firstname = input.nextLine();

		System.out.print("Lastname: ");
		String lastname = input.nextLine();

		System.out.print("Birthday (day/month/year): ");
		String birthday = input.nextLine();
		String[] date = birthday.split("/");
		int year = Integer.parseInt(date[2]);
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[0]);

		Date newDate = new Date(year, month, day);
		boolean userExist = bankApi.addUser(newLogin, newPassword, firstname,
				lastname, newDate);
		if (userExist)
			System.out.println("User with this login already exist");
		else
			System.out.println("User was added");
	}

	private void logout() {
		loged = false;
		System.out.println("Successfully logout");
	}

	private void login() {
		System.out.print("Login: ");
		login = input.nextLine();

		System.out.print("Password: ");
		String password = input.nextLine();

		loged = bankApi.login(login, password);
		if (loged)
			System.out.println("You're loged in");
		else
			System.out.println("Wrong login or password");
	}

	public static void main(String[] args) throws Exception {
		Main consoleClient = new Main();
		consoleClient.run();
	}
}
