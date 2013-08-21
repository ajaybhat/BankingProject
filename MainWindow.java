package com.banking;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	Container cn = null;
	JButton bAdmin, bUser;
	JLabel title;

	public MainWindow() {
		cn = getContentPane();
		cn.setLayout(null);
		title = new JLabel("BANK", JLabel.CENTER);
		title.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));

		bAdmin = new JButton("Admin Login");
		bUser = new JButton("Customer Login");

		bAdmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginScreen(1);

			}
		});
		bUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginScreen(0);

			}
		});

		title.setBounds(0, 20, 300, 30);
		bAdmin.setBounds(50, 100, 200, 30);
		bUser.setBounds(50, 150, 200, 30);

		cn.add(bAdmin);
		cn.add(bUser);
		cn.add(title);

		setBounds(450, 200, 300, 300);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {

		new MainWindow();
	}
}
