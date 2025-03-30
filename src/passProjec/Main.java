package passProjec;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        new Main().displayHomeScreen();
    }

    public void displayHomeScreen() {
        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();
        con.insets = new Insets(10, 10, 10, 10); // Top, Left, Bottom, Right

        JLabel titleLabel = new JLabel("Password Manager", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 27)); // Font

        con.gridx = 0; // set the column constraint to 0
        con.gridy = 0; // set the row constraint to 1
        con.gridwidth = 3; // set the width to 3
        con.anchor = GridBagConstraints.CENTER; // Middle align
        panel.add(titleLabel, con); // add the label

        // Adding image for interface
        try {
            BufferedImage img = javax.imageio.ImageIO.read(new File("src/passmanager.jpg"));
            ImageIcon icon = new ImageIcon(img);
            JLabel imageLabel = new JLabel(icon);
            con.gridy = 1; // set the row constraint to 1
            con.gridwidth = 3; // set the width to 3
            con.anchor = GridBagConstraints.CENTER; // Left align
            panel.add(imageLabel, con);
        } catch (Exception e) {
            // Handle exception
        }

        Object[] options = {"Create Account", "Login", "Cancel"};

        // option dialog
        int selection = JOptionPane.showOptionDialog(
                null,
                panel,
                "Password Manager Program",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, options,
                options[2]); // Initial focus on "Login"

        switch (selection) {
            case JOptionPane.YES_OPTION:
                new CreateAcctWin();
                break;
            case JOptionPane.NO_OPTION:
                LoginAcct loginWindow = new LoginAcct();
                loginWindow.setVisible(true);
                break;
            case JOptionPane.CANCEL_OPTION:
                System.out.println("Program Canceled! Exiting.");
                System.exit(0);
                break;
        }
    }
}