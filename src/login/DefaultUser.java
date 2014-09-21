package login;

import java.util.Date;

import api.User;

public class DefaultUser extends User{
	public DefaultUser(
			String login,     String password,
			String firstname, String lastname,
			Date   birthdate
			) {
		
		setLogin(login);
		setPassword(password);
		setFirstname(firstname);
		setLastname(lastname);
		setBirthdate(birthdate);
	}
}
