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
import javax.swing.JComboBox;


public class DoctorFrame1 {

	JFrame DoctorFrame1;
	private JTextField txtDoctorName;
	private JTextField txtDepartment;
	private JTextField txtDoctorId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorFrame1 window = new DoctorFrame1();
					window.DoctorFrame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DoctorFrame1() {
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
        if (txtDoctorId.getText().isEmpty() || txtDoctorName.getText().isEmpty() || txtDepartment.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are mandatory!");
            return false;
        }

        // Validate Doctor ID uniqueness
        try (Connection conn = DriverManager.getConnection(jdbcUrl, DBusername, DBpassword)) {
            String sql = "SELECT COUNT(*) FROM DoctorTable WHERE DoctorID = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtDoctorId.getText());
            ResultSet resultSet = pst.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Doctor ID already exists! Please enter a unique Doctor ID.");
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
			pst=conn.prepareStatement("select * from DoctorTable");
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
		DoctorFrame1 = new JFrame();
		DoctorFrame1.setBounds(100, 100, 1024, 524);
		DoctorFrame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DoctorFrame1.getContentPane().setLayout(null);
		
		JLabel lblPatientForm = new JLabel("DOCTOR FORM");
		lblPatientForm.setBounds(391, 38, 118, 37);
		lblPatientForm.setFont(new Font("Tahoma", Font.BOLD, 15));
		DoctorFrame1.getContentPane().add(lblPatientForm);
		
		JLabel lblDocName = new JLabel("Doctor Name");
		lblDocName.setBounds(87, 187, 105, 13);
		DoctorFrame1.getContentPane().add(lblDocName);
		
		txtDoctorName = new JTextField();
		txtDoctorName.setBounds(228, 181, 182, 19);
		txtDoctorName.setColumns(10);
		DoctorFrame1.getContentPane().add(txtDoctorName);
		
		JLabel lblDept = new JLabel("Department");
		lblDept.setBounds(87, 216, 68, 13);
		DoctorFrame1.getContentPane().add(lblDept);
		
		txtDepartment = new JTextField();
		txtDepartment.setBounds(228, 210, 182, 19);
		txtDepartment.setColumns(10);
		DoctorFrame1.getContentPane().add(txtDepartment);
		
		JLabel lblDocID = new JLabel("Doctor ID");
		lblDocID.setBounds(87, 158, 68, 13);
		DoctorFrame1.getContentPane().add(lblDocID);
		
		txtDoctorId = new JTextField();
		txtDoctorId.setBounds(228, 155, 182, 19);
		txtDoctorId.setColumns(10);
		DoctorFrame1.getContentPane().add(txtDoctorId);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBounds(325, 239, 85, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String doctorid = txtDoctorId.getText();
                String doctorname = txtDoctorName.getText();
                String department = txtDepartment.getText();
                              
                

                // Insert data into database
                if (validateFields()) {
                try (Connection conn = DriverManager.getConnection(jdbcUrl, DBusername, DBpassword)) {
                    String sql = "INSERT INTO DoctorTable (DoctorID,DoctorName,Department) VALUES (?, ?, ?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, doctorid);
                    pst.setString(2, doctorname);
                    pst.setString(3, department);
                    int rowsInserted = pst.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Record added successfully!");
                        txtDoctorId.setText("");
                        txtDoctorName.setText("");
                        txtDepartment.setText("");
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
		DoctorFrame1.getContentPane().add(btnNewButton);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(171, 291, 85, 21);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String doctorId = txtDoctorId.getText();
                String doctorname = txtDoctorName.getText();
                String department = txtDepartment.getText();

                // Insert data into database
                try (Connection conn = DriverManager.getConnection(jdbcUrl, DBusername, DBpassword)) {
                    String sql = "UPDATE DoctorTable SET DoctorName=?, Department=? WHERE DoctorID=?";
                    pst = conn.prepareStatement(sql);                    
                    pst.setString(1, doctorname);
                    pst.setString(2, department);
                    pst.setString(3, doctorId);
                    int rowsInserted = pst.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Record updated successfully!");
                        txtDoctorId.setText("");
                        txtDoctorName.setText("");
                        txtDepartment.setText("");
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
		DoctorFrame1.getContentPane().add(btnUpdate);
		
		JButton btnNewButton_1_1 = new JButton("Delete");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			String doctorId = txtDoctorId.getText();
			try {
				pst= conn.prepareStatement("DELETE FROM DoctorTable WHERE DoctorID =?");
				pst.setString(1,doctorId);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Records deleted succefully");
				txtDoctorId.setText("");
                txtDoctorName.setText("");
                txtDepartment.setText("");
				table_load();	
			}
			catch(Exception w) {
				System.out.println(w);
			}
			}	
		});
		btnNewButton_1_1.setBounds(266, 291, 85, 21);
		DoctorFrame1.getContentPane().add(btnNewButton_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(468, 156, 472, 156);
		DoctorFrame1.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();
				txtDoctorId.setText(table.getModel().getValueAt(row,0).toString());
				txtDoctorName.setText(table.getModel().getValueAt(row,1).toString());
				txtDepartment.setText(table.getModel().getValueAt(row,2).toString());			
				
			}
		});
		scrollPane.setViewportView(table);
	}
}	
