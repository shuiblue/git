package common;

import server.Connection;

public interface CallBack {
	public void call (Connection conn, TextMessage text);
	public void remove (Connection connection);
}
