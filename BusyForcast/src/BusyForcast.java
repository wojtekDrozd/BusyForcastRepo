import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BusyForcast {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BusyForcast window = new BusyForcast();
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
	public BusyForcast() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("BusyForcast 0.0.2");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Aktualizuj zajętość");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DataUpdater updater = new DataUpdater();
				DataUpdater.update();
				JOptionPane.showMessageDialog(null, "Zajetość zaktualizowana");
			}
		});
		btnNewButton.setBounds(127, 27, 170, 83);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Aktualizuj dostępność");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Dostępność zaktualizowana");
			}
		});
		btnNewButton_1.setBounds(127, 139, 170, 89);
		frame.getContentPane().add(btnNewButton_1);
	}
}
