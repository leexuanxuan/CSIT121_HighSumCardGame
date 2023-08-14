import java.util.*;

public class Dealer extends Player{

	private Deck deck;
	
	public Dealer() {
		super("Dealer", "", 0);
		deck = new Deck();
	}
	
	public void shuffleCards() {
		System.out.println("Dealer shuffles deck.");
		deck.shuffle();
	}
	
	public void dealCardTo(Player player) {
		Card card = deck.dealCard();	// take a card out from deck
		player.addCard(card);			// Pass the card into player
	}
	
	public void showCardsOnHandFirstHidden() {
		System.out.println(getLoginName());
		
		System.out.print("<HIDDEN CARD> ");
		
		for (int i = 1; i < cardsOnHand.size(); i++ ) {
			Card card = cardsOnHand.get(i);
			System.out.print(card + " ");
		}
		System.out.println();
	}
	
	public void addUsedCardsToBackOfDeck(ArrayList<Card> playerCards, ArrayList<Card> dealerCards) {
			
			//Combine player cards and dealer cards
			for (Card card: dealerCards) {
				playerCards.add(card);
			}
			
			//shuffle cards on hand
			Random random = new Random();
			
			for (int i = 0; i < 1000; i++) {
				int indexA = random.nextInt(playerCards.size());
				int indexB = random.nextInt(playerCards.size());
				
				Card cardA = playerCards.get(indexA);
				Card cardB = playerCards.get(indexB);
				
				playerCards.set(indexA, cardB);
				playerCards.set(indexB, cardA);
			}
			
			//Add back shuffled cards into back of deck
			deck.appendCard(playerCards);
			
			playerCards.clear();
			dealerCards.clear();
	}

}
