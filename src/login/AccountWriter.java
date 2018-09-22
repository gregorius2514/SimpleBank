package login;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import api.Account;
import api.CsvWriter;

public class AccountWriter implements CsvWriter {
	private String filename;
	
	public AccountWriter(String filename) throws Exception {
		this.filename = filename;
	}

	@Override
	public void writeData(Object newAccount) {
		Account account = (Account) newAccount;
		int number = account.getNumber();
		int balance = account.getBalance();
		String owner = account.getOwner();
		
		try {
			write(number, balance, owner);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void write(int number, int balance, String owner) throws Exception {
		FileWriter filewriter = new FileWriter(filename, true);
		
		filewriter.append(Integer.toString(number));
		filewriter.append(",");
		filewriter.append(Integer.toString(balance));
		filewriter.append(",");
		filewriter.append(owner);
		filewriter.append("\n");
		
		filewriter.flush();
		filewriter.close();
	}
	
	public void updateFile(List<Object> oldAccounts) throws Exception {
		List<Account> accounts = (List<Account>) (List<?>) oldAccounts; 
		
		for (Account a : accounts) {
			writeData(a);
		}
	}
}
