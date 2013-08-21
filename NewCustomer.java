package com.banking;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class NewCustomer extends JFrame implements ActionListener {

	Container cn = null;
	JLabel header, lAcc, lName, lAdd1, lAdd2, lPhno, lBalance, lType, lPass;
	@SuppressWarnings("rawtypes")
	JComboBox cType;
	JButton bOK, bReset, bBack;
	JTextField tAcc, tName, tAdd1, tAdd2, tPhno, tBalance, tPass;
	String AccTypes[] = { "Savings Account", "Checking Account",
			"Personal Account" };

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NewCustomer() {

		cn = getContentPane();
		cn.setLayout(null);

		header = new JLabel("NEW CUSTOMER", JLabel.CENTER);
		lAcc = new JLabel("Account Number");
		lName = new JLabel("Customer Name");
		lAdd1 = new JLabel("Address Line 1");
		lAdd2 = new JLabel("Address Line 2");
		lPhno = new JLabel("Phone No. ");
		lBalance = new JLabel("Balance ");
		lType = new JLabel("Account Type");
		lPass = new JLabel("Password");
		cType = new JComboBox(AccTypes);
		bOK = new JButton("OK");
		bReset = new JButton("Reset");
		bBack = new JButton("Back");
		tAcc = new JTextField("");
		tName = new JTextField("");
		tAdd1 = new JTextField("");
		tAdd2 = new JTextField("");
		tPhno = new JTextField("");
		tBalance = new JTextField("");
		tPass = new JTextField("");
		header.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));

		header.setBounds(0, 0, 400, 30);
		lAcc.setBounds(20, 50, 100, 30);
		tAcc.setBounds(130, 50, 200, 30);
		lName.setBounds(20, 90, 100, 30);
		tName.setBounds(130, 90, 200, 30);
		lType.setBounds(20, 130, 100, 30);
		cType.setBounds(130, 130, 200, 30);
		lAdd1.setBounds(20, 170, 100, 30);
		tAdd1.setBounds(130, 170, 200, 30);
		lAdd2.setBounds(20, 210, 100, 30);
		tAdd2.setBounds(130, 210, 200, 30);
		lPhno.setBounds(20, 250, 100, 30);
		tPhno.setBounds(130, 250, 200, 30);
		lBalance.setBounds(20, 290, 100, 30);
		tBalance.setBounds(130, 290, 200, 30);
		lPass.setBounds(20, 330, 100, 30);
		tPass.setBounds(130, 330, 200, 30);

		bOK.setBounds(50, 390, 80, 30);
		bReset.setBounds(150, 390, 80, 30);
		bBack.setBounds(250, 390, 80, 30);

		setBounds(400, 150, 400, 500);

		add(header);
		add(lAcc);
		add(tAcc);
		add(lName);
		add(tName);
		add(lType);
		add(cType);
		add(lAdd1);
		add(tAdd1);
		add(lAdd2);
		add(tAdd2);
		add(lPhno);
		add(tPhno);
		add(lPass);
		add(tPass);
		add(lBalance);
		add(tBalance);
		add(bOK);
		add(bReset);
		add(bBack);

		bOK.addActionListener(this);
		bReset.addActionListener(this);
		bBack.addActionListener(this);
		setVisible(true);
		setResizable(false);
		tAcc.requestFocus();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		int accno = 0, balance = 0, fa, fp, fb;
		long phno = 0;
		String name, address, acctype, sql_stmt;
		fa = fp = fb = 1;
		if (ae.getSource() == bOK) {
			if (tName.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter Customer Name");

				tName.setText("");
				tName.requestFocus();
			}

			else if (tAdd1.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter Address");
				tAdd1.setText("");
				tAdd1.requestFocus();
			}

			else if (tPass.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter a password");
				tPass.setText("");
				tPass.requestFocus();
			} else {

				if (tAcc.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "Enter Account No");
					tAcc.setText("");
					tAcc.requestFocus();
				}

				else {
					try {
						accno = Integer.parseInt(tAcc.getText());
						fa = 1;
					} catch (NumberFormatException e) {
						JOptionPane
								.showMessageDialog(null,
										"Account No can have only numeric values. Please try again");
						fa = 0;
						tAcc.setText("");
						tAcc.requestFocus();

					}
				}
				if (tPhno.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "Enter Phone No");
					tPhno.setText("");
					tPhno.requestFocus();
				} else {
					try {
						phno = Long.parseLong(tPhno.getText());
						fp = 1;
					} catch (NumberFormatException e) {
						JOptionPane
								.showMessageDialog(null,
										"Phone No can have only numeric values. Please try again");
						fp = 0;
						tPhno.setText("");
						tPhno.requestFocus();
					}

				}
				if (tBalance.getText().trim().length() == 0) {
					JOptionPane
							.showMessageDialog(null, "Enter Account Balance");
					tBalance.setText("");
					tBalance.requestFocus();
				} else {
					try {
						balance = Integer.parseInt(tBalance.getText());
						fb = 1;
					} catch (NumberFormatException e) {
						JOptionPane
								.showMessageDialog(null,
										"Account Balance can have only numeric values. Please try again");
						fb = 0;
						tBalance.setText("");
						tBalance.requestFocus();
					}
				}

			}
			if (fa == 1 && fp == 1 && fb == 1) {
				name = tName.getText();

				address = tAdd1.getText() + ", " + tAdd2.getText();
				acctype = (String) cType.getSelectedItem();

				String password = tPass.getText();
				sql_stmt = "insert into customer values("
						+ Integer.toString(accno) + ",'" + name + "','"
						+ address + "','" + Long.toString(phno) + "','"
						+ acctype + "'," + Integer.toString(balance) + ",0,'"
						+ password + "')";
				DBConnection d = new DBConnection();
				d.dbquery(sql_stmt);
				JOptionPane.showMessageDialog(null, "Details of \tAccount : "
						+ accno + "\n\tCustomer : " + name + "\nsaved");
				tAcc.setText("");
				tName.setText("");
				tAdd1.setText("");
				tAdd2.setText("");
				tPhno.setText("");
				tBalance.setText("");
				tPass.setText("");
				tAcc.requestFocus();
			}
		}

		else if (ae.getSource() == bReset) {
			tAcc.setText("");
			tName.setText("");
			tAdd1.setText("");
			tAdd2.setText("");
			tPhno.setText("");
			tBalance.setText("");
			tPass.setText("");
			tAcc.requestFocus();
		} else if (ae.getSource() == bBack) {
			dispose();
			new MainAdminWindow("ajay");
		}
	}
}
