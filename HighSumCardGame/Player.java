import java.util.*;

public class Player extends User {

	private int chips;
	protected ArrayList<Card> cardsOnHand;
	
	public Player(String loginName, String password, int chips) {
		super(loginName, password);
		this.chips = chips;
		this.cardsOnHand = new ArrayList<Card>();
	}
	
	public void addChips(int amount) {
		this.chips += amount; //no error check
	}
	
	public int getChips() {
		return this.chips;
	}
	
	// FIXX ME FIXX ME FIXX ME FIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX ME
	public int deductChips(int amount) {
		if (this.chips > 0 && this.chips >= amount) {
			this.chips -= amount; //no error check, negative??
			return amount;
		} else {
			System.out.println("Failed deduction. Insufficient chips on hand");
			System.out.println("Please enter a valid bet");
			int bet = Keyboard.readInt("Player call, state bet: ");
			deductChips(bet);
			return bet;
		}
	}
	
	public void displayChips() {
		System.out.printf("%s, You have %d chips", getLoginName(), getChips());
	}

	public void addCard(Card card) {
		this.cardsOnHand.add(card);
	}
	
	public ArrayList<Card> getCardsOnHand() {
		return this.cardsOnHand;
	}
	
	public void showCardsOnHand() {
		System.out.println(getLoginName());
		
		for (Card card: cardsOnHand) {
			System.out.print(card + " ");
		}
		System.out.println();
	}

	public void showTotalCardsValue() {
		System.out.println("Value: " + getTotalCardsValue());
	}
	
	public int getTotalCardsValue() {
		int total = 0;
		
		for (Card card: this.cardsOnHand) {
			total += card.getValue();
		}
		return total;
	}

	public boolean checkEnteredPassword(String enteredPassword) {
		boolean canContinue = false;
		if (checkPassword(Utility.getHash(enteredPassword)) == false) {
			System.out.println("Incorrect password. Try again.");
		} else {
			canContinue = true;
		} return canContinue;
	}
	
	// FIXX ME FIXX ME FIXX ME FIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX MEFIXX ME
	public int playerBets(int bet) {

		int finalBetAmount = -1;
		
		if (bet >= 0) {
			// Player bets more than the amount of chips they have
			if (getChips() < bet) {
				while (getChips() < finalBetAmount || finalBetAmount < 0) {
					System.out.println("Invalid bet. Please enter a valid bet");
					finalBetAmount = Keyboard.readInt("Player call, state bet: ");
				}
				deductChips(finalBetAmount);
				
			} else {
				// Normal betting
				finalBetAmount = deductChips(bet);
				System.out.printf("%s, You are left with %d chips%n", getLoginName(), getChips());
			}
			
		}
		
		//reject negative amounts and ask for new bet
		if (bet < 0) {
			while (finalBetAmount < 0 || getChips() < finalBetAmount) {
				System.out.println("Invalid bet. Please enter a valid bet");
				finalBetAmount = Keyboard.readInt("Player call, state bet: ");
			}
			deductChips(finalBetAmount);
		}
		return finalBetAmount;
	}
	
	//Think of the actions that a player can take
	//Implement more related methods here
	// If action is to be performed by player > put in player class
	
	public void display() {
		super.display();
		System.out.println(this.chips);
	}
	
	public static void main(String[] args) {
		
		Player player = new Player("Xuan", "A", 100);
		
		System.out.println(player.getChips());
		player.deductChips(10);
		System.out.println(player.getChips());
		player.addChips(20);
		System.out.println(player.getChips());
		
		
		/*Deck deck = new Deck();
		deck.shuffle();
		Card card1 = deck.dealCard();
		Card card2 = deck.dealCard();
		Card card3 = deck.dealCard();
		
		player.addCard(card1);
		player.addCard(card2);
		player.addCard(card3);
		
		player.showCardsOnHand();
		player.showTotalCardsValue();
		*/
		
		
	}



}
