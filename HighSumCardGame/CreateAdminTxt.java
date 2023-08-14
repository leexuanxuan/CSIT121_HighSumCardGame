import java.io.*;
import java.io.ObjectOutputStream;

public class CreateAdminTxt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Player admin = new Player("xuan", "adminpassword", 99999);
		
		try {
			
			FileOutputStream file = new FileOutputStream("admin.txt");
			ObjectOutputStream opStream = new ObjectOutputStream(file);
			
			String adminPassword = admin.getPassword();
			opStream.writeObject(adminPassword);
			opStream.close();
			
		} catch (IOException ex) {
		}
	}

}
