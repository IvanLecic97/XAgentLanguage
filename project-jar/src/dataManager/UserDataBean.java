package dataManager;

import java.util.HashMap;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.User;


@Singleton
@LocalBean
@Startup
public class UserDataBean {
	
	private HashMap<String, User> registeredUsers;
	private HashMap<String, User> loggedInUsers;
	
	public UserDataBean() {
		this.registeredUsers = new HashMap<String, User>();
		this.loggedInUsers = new HashMap<String, User>();
	}

	public HashMap<String, User> getRegisteredUsers() {
		return registeredUsers;
	}

	public void setRegisteredUsers(HashMap<String, User> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}

	public HashMap<String, User> getLoggedInUsers() {
		return loggedInUsers;
	}

	public void setLoggedInUsers(HashMap<String, User> loggedInUsers) {
		this.loggedInUsers = loggedInUsers;
	}
	
	
	

}