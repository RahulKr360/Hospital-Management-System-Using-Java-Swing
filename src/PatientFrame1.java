import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.proteanit.sql.DbUtils;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PatientFrame1 {

	JFrame PatientFrame1;
	private JTextField txtPID;
	private JTextField txtPatientName;
	private JTextField txtAge;
	private JTextField txtReason;
	private JTextField txtAddress;
	private JTextField txtDoctorId;
	private JTextField txtContact;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientFrame1 window = new PatientFrame1();
					window.PatientFrame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PatientFrame1() {
		initialize();
		table_load();
		Connect();
	}
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
    String DBusername = "system";
    String DBpassword = "rahul11";
    
    
    public void Connect() 
    {
    	try {
    		conn = DriverManager.getConnection(jdbcUrl, DBusername, DBpassword);
        // Connection successful
    		System.out.println("Connected to Oracle database!");    		
		
    	} catch (SQLException e) 
    	{
        // Connection failed
    		System.err.println("Connection failed! Error: " + e.getMessage());
    	}
    }
	
	//Validate All Fields
	private boolean validateFields() {
        if (txtPID.getText().isEmpty() || txtPatientName.getText().isEmpty() || txtAge.getText().isEmpty() ||
                txtReason.getText().isEmpty() || txtAddress.getText().isEmpty() || txtContact.getText().isEmpty() ||
                txtDoctorId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are mandatory!");
            return false;
        }

        // Validate PID uniqueness
        try (Connection conn = DriverManager.getConnection(jdbcUrl, DBusername, DBpassword)) {
            String sql = "SELECT COUNT(*) FROM PatientTable WHERE PID = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtPID.getText());
            ResultSet resultSet = pst.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                JOptionPane.showMessageDialog(null, "PID already exists! Please enter a unique PID.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            return false;
        }

        return true;
    }
		
	
	
    
    private JTable table;
	public void table_load() {
		try {
			conn = DriverManager.getConnection(jdbcUrl, DBusername, DBpassword);
			pst=conn.prepareStatement("select * from PatientTable");
			rs=pst.executeQuery();
			TableModel model=DbUtils.resultSetToTableModel(rs);
			table.setModel(model);
		}
		catch(Exception h) {
			System.out.println(h);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		PatientFrame1 = new JFrame();
		PatientFrame1.setBounds(100, 100, 1024, 524);
		PatientFrame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PatientFrame1.getContentPane().setLayout(null);
		
		JLabel lblPatientForm = new JLabel("PATIENT FORM");
		lblPatientForm.setBounds(402, 42, 118, 37);
		lblPatientForm.setFont(new Font("Tahoma", Font.BOLD, 15));
		PatientFrame1.getContentPane().add(lblPatientForm);
		
		JLabel lblPID = new JLabel("PID");
		lblPID.setBounds(87, 161, 45, 13);
		PatientFrame1.getContentPane().add(lblPID);
		
		JLabel lblPID_1 = new JLabel("Patient Name");
		lblPID_1.setBounds(87, 187, 105, 13);
		PatientFrame1.getContentPane().add(lblPID_1);
		
		txtPatientName = new JTextField();
		txtPatientName.setBounds(228, 181, 182, 19);
		txtPatientName.setColumns(10);
		PatientFrame1.getContentPane().add(txtPatientName);
		
		JLabel lblPID_2 = new JLabel("Age");
		lblPID_2.setBounds(87, 213, 89, 13);
		PatientFrame1.getContentPane().add(lblPID_2);
		
		txtAge = new JTextField();
		txtAge.setBounds(228, 210, 182, 19);
		txtAge.setColumns(10);
		PatientFrame1.getContentPane().add(txtAge);
		
		JLabel lblPID_3 = new JLabel("Reason");
		lblPID_3.setBounds(87, 242, 68, 13);
		PatientFrame1.getContentPane().add(lblPID_3);
		
		txtReason = new JTextField();
		txtReason.setBounds(228, 236, 182, 19);
		txtReason.setColumns(10);
		PatientFrame1.getContentPane().add(txtReason);
		
		txtPID = new JTextField();
		txtPID.setBounds(228, 155, 182, 19);
		PatientFrame1.getContentPane().add(txtPID);
		txtPID.setColumns(10);
		
		JLabel lblPID_3_1 = new JLabel("Address");
		lblPID_3_1.setBounds(87, 268, 68, 13);
		PatientFrame1.getContentPane().add(lblPID_3_1);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(228, 265, 182, 19);
		txtAddress.setColumns(10);
		PatientFrame1.getContentPane().add(txtAddress);
		
		JLabel lblPID_3_1_1 = new JLabel("Doctor ID");
		lblPID_3_1_1.setBounds(87, 367, 68, 13);
		PatientFrame1.getContentPane().add(lblPID_3_1_1);
		
		txtDoctorId = new JTextField();
		txtDoctorId.setBounds(228, 364, 182, 19);
		txtDoctorId.setColumns(10);
		PatientFrame1.getContentPane().add(txtDoctorId);
		
		JLabel lblPID_3_1_2 = new JLabel("Contact");
		lblPID_3_1_2.setBounds(87, 294, 68, 13);
		PatientFrame1.getContentPane().add(lblPID_3_1_2);
		
		txtContact = new JTextField();
		txtContact.setBounds(228, 291, 182, 19);
		txtContact.setColumns(10);
		PatientFrame1.getContentPane().add(txtContact);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBounds(325, 393, 85, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pid = txtPID.getText();
                String name = txtPatientName.getText();
                String age = txtAge.getText();
                String reason = txtReason.getText();
                String address = txtAddress.getText();
                String contact = txtContact.getText();
                String doctorId = txtDoctorId.getText();
                              
                

                // Insert data into database
                if (validateFields()) {
                try (Connection conn = DriverManager.getConnection(jdbcUrl, DBusername, DBpassword)) {
                    String sql = "INSERT INTO PatientTable (PID, Name, Age, Reason, Address, Contact, DoctorID) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, pid);
                    pst.setString(2, name);
                    pst.setString(3, age);
                    pst.setString(4, reason);
                    pst.setString(5, address);
                    pst.setString(6, contact);
                    pst.setString(7, doctorId);
                    int rowsInserted = pst.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Record added successfully!");
                        txtPID.setText("");
                        txtPatientName.setText("");
                        txtAge.setText("");
                        txtReason.setText("");
                        txtAddress.setText("");
                        txtContact.setText("");
                        txtDoctorId.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add record.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
                }
                table_load();
			}
		});
		PatientFrame1.getContentPane().add(btnNewButton);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(153, 449, 85, 21);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pid = txtPID.getText();
                String name = txtPatientName.getText();
                String age = txtAge.getText();
                String reason = txtReason.getText();
                String address = txtAddress.getText();
                String contact = txtContact.getText();
                String doctorId = txtDoctorId.getText();
                              
                

                // Insert data into database
                try (Connection conn = DriverManager.getConnection(jdbcUrl, DBusername, DBpassword)) {
                    String sql = "UPDATE PatientTable SET Name=?, Age=?, Reason=?, Address=?, Contact=?, DoctorID=? WHERE PID=?";
                    pst = conn.prepareStatement(sql);                    
                    pst.setString(1, name);
                    pst.setString(2, age);
                    pst.setString(3, reason);
                    pst.setString(4, address);
                    pst.setString(5, contact);
                    pst.setString(6, doctorId);
                    pst.setString(7, pid);
                    int rowsInserted = pst.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Record updated successfully!");
                        txtPID.setText("");
                        txtPatientName.setText("");
                        txtAge.setText("");
                        txtReason.setText("");
                        txtAddress.setText("");
                        txtContact.setText("");
                        txtDoctorId.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update record.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }             
                table_load();
				
			}
		});
		PatientFrame1.getContentPane().add(btnUpdate);
		
		JButton btnNewButton_1_1 = new JButton("Delete");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			String pid = txtPID.getText();
			try {
				pst= conn.prepareStatement("DELETE FROM PatientTable WHERE PID =?");
				pst.setString(1,pid);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Records deleted succefully");
				txtPID.setText("");
                txtPatientName.setText("");
                txtAge.setText("");
                txtReason.setText("");
                txtAddress.setText("");
                txtContact.setText("");
                txtDoctorId.setText("");
				table_load();	
			}
			catch(Exception w) {
				System.out.println(w);
			}
			}	
		});
		btnNewButton_1_1.setBounds(248, 449, 85, 21);
		PatientFrame1.getContentPane().add(btnNewButton_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(453, 156, 500, 200);
		PatientFrame1.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();
				txtPID.setText(table.getModel().getValueAt(row,0).toString());
				txtPatientName.setText(table.getModel().getValueAt(row,1).toString());
				txtAge.setText(table.getModel().getValueAt(row,2).toString());
				txtReason.setText(table.getModel().getValueAt(row,3).toString());
				txtAddress.setText(table.getModel().getValueAt(row,4).toString());
				txtContact.setText(table.getModel().getValueAt(row,5).toString());
				txtDoctorId.setText(table.getModel().getValueAt(row,6).toString());				
				
			}
		});
		scrollPane.setViewportView(table);
	}
}	
