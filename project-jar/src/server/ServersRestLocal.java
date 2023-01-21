package server;

import model.Node;
import model.User;

public interface ServersRestLocal {
	
	public static String JNDISTRING = "java:global/project-ear/project-jar/ServersRest!server.ServersRestLocal";
	
	public Node getNode();

	public void informNodesAboutLoggedInUsers();
	
	public void informNodesNewUserRegistered(User user);

}
