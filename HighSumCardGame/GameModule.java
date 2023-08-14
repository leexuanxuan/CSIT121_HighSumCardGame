import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class GameModule {
	
	private Dealer dealer;
	//private Player player;
	private ArrayList<Player> players;
	private final String PLAYERS_FILENAME = "players.bin";
	
	public GameModule() {
		dealer = new Dealer();
	}
	
	public void dealerPassCards(Player player) {		// 1 card each to player & dealer
		dealer.dealCardTo(player);
		dealer.dealCardTo(dealer);
	}
	
	public void round1(Player player) {
		System.out.println("Dealer dealing cards - ROUND 1");
		System.out.println("-------------------------------------------");
		dealerPassCards(player);	// Dealer passes 1 card each to P & D so both have 2 ea.
	}
	
	public User hasHigherValueCard(Player player) {
		User userToCall = player;
		
		int playerNumOfCards = player.cardsOnHand.size() - 1;
		Card playerLatestCard = player.cardsOnHand.get(playerNumOfCards);
		int valueOfPlayerLatestCard = playerLatestCard.getValue();
		//System.out.printf("\nvalue of player card = %d" ,valueOfPlayerLatestCard);
		
		int dealerNumOfCards = dealer.cardsOnHand.size() - 1;
		Card dealerLatestCard = dealer.cardsOnHand.get(dealerNumOfCards);
		int valueOfDealerLatestCard = dealerLatestCard.getValue();
		//System.out.printf("\nvalue of dealer card = %d\n" ,valueOfDealerLatestCard);
		
		if (valueOfDealerLatestCard > valueOfPlayerLatestCard) {
			userToCall = dealer;
		} else if (valueOfDealerLatestCard == valueOfPlayerLatestCard) {
			if (dealerLatestCard.getName() == "King" || playerLatestCard.getName() == "King" ||
				dealerLatestCard.getName() == "Queen" || playerLatestCard.getName() == "Q" ||
				dealerLatestCard.getName() == "Q" || playerLatestCard.getName() == "Jack") {
				int dealerRanking = dealerLatestCard.kingQueenJack();
				int playerRanking = playerLatestCard.kingQueenJack();
				
				if (dealerRanking > playerRanking) {
					userToCall = dealer;
				} else if (dealerRanking == playerRanking) {
					// call compare suits method by getting "suit value (1 - 4)" 
					int d = dealerLatestCard.getSuitValue(player.cardsOnHand.get(dealerNumOfCards));
					int p = playerLatestCard.getSuitValue(player.cardsOnHand.get(playerNumOfCards));
					
					if (d > p) {
						userToCall = dealer;
					} else if (p > d) {
						userToCall = player;
					}
				} else if (dealerRanking < playerRanking) {
					userToCall = player;
				}
			}
			// call compare suits method by getting "suit value (1 - 4)" 
			int d = dealerLatestCard.getSuitValue(player.cardsOnHand.get(dealerNumOfCards));
			int p = playerLatestCard.getSuitValue(player.cardsOnHand.get(playerNumOfCards));
			
			if (d > p) {
				userToCall = dealer;
			} else if (p > d) {
				userToCall = player;
			}
		} else if (valueOfDealerLatestCard < valueOfPlayerLatestCard){
			userToCall = player;
		}
		return userToCall;
	}

	public int higherValueCalls(Player player) {
		User callingNext = hasHigherValueCard(player);
		int bet = 0;
		
		if (callingNext == dealer) {
			System.out.println("Dealer call, state bet: 10");
			bet = 10;
			boolean cont2 = false;
			while (cont2 == false) {
				char reply = Keyboard.readChar("Do you want to follow? [Y/N}: ");
				if (reply == 'Y' || reply == 'y') {
					//If player has sufficient amount to follow
					if (player.getChips() >= 10) {
						player.playerBets(bet);
						cont2 = true;
					} else {
						// all in
						bet = player.getChips() + 10;
						player.playerBets(player.getChips());
						cont2 = true;
					}
				} else if (reply == 'N' || reply == 'n') {
					bet = -999;	// bet = -999 indicates game end
					cont2 = true;
				} else {
					System.out.println("You have entered an invalid character.");
					cont2 = false;
				}
			}
		} else if (callingNext == player) {
			boolean cont = false;
			while (cont == false) {
				char reply2 = Keyboard.readChar("Do you want to [C]all or [Q]uit?: ");
				if (reply2 == 'c' || reply2 == 'C') {
					int betAmount = Keyboard.readInt("Player call, state bet: ");
					bet = player.playerBets(betAmount);			
					cont = true;
				} else if (reply2 == 'q' || reply2 == 'Q') {
					bet = -999;	// bet = -999 indicates game end
					cont = true;
				} else {
					System.out.println("You have entered an invalid character.");
					cont = false;
				}
			}
		}
		return bet;
	}
	
	public void win(Player winner, int chipsWon, Player player) {
		System.out.printf("%n%s Wins!!%n", winner.getLoginName());
		
		if (winner == player) {
			winner.addChips(chipsWon);
			System.out.printf("%s, You have %d chips%n", winner.getLoginName(), winner.getChips());
		} else if (winner == dealer) {
			System.out.printf("%s, You have lost %d chips%n", player.getLoginName(), chipsWon/2);
			System.out.printf("%s, You are left with %d chips.%n", player.getLoginName(), player.getChips());
			player.getChips();
			System.out.println();
		}
		
		System.out.println("Dealer shuffles used card and place behind the deck.");
		
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
	
	public void run() {
		
		players = new ArrayList<Player>();
		loadPlayers();

		System.out.println("HighSum GAME");
		System.out.println("===========================================");
		//get Password and prompt for password again if it's incorrect.
		
		// Check if username exists in players ArrayList, if yes - enter password and pw check
		Player player = players.get(0);
		String currentPlayerUsername = "dummyName";
		
		boolean validLogin = false;
		while (validLogin == false) {
			String playerLogin = Keyboard.readString("Enter Login name> ");
			
			for (Player p: players) {
				if (p.getLoginName().equals(playerLogin)) {
					boolean rightPassword = false;
					
					while(rightPassword == false) {
						rightPassword = p.checkEnteredPassword(Keyboard.readString("Enter Password> "));
					}
					
					currentPlayerUsername = p.getLoginName();
					validLogin = rightPassword;
				} 
			}
			
			if (validLogin == false) {
				System.out.println("Invalid Login ID - no such username");
			}
			System.out.println();
		}
	
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getLoginName().equals(currentPlayerUsername)) {
				player = players.get(i);
			}
		}
		

		
		/*
		
		boolean canStart = false;
		do {
			canStart = player.checkEnteredPassword(Keyboard.readString("Enter Password> "));
		} while (canStart == false);
		System.out.println();
		*/
		
		boolean allIn = false;
		boolean wantsNextGame = true;
		wantsNextGame = validLogin;
		while (wantsNextGame) {
			wantsNextGame = true;
			
			//Game starts here!
			System.out.println();
			System.out.println("HighSum GAME");		//header
			System.out.println("===========================================");
			player.displayChips();		//display player current chips
			System.out.println("\n-------------------------------------------");
			System.out.print("Game starts - ");
			dealer.shuffleCards();		//shuffle
			System.out.println("-------------------------------------------");
			
			// Tracks all in round in the event allIn happens
			int allInRound = 0;
			
			//Track Bet On Table + round
			int betOnTable = 0;
			int round = 1;
			
			while (round <= 5) {
				if (round == 1) {
					//First Round Betting Amount (no call/ quit/ follow)
					dealerPassCards(player);		//pass card (1 to player, 1 to dealer)
					round1(player);
					
					dealer.showCardsOnHandFirstHidden();
					System.out.println();
					player.showCardsOnHand();
					player.showTotalCardsValue();
					System.out.println();
					
					User callingNext = hasHigherValueCard(player);
					int bet = 0;
					if (callingNext == dealer) {
						System.out.println("Dealer call, state bet: 10");
						if (player.getChips()>10) {
							bet = 10;
							player.playerBets(bet);
							betOnTable += bet * 2;
							System.out.printf("\nBet on table : %d", betOnTable);
						} else if (player.getChips()<10) {
							// all in
							bet = player.getChips() + 10;
							player.playerBets(player.getChips());
						}
				
						} else if (callingNext == player) {
						bet = Keyboard.readInt("Player call, state bet: ");
						int betAmount = player.playerBets(bet);
						
						// ***********ALL IN FEATURE***********
						if (player.getChips() == 0) {
							System.out.println();
							System.out.println("-------------------------------------------");
							System.out.println( player.getLoginName() + ", you've chosen to ALL IN");
							betOnTable = betAmount*2;
							System.out.println("Winning will grant you " + betOnTable + " chips.");
							allIn = true;
							allInRound = 1;
							round = 6;	// Exit "Normal game"
							break;
						}
						
						betOnTable += betAmount * 2;
						System.out.printf("\nBet on table : %d%n", betOnTable);
					}
				} else  if (round == 2 || round == 3 || round == 4 ) {
				
					//Player Can Quit from round 2 onwards
					
					// Header
					System.out.println("\n-------------------------------------------");
					System.out.printf("Dealer dealing cards - ROUND %d", round);
					System.out.println("\n-------------------------------------------");
					
					// Deal 1 card each, Display Cards on hand
					dealerPassCards(player);
					dealer.showCardsOnHandFirstHidden();
					System.out.println();
					player.showCardsOnHand();
					player.showTotalCardsValue();
					System.out.println();
					
					//Compare Cards
					int newBet = higherValueCalls(player);
					
					
					if (newBet == -999) {
						//Player Decided to Quit the game.
						win(dealer, betOnTable, player);
						round = 5;
					} else if (player.getChips() == 0 && newBet < 20) {
						// player decided to all in when he has less than 10 chips
						// Only happens when dealer's turn to call and player has to ALL IN
						System.out.println();
						System.out.println("-------------------------------------------");
						System.out.println( player.getLoginName() + ", you've chosen to ALL IN");
						betOnTable += (newBet);
						System.out.println("Winning will grant you " + (betOnTable) + " chips.");
						allIn = true;
						allInRound = round;
						round = 6;	// Exit "Normal game"
						
					}  else if (player.getChips() == 0){
						// player decided to all in (bet amount isn't x2 like usual)
						System.out.println();
						System.out.println("-------------------------------------------");
						System.out.println( player.getLoginName() + ", you've chosen to ALL IN");
						betOnTable += newBet*2;		// I MADE CHANGES HERE
						System.out.println("Winning will grant you " + (betOnTable) + " chips.");
						allIn = true;
						allInRound = round;
						round = 6;	// Exit "Normal game"
						break;
					
					} else {
						betOnTable += newBet * 2;
						System.out.printf("\nBet on table : %d%n", betOnTable);
					}
				} else {
					
					// Round 5 is Final round. Winner will be revealed.
					// Header
					System.out.println("\n-------------------------------------------");
					System.out.println("Game End - Dealer reveal hidden cards");
					System.out.println("-------------------------------------------");
					
					//Dealer reveals card
					dealer.showCardsOnHand();
					dealer.showTotalCardsValue();
					System.out.println();
					player.showCardsOnHand();
					player.showTotalCardsValue();
					
					//Determine winner
					if (player.getTotalCardsValue() > dealer.getTotalCardsValue()) {
						win(player, betOnTable, player);
					} else if (player.getTotalCardsValue() == dealer.getTotalCardsValue()){
						System.out.println("\nIt's a tie");
						player.addChips(betOnTable/2);
						System.out.println(player.getLoginName() + ", you have " + player.getChips() +" chips");
						System.out.println("Dealer shuffles used card and place behind the deck.");
					} else {
						win(dealer, betOnTable, player);
					}
				}
				round++;
			}
			
			// All in method
			if (allIn == true) {
				// all in round 1 = need 3 extra cards 
				// all in round 2 = need 2 extra cards
				for (int i = 1; i < (5-allInRound); i++) {
					System.out.println("-------------------------------------------");
					System.out.printf("Pass %d: ", i);
					
					// Deal 1 card each, Display Cards on hand
					dealerPassCards(player);
					dealer.showCardsOnHandFirstHidden();
					System.out.println();
					player.showCardsOnHand();
					player.showTotalCardsValue();
					System.out.println();
				}
				
			
				// Round 5 is Final round. Winner will be revealed.
				// Header
				System.out.println("\n-------------------------------------------");
				System.out.println("Game End - Dealer reveal hidden cards");
				System.out.println("-------------------------------------------");
				
				//Dealer reveals card
				dealer.showCardsOnHand();
				dealer.showTotalCardsValue();
				System.out.println();
				player.showCardsOnHand();
				player.showTotalCardsValue();
				
				//Determine winner
				if (player.getTotalCardsValue() > dealer.getTotalCardsValue()) {
					win(player, betOnTable,player);
				} else if (player.getTotalCardsValue() == dealer.getTotalCardsValue()){
					System.out.println("\nIt's a tie");
					player.addChips(betOnTable/2);
					System.out.println(player.getLoginName() + ", you have " + player.getChips() +" chips");
					System.out.println("Dealer shuffles used card and place behind the deck.");
				} else {
					win(dealer, betOnTable,player);
				}
			}
			
			// Set this to true so Console won't ask if player
			// Wants to start next game
			boolean invalidChar = true;
			
			if (player.getChips() == 0) {
				System.out.println("You do not have any chips left :(");
				System.out.println("Thank you for playing ! :)");
				wantsNextGame = false;
				invalidChar = false;
			}
			
			// Ask if player wants next Game.
			System.out.println("-------------------------------------------");
			
			while (invalidChar) {
				invalidChar = false;
				
				char reply3 = Keyboard.readChar("Next Game? (Y/N) > ");
				if (reply3 == 'Y' || reply3 == 'y') {
					//Return All cards
					dealer.addUsedCardsToBackOfDeck(player.getCardsOnHand(), dealer.getCardsOnHand());
					wantsNextGame = true;
				} else if (reply3 == 'N' || reply3 == 'n') {
					System.out.println("Thank you for playing ! :)");
					wantsNextGame = false;
				} else {
					System.out.println("You have entered an invalid character.");
					invalidChar = true;
				}
			}
		}

		savePlayersToBin();
	}

	public static void main(String[] args) {
		
		GameModule app = new GameModule();
		app.run();
	}

}