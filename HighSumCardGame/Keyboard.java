public class Keyboard {

	public static String readString(String prompt) {
		System.out.print(prompt);
		return new java.util.Scanner(System.in).nextLine();
	}

	public static int readInt(String prompt) {
		int input = 0;
		boolean valid = false;
		while (!valid) {
			try {
				input = Integer.parseInt(readString(prompt));
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("*** Please enter an integer ***");
			}
		}
		return input;
	}

	public static double readDouble(String prompt) {
		double input = 0;
		boolean valid = false;
		while (!valid) {
			try {
				input = Double.parseDouble(readString(prompt));
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("*** Please enter a double ***");
			}
		}
		return input;
	}

	public static float readFloat(String prompt) {
		float input = 0;
		boolean valid = false;
		while (!valid) {
			try {
				input = Float.parseFloat(readString(prompt));
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("*** Please enter a float ***");
			}
		}
		return input;
	}

	public static long readLong(String prompt) {
		long input = 0;
		boolean valid = false;
		while (!valid) {
			try {
				input = Long.parseLong(readString(prompt));
				valid = true;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("*** Please enter a long ***");
			}
		}
		return input;
	}

	public static char readChar(String prompt) {
		char input = 0;
		boolean valid = false;
		while (!valid) {
			String temp = readString(prompt);
			if (temp.length() != 1) {
				System.out.println("*** Please enter a character ***");
			} else {
				input = temp.charAt(0);
				valid = true;
			}
		}
		return input;
	}

	public static boolean readBoolean(String prompt) {
		boolean valid = false;
		while (!valid) {
			String input = readString(prompt);
			if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y") || input.equalsIgnoreCase("true")
					|| input.equalsIgnoreCase("t")) {
				return true;
			} else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n") || input.equalsIgnoreCase("false")
					|| input.equalsIgnoreCase("f")) {
				return false;
			} else {
				System.out.println("*** Please enter Yes/No or True/False ***");
			}
		}
		return false;
	}

	public static java.util.Date readDate(String prompt) {
		java.util.Date date = null;
		boolean valid = false;
		while (!valid) {
			try {
				String input = readString(prompt).trim();
				if (input.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d")) {
					int day = Integer.parseInt(input.substring(0, 2));
					int month = Integer.parseInt(input.substring(3, 5));
					int year = Integer.parseInt(input.substring(6, 10));
					java.util.Calendar cal = java.util.Calendar.getInstance();
					cal.setLenient(false);
					cal.set(year, month - 1, day, 0, 0, 0);
					date = cal.getTime();
					valid = true;
				} else {
					System.out.println("*** Please enter a date (DD/MM/YYYY) ***");
				}
			} catch (IllegalArgumentException e) {
				System.out.println("*** Please enter a date (DD/MM/YYYY) ***");
			}
		}
		return date;
	}

	private static String quit = "0";

	public static int getUserOption(String title, String[] menu) {
		displayMenu(title, menu);
		int choice = Keyboard.readInt("Enter Choice --> ");
		while (choice > menu.length || choice < 0) {
			choice = Keyboard.readInt("Invalid Choice, Re-enter --> ");
		}
		return choice;
	}

	private static void displayMenu(String title, String[] menu) {
		line(80, "=");
		System.out.println(title.toUpperCase());
		line(80, "-");
		for (int i = 0; i < menu.length; i++) {
			System.out.println("[" + (i + 1) + "] " + menu[i]);
		}
		System.out.println("[" + quit + "] Quit");
		line(80, "-");
	}

	public static void line(int len, String c) {
		System.out.println(String.format("%" + len + "s", " ").replaceAll(" ", c));
	}
}
