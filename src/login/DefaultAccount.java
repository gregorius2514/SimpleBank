package login;

import api.Account;

public class DefaultAccount extends Account {
	
	public DefaultAccount(int number, int balance, String owner) {
		
		setNumber(number);
		setBalance(balance);
		setOwner(owner);
	}
}
