package com.coke.km;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Message extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3968288008269131453L;
	private JPanel contentPane;
	private JTextField txtKm;
	
	private  Chat chat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Message frame = new Message();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Message() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 653);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 93, 439, 271);
		contentPane.add(scrollPane);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane.setViewportView(textArea_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(24, 396, 439, 121);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		txtKm = new JTextField();
		txtKm.setFont(new Font("宋体", Font.PLAIN, 16));
		txtKm.setText("km://127.0.0.1:6363/?u=2222&p=3333");
		txtKm.setBounds(24, 38, 482, 45);
		contentPane.add(txtKm);
		txtKm.setColumns(10);
		
		JButton btnNewButton = new JButton("连接");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					
					
					Message.this.chat = KmClient.createClient(txtKm.getText()).addMessageListener(new MessageListener() {
						
						@Override
						public void onMessage(com.coke.km.message.Message msg) {
							
							System.out.println(msg.getMessageId());
							
						}
					}).build();
					Message.this.chat.start();
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(530, 50, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("发送信息");
		btnNewButton_1.setBounds(370, 527, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("状态");
		lblNewLabel.setBounds(473, 99, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(473, 136, 190, 15);
		contentPane.add(lblNewLabel_1);
	}
}
