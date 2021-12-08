package com.coke.km.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

	private String host;
	private int port;

	public SocketClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	private InputStream ips;

	private OutputStream ops;

	public void start() throws UnknownHostException, IOException {
		@SuppressWarnings("resource")
		Socket socket = new Socket(host, port);
		this.ips = socket.getInputStream();
		this.ops = socket.getOutputStream();
	}

	public int read() throws IOException {
		return this.ips.read();
	}

	public byte[] readBytes(int num) throws IOException {
		byte[] odata = new byte[num];
		int size = num;
		int start = 0;
		while (true) {
			byte[] data = new byte[size];
			int has = this.ips.read(data);
			System.arraycopy(data, 0, odata, start, has);
			size = size - has;
			if (size == 0) {
				break;
			}
			start = start + has;
		}
		return odata;
	}
	
	public void write(byte[] data) throws IOException {
		this.ops.write(data);
	}
	
	public void write(int data) throws IOException  {
		this.ops.write(data);
	}
	
	
	public void close() {
		
	}

}
