package login;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import api.CsvWriter;
import api.User;

public class UserWriter implements CsvWriter {
	private FileWriter filewriter;
	
	public UserWriter(String filename) throws Exception {
		filewriter = new FileWriter(filename, true);
	}

	@Override
	public void writeData(Object newUser) {
		User user = (User) newUser;
		String login = user.getLogin();
		String password = user.getPassword();
		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		Date birthdate = user.getBirthdate();
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		String formatedDate = dateformat.format(birthdate);
		
		try {
			write(login, password, firstname, lastname, formatedDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void write(
			String login,
			String password,
			String firstname,
			String lastname,
			String birthdate
			) throws Exception {
	
		filewriter.append(login);
		filewriter.append(",");
		filewriter.append(password);
		filewriter.append(",");
		filewriter.append(firstname);
		filewriter.append(",");
		filewriter.append(lastname);
		filewriter.append(",");
		filewriter.append(birthdate);
		filewriter.append("\n");
		
		filewriter.flush();
		filewriter.close();
	}

	@Override
	public void updateFile(List<Object> accounts) throws Exception {}

}
