package com.banking;

import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class MainAdminWindow extends JFrame {

	JLabel header, footer;
	JTabbedPane tpane;
	Container cn = null;
	JFrame accframe;
	static String username;
	JButton bPass, bLogout;

	public MainAdminWindow(final String u) {
		cn = getContentPane();
		cn.setLayout(null);
		username = u;
		header = new JLabel("ACCOUNTANT", JLabel.CENTER);
		footer = new JLabel("Logged in as : " + username, JLabel.CENTER);
		tpane = new JTabbedPane();
		accframe = new JFrame();
		bPass = new JButton("Change Password");
		bLogout = new JButton("Logout");

		header.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));

		tpane.addTab("Customer", custtab());
		tpane.addTab("Transaction", transtab());

		bPass.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				JPasswordField oldpass = new JPasswordField(10);
				panel.add(new JLabel("Enter current password"));
				panel.add(oldpass);
				JOptionPane.showConfirmDialog(null, panel,
						"", JOptionPane.PLAIN_MESSAGE);

				panel.removeAll();
				ResultSet rs = null;
				if (oldpass.getText().trim().length() == 0
						|| oldpass.getText() == null)
					;
				else {

					try {
						rs = query("select * from adminpass where username='"
								+ username + "'");
						rs.next();
						String password = rs.getString("password");
						password.equals(oldpass.getText());
						if (password.equals(oldpass.getText())) {
							JPasswordField jt1 = new JPasswordField(10), jt2 = new JPasswordField(
									10);

							panel.add(new JLabel("Enter new password"));
							panel.add(jt1);

							panel.add(new JLabel("Re-enter new password"));
							panel.add(jt2);
							JOptionPane.showConfirmDialog(null, panel, "",
									JOptionPane.PLAIN_MESSAGE);
							if (jt1.getText().equals(jt2.getText())) {
								rs = query("update adminpass set password = '"
										+ jt1.getText() + "' where username='"
										+ username + "'");
								JOptionPane.showMessageDialog(null,
										"Password updated", "",
										JOptionPane.PLAIN_MESSAGE);

							} else {
								JOptionPane.showMessageDialog(null,
										"Passwords don't match");
							}
						} else
							JOptionPane.showMessageDialog(null,
									"Wrong password entered");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		bLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
				new MainWindow();

			}
		});
		header.setBounds(0, 0, 400, 30);
		footer.setBounds(0, 330, 150, 30);
		tpane.setBounds(0, 30, 400, 300);
		bPass.setBounds(150, 330, 150, 30);
		bLogout.setBounds(300, 330, 100, 30);

		cn.add(header);
		cn.add(footer);
		cn.add(tpane);
		cn.add(bPass);
		cn.add(bLogout);

		setTitle("Accountant");
		setVisible(true);
		setBounds(400, 150, 400, 390);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private JPanel custtab() {
		JPanel cust = new JPanel();
		cust.setLayout(null);
		JButton newcust, edcust, delcust, viewcust;
		newcust = new JButton("New Customer");
		edcust = new JButton("Edit Customer");
		delcust = new JButton("Delete Customer");
		viewcust = new JButton("View all Customers");

		newcust.setBounds(100, 45, 200, 30);
		edcust.setBounds(100, 85, 200, 30);
		delcust.setBounds(100, 125, 200, 30);
		viewcust.setBounds(100, 165, 200, 30);

		newcust.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				setVisible(false);
				new NewCustomer();
			}
		});
		edcust.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				setVisible(false);

				String accno = getaccno();
				if (accno == null || accno.trim().length() == 0)
					setVisible(true);
				else {
					ResultSet rs = query("select * from customer where accno = "
							+ accno);
					try {
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null,
									"Account does not exist with this No.", "",
									JOptionPane.ERROR_MESSAGE);
							setVisible(true);
						} else
							new EditCustomer(Integer.parseInt(accno), 0);

					} catch (HeadlessException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});
		delcust.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				setVisible(false);

				String accno = getaccno();
				if (accno == null || accno.trim().length() == 0)
					setVisible(true);
				else {

					try {
						ResultSet rs = query("select * from customer where accno = "
								+ accno);
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null,
									"Account does not exist with this No.", "",
									JOptionPane.ERROR_MESSAGE);
						} else {
							query("delete from customer where accno = " + accno);

							JOptionPane.showMessageDialog(null, "Account : "
									+ accno + " deleted");

						}
						setVisible(true);

					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		viewcust.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				setVisible(false);
				new ViewFrame().viewFrame(0, 0, 1,username);

			}
		});

		cust.add(newcust);
		cust.add(edcust);
		cust.add(delcust);
		cust.add(viewcust);
		return cust;

	}

	private JPanel transtab() {
		JPanel trans = new JPanel();
		trans.setLayout(null);
		JButton newtrans, viewtrans;
		newtrans = new JButton("New Transaction");
		viewtrans = new JButton("View Transactions");

		newtrans.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				setVisible(false);

				String accno = getaccno();
				if (accno == null || accno.trim().length() == 0)
					setVisible(true);
				else {
					ResultSet rs = query("select * from customer where accno = "
							+ accno);
					try {
						if (!rs.next()) {
							JOptionPane.showMessageDialog(null,
									"Account does not exist with this No.", "",
									JOptionPane.ERROR_MESSAGE);
							setVisible(true);
						} else
							new EditCustomer(Integer.parseInt(accno), 1);

					} catch (HeadlessException | SQLException e) {
						e.printStackTrace();
					}

				}

			}
		});

		viewtrans.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				String accno = getaccno();
				if (accno == null || accno.trim().length() == 0)
					setVisible(true);
				else {
					try {
						ResultSet rs = query("select * from customer where accno = "
								+ accno);

						if (!rs.next()) {
							JOptionPane.showMessageDialog(null,
									"Account does not exist with this No.", "",
									JOptionPane.ERROR_MESSAGE);
							setVisible(true);
						} else

							new ViewFrame().viewFrame(1,
									Integer.parseInt(accno), 1,username);

					} catch (HeadlessException | SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});

		newtrans.setBounds(100, 80, 200, 30);
		viewtrans.setBounds(100, 120, 200, 30);

		trans.add(newtrans);
		trans.add(viewtrans);
		return trans;

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

	String getaccno() {
		return JOptionPane.showInputDialog(accframe, "Enter Account No", "",
				JOptionPane.PLAIN_MESSAGE);

	}

	
}
