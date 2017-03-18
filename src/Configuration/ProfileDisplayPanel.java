package Configuration;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class ProfileDisplayPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ProfileDisplayPanel() {
		setLayout(null);
		
		JLabel lblWinchUnits = new JLabel("Display Units");
		lblWinchUnits.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWinchUnits.setBounds(181, 136, 104, 14);
		add(lblWinchUnits);
	}
}
