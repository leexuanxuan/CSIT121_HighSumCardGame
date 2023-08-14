
import java.util.*;

public class Deck {

	private ArrayList<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>();
		String[] suits = {"Heart", "Diamond", "Spade", "Club"};

		for (int i = 0; i < suits.length; i++) {
			String suit = suits[i];
			Card card = new Card(suit,"Ace",1);
			cards.add(card);
			
			for (int n = 2; n <=10; n++)  {
				Card oCard = new Card(suit, "" + n, n);
				cards.add(oCard);
			}
			
			Card jackCard = new Card(suit, "Jack", 10);
			cards.add(jackCard);
			
			Card queenCard = new Card(suit, "Queen", 10);
			cards.add(queenCard);
			
			Card kingCard = new Card(suit, "King", 10);
			cards.add(kingCard);
		}
	}
	
	public Card dealCard() {
		return cards.remove(0);
	}
	
	//add back one card
	public void appendCard(Card card) {
		cards.add(card);
	}
	
	//add back arraylist of cards
	public void appendCard(ArrayList<Card> cards) {
		for (Card card: cards) {
			this.cards.add(card);
		}
	}
	
	public void shuffle() {
		Random random = new Random();
		
		for (int i = 0; i < 1000; i++) {
			int indexA = random.nextInt(cards.size());
			int indexB = random.nextInt(cards.size());
			
			Card cardA = cards.get(indexA);
			Card cardB = cards.get(indexB);
			
			cards.set(indexA, cardB);
			cards.set(indexB, cardA);
		}
	}
	
	public void showCards() {
		for (Card card: cards) {
			System.out.println(card);
		}
	}
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		//deck.shuffle();
		Card card1 = deck.dealCard();
		Card card2 = deck.dealCard();
		Card card3 = deck.dealCard();
		deck.showCards();
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		deck.appendCard(cards);
		System.out.println();
		
		deck.showCards();
	}

}
