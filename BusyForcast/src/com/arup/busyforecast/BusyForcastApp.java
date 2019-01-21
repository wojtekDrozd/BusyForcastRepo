package com.arup.busyforecast;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BusyForcastApp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BusyForcastApp window = new BusyForcastApp();
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
	public BusyForcastApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("BusyForcast 0.0.4");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Aktualizuj zajetosc");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DataUpdater.update();
				JOptionPane.showMessageDialog(null, "Zajetosc zaktualizowana");
			}
		});
		btnNewButton.setBounds(127, 27, 170, 83);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Aktualizuj dostepnosc");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ResourceUpdater rUpdater = new ResourceUpdater();
				ResourceUpdater.update();
				JOptionPane.showMessageDialog(null, "Dostepnosc zaktualizowana");
			}
		});
		btnNewButton_1.setBounds(127, 139, 170, 89);
		frame.getContentPane().add(btnNewButton_1);
	}
}
