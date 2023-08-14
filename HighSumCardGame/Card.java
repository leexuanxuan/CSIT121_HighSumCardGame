
public class Card {

	private String suit;
	private String name;
	private int value;
	
	public Card(String suit, String name, int value) {
		this.suit = suit;
		this.name = name;
		this.value = value;
	}

	public String getSuit() {
		return suit;
	}

	public String getName() {
		return name;
	}
	
	public int getValue() {
		return value;
	}

	public int getSuitValue(Card card) {
		int suitValue = 0;
		if (getSuit() == "Spade") {
			suitValue = 4;
		} else if (getSuit() == "Heart") {
			suitValue = 3;
		} else if (getSuit() == "Club") {
			suitValue = 2;
		} else if (getSuit() == "Spade") {
			suitValue = 1;
		}
		return suitValue;
	}
	
	public int kingQueenJack() {
		int ranking = 0;
		if (getName() == "King") {
			ranking = 3;
		} else if (getName() == "Queen") {
			ranking = 2;
		} else if (getName() == "Jack") {
			ranking = 1;
		} 
		return ranking;
	}
	
	public String toString() {
		return "<"+this.suit+" "+this.name+">";
	}
	
	public static void main(String[] args) {
		Card card = new Card("Heart", "Ace", 1);
		System.out.println(card);
	}

}
