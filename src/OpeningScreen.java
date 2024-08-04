import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class OpeningScreen {

	JFrame OpeningFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpeningScreen window = new OpeningScreen();
					window.OpeningFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OpeningScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		OpeningFrame = new JFrame();
		OpeningFrame.setBounds(100, 100, 860, 500);
		OpeningFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		OpeningFrame.getContentPane().setLayout(null);
		
		JButton btnPatient = new JButton("Patient");
		btnPatient.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				PatientFrame1 pf1 = new PatientFrame1();
                pf1.PatientFrame1.setVisible(true);
                OpeningFrame.dispose();
			}
		});
		btnPatient.setBounds(210, 173, 173, 85);
		OpeningFrame.getContentPane().add(btnPatient);
		
		JButton btnDoctor = new JButton("Doctor");
		btnDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DoctorFrame1 df1 = new DoctorFrame1();
                df1.DoctorFrame1.setVisible(true);
                OpeningFrame.dispose();
			}
		});
		btnDoctor.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDoctor.setBounds(465, 173, 173, 85);
		OpeningFrame.getContentPane().add(btnDoctor);
	}

}
