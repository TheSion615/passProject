package passProjec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CreateAcctWin extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame createAcctGUI = new JFrame("Password Manager Program");
	JPanel createAcctPanel = new JPanel(new GridBagLayout());
	JLabel userNameLabel = new JLabel("Create a username: ");
	JLabel passWordLabel1 = new JLabel("Create a password: ");
	JLabel passWordLabel2 = new JLabel("At least 9 characters, Upper case letter, Lower case letter, At least 1 Number(s)");
	JTextField textFieldUserName = new JTextField(10);
	JPasswordField textFieldPassword = new JPasswordField(10);
	JButton createAcctButton = new JButton("Create Account");
	JButton cancelCreateAcctButton = new JButton("Cancel");

	public CreateAcctWin() {
		// Set up the GUI
		createAcctGUI.setSize(500, 300); // width then height
		GridBagConstraints con = new GridBagConstraints();
		con.insets = new Insets(1, 1, 1, 1); // Top, Left, Bottom, Right
		
		// Add user name label
		con.gridx = 0; // set the column constraint to 0
		con.gridy = 1; // set the row constraint to 1
		con.anchor = GridBagConstraints.WEST; // Left align
		createAcctPanel.add(userNameLabel, con); // add the label
		
		// Add user name text field
		con.gridx = 0; // set the column constraint to 1
		con.anchor = GridBagConstraints.EAST; // Right align
		createAcctPanel.add(textFieldUserName, con); // add the entry field
		textFieldUserName.setHorizontalAlignment(JTextField.LEFT);
		
		// Add password label 1
		con.gridx = 0; // reset column
		con.gridy = 2; // move to the next row
		con.anchor = GridBagConstraints.WEST;
		createAcctPanel.add(passWordLabel1, con);
		
		// Add password text field
		con.gridx = 0;
		con.anchor = GridBagConstraints.EAST;
		createAcctPanel.add(textFieldPassword, con);
		textFieldPassword.setHorizontalAlignment(JTextField.LEFT);
		
		// Add password label 2
		con.gridx = 0;
		con.gridy = 3;
		con.anchor = GridBagConstraints.WEST;
		createAcctPanel.add(passWordLabel2, con);
		
		// Add "Cancel Account Creation" button
		con.gridx = 0;
		con.gridy = 4;
		con.gridwidth = 1; // span across two columns
		con.anchor = GridBagConstraints.CENTER;
		createAcctPanel.add(cancelCreateAcctButton, con);
		
		// Add "Create Account" button
		con.gridx = 0;
		con.gridy = 5;
		con.anchor = GridBagConstraints.CENTER;
		createAcctPanel.add(createAcctButton, con);
		
		// Add create account button action listeners
		createAcctButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textFieldUserName.getText();
				//String password = new String(textFieldPassword.getText());
				String password = new String(textFieldPassword.getPassword());
				StringBuilder errorMessage = new StringBuilder("<html>");
				boolean hasError = false;
				
				if (NewAccountManager.isUsernameTaken(username)) {
					errorMessage.append("The username \"" + username + "\" already exists. Please choose a different username.<br>");
					hasError = true;
				} 
				if (!NewAccountManager.isValidPass(password)) {
					errorMessage.append("The password \"" + password + "\" is invalid! Please abide by the password requirements.<br>");
					hasError = true;
				}
				
				errorMessage.append("</html>"); //tag to separate line
				
				if (hasError) {
					JOptionPane.showMessageDialog(createAcctGUI, errorMessage.toString());
				} else {
					NewAccountManager.createAccount(username, password);
					createAcctGUI.dispose();
					new Main().displayHomeScreen();
					createAcctButton.setEnabled(false);
					repaint();
				}
			
			}
		});
		
		cancelCreateAcctButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//createAcctGUI.dispose();
				createAcctGUI.dispose();
				new Main().displayHomeScreen();
				//Main.frame.setVisible(true);
			}
		});
		
		// Finalize GUI
		createAcctGUI.add(createAcctPanel); // add the panel
		createAcctGUI.setResizable(false); // preclude resizing
		createAcctGUI.setLocationRelativeTo(null); // center the window
		createAcctGUI.setVisible(true); // make it visible
	}

}
