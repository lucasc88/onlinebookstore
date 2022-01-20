package org.onlinebookstore.websockets;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.onlinebookstore.model.Promo;

@ServerEndpoint(value = "/channel/promo")
public class PromoEndpoint {

	@Inject
	private UsersSession usersSession;

	@OnOpen // to open the connection between server and client
	public void onMessage(Session session) {// Session represents the user
		usersSession.add(session);
	}

	/**
	 * Send the message when the user is on the website
	 * 
	 * @param promo
	 */
	public void send(Promo promo) {
		List<Session> users = usersSession.getUsers();
		for (Session session : users) {
			if (session.isOpen()) {
				try {
					session.getBasicRemote().sendText(promo.toJSon());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Close the users sessions
	 * 
	 * @param session
	 * @param closeReason
	 */
	@OnClose//when the customer browser is closed
	public void onClose(Session session, CloseReason closeReason) {
		usersSession.remove(session);
		System.out.println(closeReason.getCloseCode());
	}
}