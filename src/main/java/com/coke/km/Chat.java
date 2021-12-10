package com.coke.km;

import com.coke.km.stream.KmStream;

public class Chat {
	
	
	private KmStream kmStream;
	
	public Chat(ClientConfig clientConfig) {
		this.kmStream = new KmStream(clientConfig);
	}

	
	public void sendMessage(String to,String msg) {
		
		this.kmStream.sendMessage(to, msg);
		
		
	}
	
	public void start() {
		this.conn();
	}
	private void conn() {
		
		this.kmStream.readMessage();
		
	}
	
}
