import java.util.*;
import java.io.*;

public class Admin {
	private ArrayList<Player> players;
	private final String PLAYERS_FILENAME = "players.bin";
	private final String ADMIN_FILENAME = "admin.txt";
	private String adminUsername;
	private String adminPassword;

	// To construct admin account
	public Admin(String username, String password) {
		this.adminUsername = username;
		this.adminPassword = Utility.getHash(password);
	}

	public String getAdminUsername() {
		return this.adminUsername;
	}

	public String getAdminHashedPassword() {
		return this.adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;	//admin password will already be hashed
	}

	public Admin() {
		players = new ArrayList<Player>();
		loadPlayers();
	}

	private void loadPlayers() {
		try {
			FileInputStream file = new FileInputStream(PLAYERS_FILENAME);
			ObjectInputStream output = new ObjectInputStream(file);

			boolean endOfFile = false;
			while (!endOfFile) {
				try {
					Player player = (Player) output.readObject();
					players.add(player);
				}

				catch (EOFException ex) {
					endOfFile = true;
				}
			}
			output.close();
		}

		catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException");
		}

		catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException");
		}

		catch (IOException ex) {
			System.out.println("IOException");
		}

		System.out.println("Players information loaded");
	}

	private void displayPlayers() {
		for (Player player : players) {
			player.display();
		}
	}

	private void createNewPlayer() {
		// Method provided by teacher - does not allow player to choose own name, pw
		// etc.
		/*
		 * int playerNum = players.size() + 1; Player player = new Player("Player" +
		 * playerNum, "Password" + playerNum, 100); players.add(player);
		 */

		// Header
		System.out.println("Create a new player");
		System.out.println();

		// Prompt for user to create username and password
		String newPlayerName = Keyboard.readString("Enter your username: ");
		String newPlayerPassword = Keyboard.readString("Create a password: ");

		// Create player object and add into array list
		// Set 100 as default chips to start with
		Player newPlayer = new Player(newPlayerName, newPlayerPassword, 100);
		players.add(newPlayer);

	}

	private void savePlayersToBin() {
		try {
			FileOutputStream file = new FileOutputStream(PLAYERS_FILENAME);
			ObjectOutputStream opStream = new ObjectOutputStream(file);

			for (Player player : players) {
				opStream.writeObject(player);
			}

			opStream.close();
		}

		catch (IOException ex) {

		}
	}
	
	private boolean checkEnteredUsername(String enteredUsername) {
		boolean canContinue = false;
		if (enteredUsername.equalsIgnoreCase(getAdminUsername())) {
			canContinue = true;
		} else {
			System.out.println("Incorrect username. Try again.");
		}
		return canContinue;
	}

	private boolean checkEnteredPassword(String enteredPassword) {
		boolean canContinue = false;
		String hashed = Utility.getHash(enteredPassword);
		if (hashed.equals(this.adminPassword)) {
			canContinue = true;
		} else {
			System.out.println("Incorrect password. Try again.");
		}
		return canContinue;
	}

	// delete player method takes in username to be deleted
	private void deletePlayer(String username) {

		boolean playerFound = false;
		
		// loops through arraylist, check login name against username
		for (int i=0; i < players.size(); i++) {
			if (players.get(i).getLoginName().equalsIgnoreCase(username)) {
				players.remove(i);
				playerFound = true;
			}
		}

		if (playerFound == false) {
			System.out.println("No matching Username in database");
		}
	}

	private boolean checkIfUsernameExists(String username) {

		boolean playerExists = false;

		for (Player player : players) {
			// loops through arraylist, check login name against username
			if (player.getLoginName().equalsIgnoreCase(username)) {
				playerExists = true;
			}
		}

		if (playerExists == false) {
			System.out.println("No matching Username in database");
		}

		return playerExists;
	}

	private boolean moreAction() {

		boolean stay = false;

		System.out.println();
		System.out.println("Action completed");
		
		boolean cont = false;
		while (cont == false) {
			
			char reply = Keyboard.readChar("Do you want to do anything else? [Y/N]: ");
			
			if (reply == 'Y' || reply == 'y') {
				stay = true;
				cont = true;
			} else if (reply == 'N' || reply == 'n') {
				stay = false;
				cont = true;
			} else {
				System.out.println("*** Invalid char. [Y/N] only ***");
			}

		}
		
		return stay;
	}

	private int actionMenu() {
		System.out.println("-------------------------------------------");
		System.out.println();
		System.out.println("Welcome to the admin module! What would you like to do today?");
		System.out.println("\t1) Create a player");
		System.out.println("\t2) Delete a player");
		System.out.println("\t3) View all players");
		System.out.println("\t4) Issue more chips to a player");
		System.out.println("\t5) Reset Player's password");
		System.out.println("\t6) Change Administrator's password");
		System.out.println("\t7) Logout");
		System.out.println();
		int action = Keyboard.readInt("Please select an option (1 - 7): ");
		System.out.println("-------------------------------------------");
		return action;
	}

	public void run() {

		// what teacher gave for example
		/*
		 * displayPlayers(); updatePlayersChip(); displayPlayers(); createNewPlayer();
		 * displayPlayers(); savePlayersToBin(); displayPlayers();
		 */
		
		// create admin object
		Admin admin = new Admin("admin", "adminpassword");
		
		// Read admin hashed password from admin.txt
		try {
			File file = new File(ADMIN_FILENAME);
			Scanner reader = new Scanner(file);
			
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				//Set admin password as hashed password in admin.txt file
				admin.setAdminPassword(line);
			}
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		// Admin login
		System.out.println();
		System.out.println("This is the admin module");
		System.out.println("Login is required to access module's functions.");
		System.out.println("-------------------------------------------");

		// Admin username + password error check
		boolean canStart = false;
		do {
			canStart = admin.checkEnteredUsername(Keyboard.readString("Enter admin username: "));
		} while (canStart == false);

		boolean canStart2 = false;
		do {
			canStart2 = admin.checkEnteredPassword(Keyboard.readString("Enter password: "));
		} while (canStart2 == false);
		
		
		// Ask admin what he wants to do
		int action = actionMenu();
		
		boolean performAction = true;
		while (performAction) {
			
			if (action == 1) {
				
				// Create a player
				System.out.println("You have selected \"Create a player\"");
				createNewPlayer();
				
				if(moreAction() == false) {
					performAction = false;
				} else {
					action = actionMenu();
				}
				
			} else if (action == 2) {
				
				// Delete a player
				System.out.println("You have selected \"Delete a player\"");
				String toDelete = Keyboard.readString("Enter the username of player you want to Delete: ");
				deletePlayer(toDelete);
				
				if(moreAction() == false) {
					performAction = false;
				} else {
					action = actionMenu();
				}
				
			} else if (action == 3) {
				
				// View all players
				System.out.println("You have selected \"View all players\"");
				displayPlayers();
				
				if(moreAction() == false) {
					performAction = false;
				}else {
					action = actionMenu();
				}
				
			} else if (action == 4) {
				
				// Issue more chips to a player
				System.out.println("You have selected \"Issue more chips to a player\"");
				String user = Keyboard.readString("Enter the username of player you want to Add Chips to: ");
				
				if (checkIfUsernameExists(user)) {
					
					//Ask how many chips to add
					int chipsToAdd = Keyboard.readInt("Chips to add to "+user+": ");
					
					// Can't add negative amounts
					while(chipsToAdd < 0) {
						System.out.println("Invalid number (negative)");
						chipsToAdd = Keyboard.readInt("Chips to add to "+user+": ");
					}
					
					// add chips to player
					for(Player player: players) {
						if(player.getLoginName().equalsIgnoreCase(user)) {
							player.addChips(chipsToAdd);
							player.displayChips();
							System.out.println();
						}
					}
				}
				
				if(moreAction() == false) {
					performAction = false;
				}else {
					action = actionMenu();
				}
				
			} else if (action == 5) {
				
				// Reset Players Password
				System.out.println("You have selected \"Reset Player's password\"");
				String user = Keyboard.readString("Enter username to perform password reset on: ");
				
				if (checkIfUsernameExists(user)) {
					
					for (Player player : players) {
						// loops through arraylist til username match, change password
						if (player.getLoginName().equalsIgnoreCase(user)) {
							
							// New password has to be double confirmed
							String newPassword = Keyboard.readString("Enter new password: ");
							String confirmation = Keyboard.readString("Re-enter new password: ");
							
							boolean resetSuccessful = false;
							while(resetSuccessful == false) {
								
								if (newPassword.equals(confirmation)) {
									player.setPassword(newPassword);
									System.out.println(player.getLoginName()+"'s password has been reset");
									resetSuccessful = true;
								} else {
									System.out.println("Passwords do not match, please try again.");
									newPassword = Keyboard.readString("Enter new password: ");
									confirmation = Keyboard.readString("Re-enter new password: ");
								}
							}
						}
					}
					
				}
				
				
				if(moreAction() == false) {
					performAction = false;
				}else {
					action = actionMenu();
				}
				
			} else if (action == 6) {
				
				// Change Administrator's password
				System.out.println("You have selected \"Change Administrator's password\"");
				
				// Admin must enter both username and password correctly to reset
				boolean authorizedAccess = false;
				while(authorizedAccess == false) {
					String adminUser = Keyboard.readString("Enter Admin's username: ");
					if (adminUser.equals(admin.getAdminUsername())) {
						String currentPW = Utility.getHash(Keyboard.readString("Enter Admin's current password: "));
						
						if (currentPW.equals(admin.getAdminHashedPassword())) {
							
							// New password has to be double confirmed
							String newPassword = Keyboard.readString("Enter new password: ");
							String confirmation = Keyboard.readString("Re-enter new password: ");
							
							boolean resetSuccessful = false;
							while(resetSuccessful == false) {
								
								if (newPassword.equals(confirmation)) {
									admin.setAdminPassword(Utility.getHash(newPassword));
									System.out.println(admin.getAdminUsername()+"'s password has been reset");
									
									// Save Admin password into admin.txt file
									try {
										
										PrintWriter pw = new PrintWriter(ADMIN_FILENAME);
										
										pw.write(admin.getAdminHashedPassword());
										
										pw.close();
										
									} catch (FileNotFoundException ex) {
										
									}
									
									resetSuccessful = true;
								} else {
									System.out.println("Passwords do not match, please try again.");
									newPassword = Keyboard.readString("Enter new password: ");
									confirmation = Keyboard.readString("Re-enter new password: ");
								}
							}
							authorizedAccess = true;
							
						} else {
							System.out.println("Password is incorrect, please try again");
						}
					} else {
						System.out.println("Admin username is incorrect, please try again");
					}
				}
					
				if(moreAction() == false) {
					performAction = false;
				}else {
					action = actionMenu();
				}
				
			} else if (action == 7) {
				
				// Logout
				System.out.println("You have chosen to logout. Goodbye! =)");
				performAction = false;
			}
		}
		
		// Save new player info into players.bin file
		savePlayersToBin();
		
		System.out.println("Admin session ended. Thank you =) !");
		
	}

	public static void main(String[] args) {

		// Load pre existing players (4 given ones)
		new Admin().run();
	}
}