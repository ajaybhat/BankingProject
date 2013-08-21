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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class EditCustomer extends JFrame implements ActionListener {

	// for window
	JTabbedPane tab;
	Container cn = null;

	// for transaction
	JLabel lType, lAmt, lDate;
	JTextField tAmt;
	@SuppressWarnings("rawtypes")
	JComboBox cType, cDD, cMM, cYY;
	JButton btAdd, btReset, bBack;

	// for edit customer
	JLabel lName, lAdd, lPhno, lPass;
	JButton bcOK, bcReset;
	JTextField tName, tAdd, tPhno, tPass;

	// for all
	int accno;

	public EditCustomer(int a, int index) {
		cn = getContentPane();
		cn.setLayout(null);
		JLabel header = new JLabel("CUSTOMER", JLabel.CENTER);
		bBack = new JButton("Back");
		header.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
		accno = a;

		tab = new JTabbedPane();
		tab.addTab("Edit Customer", addCustPanel());
		tab.addTab("Add Transaction", addTransPanel());
		tab.setVisible(true);
		tab.setSelectedIndex(index);
		bBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new MainAdminWindow("ajay");

			}
		});

		header.setBounds(0, 0, 325, 30);
		bBack.setBounds(325, 0, 75, 30);
		tab.setBounds(0, 30, 400, 400);

		cn.add(header);
		cn.add(tab);
		cn.add(bBack);

		setBounds(400, 150, 400, 430);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	JPanel addCustPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		lName = new JLabel("Customer Name");
		lAdd = new JLabel("Address");
		lPhno = new JLabel("Phone No. ");
		lPass = new JLabel("Password ");

		String sql_stmt = "select * from customer where accno = " + accno, values[][] = new String[1][4];
		ResultSet rs = null;
		try {
			rs = query(sql_stmt);
			rs.next();
			values[0][0] = rs.getString("name");
			values[0][1] = rs.getString("address");
			values[0][2] = rs.getString("phno");
			values[0][3] = rs.getString("password");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		bcOK = new JButton("OK");
		bcReset = new JButton("Reset");
		tName = new JTextField(values[0][0]);
		tAdd = new JTextField(values[0][1]);
		tPhno = new JTextField(values[0][2]);
		tPass = new JTextField(values[0][3]);

		lName.setBounds(20, 60, 100, 30);
		tName.setBounds(130, 60, 200, 30);
		lAdd.setBounds(20, 100, 100, 30);
		tAdd.setBounds(130, 100, 200, 30);
		lPhno.setBounds(20, 140, 100, 30);
		tPhno.setBounds(130, 140, 200, 30);
		lPass.setBounds(20, 180, 100, 30);
		tPass.setBounds(130, 180, 200, 30);

		bcOK.setBounds(100, 240, 75, 30);
		bcReset.setBounds(200, 240, 75, 30);

		panel.add(lName);
		panel.add(tName);
		panel.add(lAdd);
		panel.add(tAdd);
		panel.add(lPhno);
		panel.add(tPhno);
		panel.add(lPass);
		panel.add(tPass);
		panel.add(bcOK);
		panel.add(bcReset);

		bcOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = tName.getText(), address = tAdd.getText(), phno = tPhno
						.getText(), password = tPass.getText();

				String sql_stmt = "update customer set name='" + name
						+ "',address='" + address + "',phno='" + phno
						+ "',password='" + password + "'where accno=" + accno;
				ResultSet rs = null;

				rs = query(sql_stmt);

				try {
					if (rs.getWarnings() == null)
						JOptionPane.showMessageDialog(null,
								"Details of \tAccount : " + accno
										+ "\n\tCustomer : " + name
										+ "\nupdated");
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		bcReset.addActionListener(this);

		tName.requestFocus();
		return panel;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	JPanel addTransPanel() {

		JPanel panel = new JPanel();

		panel.setLayout(null);
		String[] dd = new String[31], mm = { "Jan", "Feb", "Mar", "Apr", "May",
				"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }, yy = new String[23], transTypes = {
				"Credit", "Debit" };
		for (int i = 1; i <= 31; i++)
			dd[i - 1] = Integer.toString(i);
		for (int i = 1990; i <= 2012; i++)
			yy[i - 1990] = Integer.toString(i);

		lDate = new JLabel("Transaction Date");
		lType = new JLabel("Transaction Type");
		lAmt = new JLabel("Amount");

		cType = new JComboBox(transTypes);
		cDD = new JComboBox(dd);
		cMM = new JComboBox(mm);
		cYY = new JComboBox(yy);
		btAdd = new JButton("Add Transaction");
		btReset = new JButton("Reset");
		tAmt = new JTextField("");
		lDate.setBounds(20, 50, 100, 30);
		cDD.setBounds(130, 50, 40, 30);
		cMM.setBounds(180, 50, 50, 30);
		cYY.setBounds(240, 50, 60, 30);
		lType.setBounds(20, 90, 100, 30);
		cType.setBounds(130, 90, 100, 30);
		lAmt.setBounds(20, 130, 100, 30);
		tAmt.setBounds(130, 130, 200, 30);
		btAdd.setBounds(75, 190, 150, 30);
		btReset.setBounds(235, 190, 75, 30);

		btAdd.addActionListener(this);
		btReset.addActionListener(this);

		panel.add(lType);
		panel.add(cType);
		panel.add(lDate);
		panel.add(cDD);
		panel.add(cMM);
		panel.add(cYY);
		panel.add(lAmt);
		panel.add(tAmt);
		panel.add(btAdd);
		panel.add(btReset);

		tAmt.requestFocus();
		return panel;

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btAdd) {
			int amt = 0, flag = 1;
			String transtype, date, sql_stmt;

			try {
				amt = Integer.parseInt(tAmt.getText());
				flag = 1;
			} catch (NumberFormatException e) {
				JOptionPane
						.showMessageDialog(null,
								"Amount can have only numeric values. Please try again");
				flag = 0;
				tAmt.setText("");
				tAmt.requestFocus();
			}
			if (flag == 1) {
				try {
					transtype = (String) cType.getSelectedItem();
					date = (String) cDD.getSelectedItem() + "-"
							+ cMM.getSelectedItem().toString().toUpperCase()
							+ "-" + (String) cYY.getSelectedItem();

					int amount;
					if (transtype.equals("Credit"))
						amount = Math.abs(amt);
					else
						amount = -Math.abs(amt);
					DBConnection d = new DBConnection();
					ResultSet rs = query("select * from customer where accno="
									+ accno);
					rs.next();
					int bal = Integer.parseInt(rs.getString("balance"));

					bal += amount;
					sql_stmt = "insert into transaction values(" + accno + ",'"
							+ date + "','" + transtype + "'," + amt + "," + bal
							+ ")";

					rs = d.dbquery(sql_stmt);
					String updatecust = "update customer set balance =" + bal
							+ ",no_of_trans = no_of_trans + 1 where accno = "
							+ accno;
					rs = d.dbquery(updatecust);
					if (rs.getWarnings() == null)
						JOptionPane.showMessageDialog(null,
								"Transaction Details of Account : " + accno
										+ " saved");
				} catch (HeadlessException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tAmt.setText("");
				tAmt.requestFocus();
			}
		} else if (ae.getSource() == btReset) {
			tAmt.setText("");
			cDD.setSelectedIndex(0);
			cMM.setSelectedIndex(0);
			cYY.setSelectedIndex(0);
		} else if (ae.getSource() == bcOK) {

		}

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