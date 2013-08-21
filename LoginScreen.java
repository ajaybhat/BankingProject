package com.banking;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginScreen extends JFrame {
	Container cn = null;
	JLabel lus, lpwd;
	JButton ok, cancel;
	JTextField username;
	JPasswordField password;
	JLabel title;

	public LoginScreen(final int toggle) {
		cn = getContentPane();
		cn.setLayout(null);
		lus = new JLabel();
		lpwd = new JLabel("Password");
		username = new JTextField("");
		password = new JPasswordField("");
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		if (toggle == 1) {

			title = new JLabel("Admin Login", JLabel.CENTER);
			lus.setText("Username");
		} else {

			title = new JLabel("Customer Login", JLabel.CENTER);
			lus.setText("Account No");
		}
		title.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));

		ok.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = null;
				String user = username.getText();
				try {
					if (toggle == 1)
						rs = query("select * from adminpass where username='"
								+ user + "'");
					else
						rs = query("select * from customer where accno=" + user);
					rs.next();
					String pass = rs.getString("password");
					if (pass.equals(password.getText())){
						dispose();
						if (toggle == 1)
							new MainAdminWindow(user);
						else
							new MainUserWindow(Integer.parseInt(user));
					} else
						JOptionPane.showMessageDialog(null,
								"Wrong password entered or User doesn't exist",
								"", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e1) {
System.out.println("SQL Connection prob");
				}

			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new MainWindow();

			}
		});

		title.setBounds(0, 0, 400, 50);
		lus.setBounds(75, 70, 100, 30);
		lpwd.setBounds(75, 110, 100, 30);
		username.setBounds(160, 70, 200, 30);
		password.setBounds(160, 110, 200, 30);
		ok.setBounds(120, 160, 75, 30);
		cancel.setBounds(215, 160, 75, 30);

		cn.add(lus);
		cn.add(lpwd);
		cn.add(username);
		cn.add(password);
		cn.add(title);
		cn.add(ok);
		cn.add(cancel);

		setBounds(400, 200, 400, 250);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}

	private ResultSet query(String string) {

		Connection con = null;
		Statement st;
		ResultSet r = null;
		try {
			String driverName = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driverName);
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@127.0.0.1:1521:XE", "ajay", "password");
			st = con.createStatement();
			r = st.executeQuery(string);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return r;
	}

}