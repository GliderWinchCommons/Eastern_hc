package Configuration;

import DataObjects.CurrentDataObjectSet;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import DataObjects.Operator;
import DatabaseUtilities.DatabaseEntryInsert;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SaveAsNewFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
        private ProfileManagementFrame parent;
        private CurrentDataObjectSet currentData;

	/**
	 * Create the frame.
	 */
	public SaveAsNewFrame() {
            setTitle("New Profile");
            currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setBounds(100, 100, 287, 110);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(new BorderLayout(0, 0));
            setContentPane(contentPane);

            textField = new JTextField();
            contentPane.add(textField, BorderLayout.CENTER);
            textField.setColumns(10);

            JPanel panel = new JPanel();
            contentPane.add(panel, BorderLayout.SOUTH);

            JButton SaveButton = new JButton("Save");
            SaveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                            String profileName = textField.getText();
                            Operator newProfile = currentData.getCurrentProfile();
                            newProfile.setName(profileName);
                            Random randomId = new Random();
                            int id = randomId.nextInt(100000000);
                        try {
                            while (DatabaseEntryIdCheck.IdCheck(newProfile)){
                                id = randomId.nextInt(100000000);
                            }
                            newProfile.setID(id);
                            currentData.setCurrentProfile(newProfile);
                        
                            DatabaseEntryInsert.addOperatorToDB(newProfile);
                        } catch (SQLException ex) {
                            Logger.getLogger(SaveAsNewFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(SaveAsNewFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        parent.Rebuild();
                        dispose();
                    }
            });
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(SaveButton);

            JButton CancelButton = new JButton("Cancel");
            CancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                            dispose();
                    }
            });
            panel.add(CancelButton);

            JLabel lblProfileName = new JLabel("Profile Name:");
            contentPane.add(lblProfileName, BorderLayout.WEST);
        }

        public void setParent(ProfileManagementFrame Parent)
        {
            parent = Parent;
        }

}
