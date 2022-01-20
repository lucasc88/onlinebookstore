package org.onlinebookstore.websockets;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class UsersSession {

	//list of users sessions
	private List<Session> sessions = new ArrayList<>();
	
    public void add(Session session) {
        sessions.add(session);
    }

    public List<Session> getUsers() {
        return sessions;
    }

	public void remove(Session session) {
		sessions.remove(session);
	}
}
