package BookShop;

import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BookShop {

	private JFrame frame;
	private JTextField txtBookName;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTextField txtBookId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookShop window = new BookShop();
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
	public BookShop() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs; 
	private JTable table;
	
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/bookshopdb", "root", "");
		}
		catch (ClassNotFoundException ex) {
			
		}
		catch (SQLException ex) {
			
		}
	}
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(28, 11, 145, 50);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(38, 72, 441, 286);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(40, 40, 87, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(40, 92, 87, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(40, 137, 87, 14);
		panel.add(lblNewLabel_1_2);
		
		txtBookName = new JTextField();
		txtBookName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBookName.setBounds(161, 34, 115, 28);
		panel.add(txtBookName);
		txtBookName.setColumns(10);
		
		txtEdition = new JTextField();
		txtEdition.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEdition.setColumns(10);
		txtEdition.setBounds(161, 86, 115, 28);
		panel.add(txtEdition);
		
		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPrice.setColumns(10);
		txtPrice.setBounds(161, 131, 115, 28);
		panel.add(txtPrice);
		
		JButton btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname, edition;
				int price;
				
				bname = txtBookName.getText();
				edition = txtEdition.getText();
				price = Integer.valueOf(txtPrice.getText());
				
				try {
					pst = con.prepareStatement("insert into book(name, edition, price)values(?,?,?)");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setInt(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSave.setBounds(40, 193, 98, 46);
		panel.add(btnSave);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(160, 193, 98, 46);
		panel.add(btnExit);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBookName.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClear.setBounds(290, 193, 98, 46);
		panel.add(btnClear);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1_1.setBounds(38, 369, 441, 74);
		frame.getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Book ID");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2_1.setBounds(42, 32, 87, 14);
		panel_1_1.add(lblNewLabel_1_2_1);
		
		txtBookId = new JTextField();
	
		txtBookId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBookId.setColumns(10);
		txtBookId.setBounds(117, 26, 238, 28);
		panel_1_1.add(txtBookId);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String id = txtBookId.getText();
					
					pst = con.prepareStatement("select name, edition, price from book where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if (rs.next() == true) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						txtBookName.setText(name);
						txtEdition.setText(edition);
						txtPrice.setText(price);
					}
					else {
						txtBookName.setText("");
						txtEdition.setText("");
						txtPrice.setText("");
					}
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 7));
		btnSearch.setBounds(365, 26, 66, 29);
		panel_1_1.add(btnSearch);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname, edition;
				int price, bookId;
				
				
				bname = txtBookName.getText();
				edition = txtEdition.getText();
				price = Integer.valueOf(txtPrice.getText());
				bookId = Integer.valueOf(txtBookId.getText());
				
				try {
					pst = con.prepareStatement("update book set name = ?, edition = ?, price = ? where id = ?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setInt(3, price);
					pst.setInt(4, bookId);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					table_load();
					
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(555, 384, 98, 46);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				int  bookId;
				
				bookId = Integer.valueOf(txtBookId.getText());
				
				try {
					pst = con.prepareStatement("delete from book where id = ?");
					
					pst.setInt(1, bookId);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted!");
					
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();
					table_load();
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDelete.setBounds(728, 384, 98, 46);
		frame.getContentPane().add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(489, 72, 385, 301);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
