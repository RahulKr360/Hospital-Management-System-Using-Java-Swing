import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class Login {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 860, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLoginHead = new JLabel("Northern Hospital");
		lblLoginHead.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLoginHead.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginHead.setBounds(334, 30, 172, 43);
		frame.getContentPane().add(lblLoginHead);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsername.setBounds(331, 150, 83, 13);
		frame.getContentPane().add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(412, 147, 96, 19);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPassword.setBounds(331, 195, 83, 13);
		frame.getContentPane().add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(412, 192, 96, 19);
		frame.getContentPane().add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword());
                
                System.out.println(username+"-------"+password);  
                
                String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
                String DBusername = "system";
                String DBpassword = "rahul11";

                try (Connection conn = DriverManager.getConnection(jdbcUrl, DBusername, DBpassword)) {
                    String sql = "SELECT COUNT(*) FROM ADMINTABLE WHERE username = ? AND PASS_WORD = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    
                    System.out.println(statement);  
                    
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        OpeningScreen demo = new OpeningScreen();
                        demo.OpeningFrame.setVisible(true);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
			}
		});
		btnLogin.setBounds(378, 251, 85, 21);
		frame.getContentPane().add(btnLogin);
	}
}
