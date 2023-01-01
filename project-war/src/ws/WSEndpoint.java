package ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;


@Singleton
@LocalBean
@ServerEndpoint(value = "/ws/{username}")
public class WSEndpoint {

	private static Map<String, Session> sessions = Collections
			.synchronizedMap(new HashMap<String, Session>());
	
	private static Map<String, String> sessionToUser = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	
	 public void updateLoggedInUsers(String users) {
	    	for (Session session: sessions.values()) {
	    		try {
					session.getBasicRemote().sendText(users);
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }
	 
	 @OnOpen
		public void onOpen(Session session, @PathParam("username") String username) throws IOException {
			sessions.put(username, session);
	    	sessionToUser.put(session.getId(), username);
		}
	 
	 @OnClose
		public void close(Session session) throws IOException {
			String username = sessionToUser.get(session.getId());
			sessions.remove(username);
			session.close();
		}
		
		@OnError
		public void error(Session session, Throwable t) throws IOException {
			String username = sessionToUser.get(session.getId());
			sessions.remove(username);
			session.close();
			t.printStackTrace();
		}
	 
		
		//Send message to all users
		public void broadcast(String message) {
	    	for (Session session: sessions.values()) {
	    		try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }
		
		//Send message to one user
		public void sendToOne(String username, String message) {
	    	if (sessions.containsKey(username)) {
	    		try {
					sessions.get(username).getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }
		
		
		//Update messages list
		public void sendMessages(List<?> messages) {
			ObjectMapper object = new ObjectMapper();
		    for (Session session : sessions.values()) {
		        try {
		            session.getBasicRemote().sendText(object.writeValueAsString(messages));
		        } catch (IOException ex) {
		            // handle error
		        }
		    }
		}
		
		
		
		
		
	 
	
	
}
