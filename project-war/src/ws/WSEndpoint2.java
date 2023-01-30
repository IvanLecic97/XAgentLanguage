package ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Singleton
@LocalBean
@ServerEndpoint(value = "/ws/secondEndpoint/{username}")
public class WSEndpoint2 {

	private static Map<String, Session> sessions = Collections
			.synchronizedMap(new HashMap<String, Session>());
	
	private static Map<String, String> sessionToUser = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	
	 
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
		
		
		public void sendToOne(String username, String message) {
	    	if (sessions.containsKey(username)) {
	    		try {
					sessions.get(username).getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }
		
		public void sendEntities(String entities) {
			for(Session session : sessions.values()) {
				try {
					session.getBasicRemote().sendText(entities);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	
}
