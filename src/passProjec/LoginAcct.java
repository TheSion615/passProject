package passProjec;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

class LoginAcct extends JFrame {

    private static final long serialVersionUID = 1L;

    JPanel loginAcctPanel = new JPanel(new GridBagLayout());
    JLabel userNameLabel = new JLabel("Enter your username: ");
    JLabel passWordLabel1 = new JLabel("Enter your password: ");
    JTextField textFieldUserName = new JTextField(10);
    JPasswordField textFieldPassword = new JPasswordField(10);
    JButton loginAcctButton = new JButton("Login");
    JButton cancelLoginAcctButton = new JButton("Cancel");

    private static HashMap<String, String> accounts = new HashMap<>();

    public LoginAcct() {
        accounts = NewAccountManager.loadCredentials();
        loginComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setTitle("Password Manager Program");
        setVisible(true);
    }

    private void loginComponents() {
        GridBagConstraints con = new GridBagConstraints();
        con.insets = new Insets(5, 5, 5, 5); // Top, Left, Bottom, Right

        con.gridx = 0; // set the column constraint to 0
        con.gridy = 0; // set the row constraint to 1
        con.anchor = GridBagConstraints.WEST; // Left align
        loginAcctPanel.add(userNameLabel, con); // add the label

        con.gridx = 1; // set the column constraint to 1
        con.anchor = GridBagConstraints.EAST; // Right align
        loginAcctPanel.add(textFieldUserName, con); // add the entry field
        textFieldUserName.setHorizontalAlignment(JTextField.LEFT);

        con.gridx = 0; // reset column
        con.gridy = 2; // move to the next row
        con.anchor = GridBagConstraints.EAST;
        loginAcctPanel.add(passWordLabel1, con);

        con.gridx = 1;
        con.anchor = GridBagConstraints.EAST;
        loginAcctPanel.add(textFieldPassword, con);
        textFieldPassword.setHorizontalAlignment(JTextField.LEFT);

        con.gridx = 0;
        con.gridy = 6;
        con.gridwidth = 2; // span across two columns
        con.anchor = GridBagConstraints.CENTER;
        loginAcctPanel.add(cancelLoginAcctButton, con);

        con.gridx = 0;
        con.gridy = 4;
        con.gridwidth = 2;
        con.anchor = GridBagConstraints.CENTER;
        loginAcctPanel.add(loginAcctButton, con);

        loginAcctButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUserName.getText().toLowerCase();
                String password = new String(textFieldPassword.getPassword());
                String hashedPassword = NewAccountManager.hashPassword(password);

                if (accounts.containsKey(username) && accounts.get(username).equals(hashedPassword)) {
                    JOptionPane.showMessageDialog(null, "Login Success");
                    mainFrame();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });

        cancelLoginAcctButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Main().displayHomeScreen();
            }
        });
        add(loginAcctPanel);
    }

    private void mainFrame() {
        JFrame showMainFrame = new JFrame("Password Manager");
        showMainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        showMainFrame.setSize(800, 600);
        showMainFrame.setLocationRelativeTo(null);

        showMainFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Password Management System");
        title.setFont(new Font("Georgia", Font.BOLD, 27)); // Font
        topPanel.add(title, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showMainFrame.dispose();
                dispose();
                new Main().displayHomeScreen();
            }
        });
        topPanel.add(logoutButton, BorderLayout.EAST);

        try {
            BufferedImage img = ImageIO.read(new File("src/passmanager2.jpg"));
            ImageIcon icon = new ImageIcon(img);
            JLabel imgLabel = new JLabel(icon);
            topPanel.add(imgLabel, BorderLayout.SOUTH);
        } catch (IOException e) {
            JLabel imgLabel = new JLabel("Image not found");
            topPanel.add(imgLabel, BorderLayout.SOUTH);
        }

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();
        con.insets = new Insets(5, 5, 5, 5); // Top, Left, Bottom, Right

        JLabel descriptionLabel = new JLabel("Enter the Description/Company: ");
        con.gridx = 0;
        con.gridy = 0;
        con.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(descriptionLabel, con);

        JTextField descriptionField = new JTextField(20);
        con.gridx = 1;
        con.gridy = 0;
        con.anchor = GridBagConstraints.LINE_START;
        centerPanel.add(descriptionField, con);

        JLabel userNameLabel = new JLabel("Enter the user name: ");
        con.gridx = 0;
        con.gridy = 1;
        con.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(userNameLabel, con);

        JTextField userNameField = new JTextField(20);
        con.gridx = 1;
        con.gridy = 1;
        con.anchor = GridBagConstraints.LINE_START;
        centerPanel.add(userNameField, con);

        JLabel passwordLabel = new JLabel("Enter the password: ");
        con.gridx = 0;
        con.gridy = 2;
        con.anchor = GridBagConstraints.LINE_END;
        centerPanel.add(passwordLabel, con);

        JPasswordField passwordField = new JPasswordField(20);
        con.gridx = 1;
        con.gridy = 2;
        con.anchor = GridBagConstraints.LINE_START;
        centerPanel.add(passwordField, con);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        JButton displayAllButton = new JButton("Display All");
        displayAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("src/passwords.txt"));
                    String line;
                    String[][] data = new String[1000][3]; // Initialize an array to hold the data
                    int rowIndex = 0;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("\\|");
                        if (parts.length == 3) {
                            data[rowIndex][0] = parts[0]; // Business
                            data[rowIndex][1] = parts[1]; // User Name
                            data[rowIndex][2] = parts[2]; // Password
                            rowIndex++;
                        }
                    }

                    // Sort the data alphabetically by company
                    String[][] sortedData = new String[rowIndex][3];
                    System.arraycopy(data, 0, sortedData, 0, rowIndex);
                    for (int i = 0; i < rowIndex; i++) {
                        for (int j = i + 1; j < rowIndex; j++) {
                            if (sortedData[i][0].compareTo(sortedData[j][0]) > 0) {
                                String[] temp = sortedData[i];
                                sortedData[i] = sortedData[j];
                                sortedData[j] = temp;
                            }
                        }
                    }

                    String[] columnNames = {"Business", "User Name", "Password"};
                    JTable table = new JTable(sortedData, columnNames);
                    JScrollPane scrollPane = new JScrollPane(table);
                    JFrame frame = new JFrame("Account Information");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.add(scrollPane);
                    frame.setSize(800, 600);
                    frame.setVisible(true);
                } catch (IOException ex) {
                }
            }
        });
        bottomPanel.add(displayAllButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchCompany = JOptionPane.showInputDialog(null, "Enter the company to search for:");
                String searchUsername = JOptionPane.showInputDialog(null, "Enter the username to search for:");
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("src/passwords.txt"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("\\|");
                        if (parts.length == 3) {
                            String company = parts[0];
                            String username = parts[1];
                            String password = parts[2];
                            if (searchCompany.equals(company) && searchUsername.equals(username)) {
                                JOptionPane.showMessageDialog(null, "Company: " + company + ", Username: " + username + ", Password: " + password);
                                return;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Account not found");
                } catch (IOException ex) {
                }
            }
        });
        bottomPanel.add(searchButton);

        JButton addButton = new JButton("Update");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String company = descriptionField.getText();
                String username = userNameField.getText();
                String password = new String(passwordField.getPassword());

                if (company.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    return;
                }

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("src/passwords.txt", true));
                    writer.write(company + "|" + username + "|" + password + "\n");
                    writer.close();
                    JOptionPane.showMessageDialog(null, "The data for \"" + company + "\" has been added sucessfully!");
                } catch (IOException ex) {
                }
            }
        });
        bottomPanel.add(addButton);
        
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchCompany = JOptionPane.showInputDialog(null, "Enter the company to remove:");
                String searchUsername = JOptionPane.showInputDialog(null, "Enter the username to remove:");
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("src/passwords.txt"));
                    StringBuilder lines = new StringBuilder();
                    String line;
                    boolean found = false;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("\\|");
                        if (parts.length == 3) {
                            String company = parts[0];
                            String username = parts[1];
                            String password = parts[2];
                            if (searchCompany.equals(company) && searchUsername.equals(username)) {
                                found = true;
                            } else {
                                lines.append(line).append("\n");
                            }
                        }
                    }
                    if (found) {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("src/passwords.txt"));
                        writer.write(lines.toString());
                        writer.close();
                        JOptionPane.showMessageDialog(null, "Removed successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Account not found");
                    }
                } catch (IOException ex) {
                }
            }
        });
        bottomPanel.add(removeButton);

        showMainFrame.add(topPanel, BorderLayout.NORTH);
        showMainFrame.add(centerPanel, BorderLayout.CENTER);
        showMainFrame.add(bottomPanel, BorderLayout.SOUTH);

        showMainFrame.setVisible(true);
    }
}