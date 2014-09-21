package login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import api.Account;
import api.CsvReader;

public class AccountReader implements CsvReader{
	private BufferedReader bufferedReader;
	
	public AccountReader(String filename) throws Exception {
		bufferedReader = new BufferedReader(new FileReader(filename));
	}

	@Override
	public List<Object> readData() {
		List<Account> accounts = new ArrayList<Account>();
		String currentLine = "";
		String cvsSpliter = ",";

		try {
			while ((currentLine = bufferedReader.readLine()) != null) {
				String[] accountValues = currentLine.split(cvsSpliter);
				
				int number = Integer.parseInt(accountValues[0]);
				int balance = Integer.parseInt(accountValues[1]);
				String owner = accountValues[2];
				
				Account account = new DefaultAccount(number, balance, owner);
				accounts.add(account);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return (List<Object>)(List<?>) accounts;
	}
}
