package rest;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import dataManager.UserDataBean;
import model.User;

@Stateless
@LocalBean
@Path("/chat")
public class RestBeanRemote implements RestBean {
	
	@EJB
	private UserDataBean usersData;

	@Override
	public String register(User user) {
		if(usersData.getRegisteredUsers().containsKey(user.getUsername())) {
			return "That username already exists, pick a new one!";
		}
		else {
		User newUser = new User(user.getUsername(), user.getPassword());
		usersData.getRegisteredUsers().put(newUser.getUsername(), newUser);
		return "Registered";
		}
	}

	@Override
	public List<String> getRegisteredUsers() {
		// TODO Auto-generated method stub
		List<String> retVal = new ArrayList<>();
		for(String s : usersData.getRegisteredUsers().keySet()) {
			retVal.add(s);
		}
		return retVal;
	}

	@Override
	public String login(User user) {
		// TODO Auto-generated method stub
		User loggedUser = usersData.getRegisteredUsers().get(user.getUsername());
		if(loggedUser != null && loggedUser.getPassword().equals(user.getPassword())) {
			usersData.getLoggedInUsers().put(loggedUser.getUsername(), loggedUser);
			return "User successfully logged in!";
		}
		if(loggedUser == null) {
			return "That user is not yet registered, first go and register!";
		}
		else return "Wrong password, please try again!";
	}

	@Override
	public List<String> getLoggedInUsers() {
		// TODO Auto-generated method stub
		List<String> retVal = new ArrayList<>();
		for(String s : usersData.getLoggedInUsers().keySet()) {
			retVal.add(s);
		}
		return retVal;
	}
	
	
	

}
