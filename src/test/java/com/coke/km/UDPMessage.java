package com.coke.km;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UDPMessage {

	private JFrame frame;
	private JTextField hostText;
	private JTextField textPort;
	
	private DatagramSocket socket;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UDPMessage window = new UDPMessage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UDPMessage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 747, 716);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		hostText = new JTextField();
		hostText.setText("127.0.0.1");
		hostText.setBounds(118, 25, 353, 21);
		frame.getContentPane().add(hostText);
		hostText.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("服务器地址");
		lblNewLabel.setBounds(27, 28, 81, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("端口号");
		lblNewLabel_1.setBounds(26, 69, 54, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		textPort = new JTextField();
		textPort.setText("7676");
		textPort.setBounds(118, 66, 141, 21);
		frame.getContentPane().add(textPort);
		textPort.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 377, 514, 232);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton = new JButton("发送");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textArea.getText();
				byte[] data = text.getBytes();
				String tp = textPort.getText();
				
				try {
					InetAddress address = InetAddress.getByName(hostText.getText().trim());
					
					DatagramPacket packet = new DatagramPacket(data, data.length,address,Integer.valueOf(tp.trim()));
					if(socket!=null) {
						socket.send(packet);
					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(448, 631, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("建立连接");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					socket = new DatagramSocket(5862);
				} catch (SocketException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(331, 65, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
	}
}
