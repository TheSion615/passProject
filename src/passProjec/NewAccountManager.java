package passProjec;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import javax.crypto.*;
import javax.swing.JOptionPane;

public class NewAccountManager {
	private static final String FILE_PATH = "src/credentials.txt";
	private static HashMap<String, String> accounts = new HashMap<>(); 
	
	// method 
	static {
		loadCredentials();
	}
	
	public static HashMap<String, String> loadCredentials() {
		try (BufferedReader userreader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			String username = null;
			while ((line = userreader.readLine()) != null) {
				if (username == null) {
					username = line.trim();
				} else {
					String password = line.trim();
					accounts.put(username,  password);
					userreader.readLine();
					username = null;
				}
			}
		} catch (IOException e) {
			System.out.println("");
			e.printStackTrace();
		}
		return accounts;
	}
	
	private static void saveCredentials(String username, String password) {
		try (BufferedWriter userwriter = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
			userwriter.write(username);
			userwriter.newLine();
			userwriter.write(password);
			userwriter.newLine();
			userwriter.newLine();
		} catch (IOException e) {
			System.out.println("");
			e.printStackTrace();
		}
	}
	
	public static String hashPassword(String password) {
		try { 	MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
				byte[] hash = messageDigest.digest(password.getBytes());
				StringBuilder hexString = new StringBuilder();
				for (byte b : hash) {
					String hex = Integer.toHexString(0xff & b);
					if (hex.length() == 1) {
						hexString.append('0');
					}
					hexString.append(hex);
				}
				return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void createAccount(String username, String password) {
		String hashedPassword = hashPassword(password);
		accounts.put(username, hashedPassword);
		saveCredentials(username, hashedPassword);
		JOptionPane.showMessageDialog(null, "Account created!");
	}
	
	public static boolean isUsernameTaken(String username) {
		return accounts.containsKey(username.toLowerCase());
	}
	
	public static boolean isValidPass(String password) {
		if (password.length() < 9) {
			return false;
		}
		String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{9,}$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(password).matches();
	}
	
	public static boolean validateCredentials(String username, String password) {
		String hashedPassword = hashPassword(password);
		return accounts.containsKey(username) &&
				accounts.get(username).equals(hashedPassword);
	}

}
