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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class MainUserWindow extends JFrame implements ActionListener {
	JLabel header, footer;
	Container cn = null;
	JButton bCheck, bView, bPass, bLogout;
	int accno;

	public MainUserWindow(int a) {
		cn = getContentPane();
		cn.setLayout(null);
		accno = a;

		header = new JLabel("CUSTOMER", JLabel.CENTER);
		footer = new JLabel("Logged in with Account No : " + accno,
				JLabel.LEFT);
		bCheck = new JButton("View Customer Profile");
		bView = new JButton("View Transactions");
		bPass = new JButton("Change Password");
		bLogout = new JButton("Logout");

		header.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 20));
		footer.setFont(new Font("Arial", Font.BOLD, 12));

		bView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new ViewFrame().viewFrame(1, accno, 0, null);
			}
		});

		bCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String sql_query;
				sql_query = "select * from customer where accno=" + accno;
				int columncount = 8;
				String columns[] = { "Account No", "Name", "Address",
						"Phone No", "Account Type", "Balance",
						"No. of transactions", "Password" };
				ResultSet rs = null;
				String[][] d = null;
				try {
					rs = query("select count(*) from customer where accno = "
							+ accno);
					rs.next();
					int rowcount = Integer.parseInt(rs.getString(1));
					rs = query(sql_query);
					d = new String[rowcount][8];
					int jr = 0;

					JTable table = new JTable(d, columns) {
						DefaultTableCellRenderer renderRight = new DefaultTableCellRenderer();

						{// initializer block
							renderRight
									.setHorizontalAlignment(SwingConstants.CENTER);
						}

						@Override
						public TableCellRenderer getCellRenderer(int arg0,
								int arg1) {
							return renderRight;

						}

					};

					while (rs.next()) {

						for (int i = 1; i <= columncount; ++i) {
							d[jr][i - 1] = rs.getString(i);
						}
						jr++;
					}
					JScrollPane spTable = new JScrollPane(table);
					spTable.setBounds(50, 50, 800, 40);

					JLabel tableheader = new JLabel("CUSTOMER PROFILE", JLabel.CENTER);
					tableheader.setFont(new Font("Copperplate Gothic Bold",
							Font.PLAIN, 20));
					tableheader.setBounds(0, 0, 900, 30);
					Container frame = new JFrame();
					frame.setLayout(null);
					frame.add(tableheader);
					frame.add(spTable);
					frame.setBounds(400, 200, 900, 150);
					frame.setVisible(true);
					frame = getContentPane();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		bPass.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				JPasswordField oldpass = new JPasswordField(10);
				panel.add(new JLabel("Enter current password"));
				panel.add(oldpass);
				JOptionPane.showConfirmDialog(null, panel, "",
						JOptionPane.PLAIN_MESSAGE);

				panel.removeAll();
				ResultSet rs = null;
				if (oldpass.getText().trim().length() == 0
						|| oldpass.getText() == null)
					;
				else {

					try {
						rs = query("select password from customer where accno = "
								+ accno);
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
								rs = query("update customer set password = '"
										+ jt1.getText() + "' where accno="
										+ accno);
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
						// TODO: handle exception
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

		header.setBounds(0, 25, 400, 30);
		footer.setBounds(20, 240, 280, 30);
		bCheck.setBounds(100, 80, 200, 30);
		bView.setBounds(100, 120, 200, 30);
		bPass.setBounds(100, 160, 200, 30);
		bLogout.setBounds(300, 240, 100, 30);

		cn.add(header);
		cn.add(footer);
		cn.add(bView);
		cn.add(bCheck);
		cn.add(bPass);
		cn.add(bLogout);

		setVisible(true);
		setResizable(false);
		setBounds(400, 250, 400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	@Override
	public void actionPerformed(ActionEvent ae) {
		JPanel panel = new JPanel();

		if (ae.getSource() == bPass) {
			panel.add(new JLabel("Enter password"));
			panel.add(new JPasswordField(10));
			JOptionPane.showConfirmDialog(null, panel, "Enter password",
					JOptionPane.PLAIN_MESSAGE);

		}
	}
}
