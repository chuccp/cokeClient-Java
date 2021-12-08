package com.coke.km;

import com.coke.km.net.KmUri;

public class ClientConfig {
	
	public ClientConfig() {
		
	}
	public ClientConfig(String kmUrl) throws Exception {
		KmUri url = new KmUri(kmUrl);
		this.host = url.getHost();
		this.port = url.getPort();
		this.username = url.getParam("u");
		this.password = url.getParam("p");
		
	}
	
	private String host;
	
	private int port;
	
	private String username;
	
	private String password;
	
	private boolean reconnect = false;
	
	
	

	public boolean isReconnect() {
		return reconnect;
	}

	public void setReconnect(boolean reconnect) {
		this.reconnect = reconnect;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
