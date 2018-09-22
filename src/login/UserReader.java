package login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.CsvReader;
import api.User;

public class UserReader implements CsvReader {
	private BufferedReader bufferedReader;

	public UserReader(String filename) throws Exception {
		bufferedReader = new BufferedReader(new FileReader(filename));
	}

	@Override
	public List<Object> readData() {
		List<User> users = new ArrayList<User>();
		String currentLine = "";
		String cvsSpliter = ",";

		try {
			while ((currentLine = bufferedReader.readLine()) != null) {
				String[] userValues = currentLine.split(cvsSpliter);
				String[] userBirthdate = userValues[4].split("/");
				
				int year = Integer.parseInt(userBirthdate[2]);
				int month = Integer.parseInt(userBirthdate[1]);
				int day = Integer.parseInt(userBirthdate[0]);
				
				Date birthdate = new Date(year, month, day);
				String login = userValues[0];
				String password = userValues[1];
				String firstname = userValues[2];
				String lastname = userValues[3];
				
				User user = new DefaultUser(login, password, firstname, lastname, birthdate);
				users.add(user);
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
		return (List<Object>)(List<?>) users;
	}

}
