import java.io.*;
import java.io.ObjectOutputStream;

public class CreatePlayersBin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Player player1 = new Player("BlackRanger", "Password1", 1000);
		Player player2 = new Player("BlueKnight", "Password2", 1500);
		Player player3 = new Player("IcePeak", "Password3", 100);
		Player player4 = new Player("GoldDigger", "Password4", 2200);
		
		try {
			
			FileOutputStream file = new FileOutputStream("players.bin");
			ObjectOutputStream opStream = new ObjectOutputStream(file);
			
			opStream.writeObject(player1);
			opStream.writeObject(player2);
			opStream.writeObject(player3);
			opStream.writeObject(player4);
			opStream.close();
			
		} catch (IOException ex) {
			
		}
	}

}
