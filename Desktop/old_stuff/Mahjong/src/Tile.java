
public class Tile {
	
	private int number;
	private String suit; // Circle, bamboo, character, wind, dragon, or honor
	
	public Tile(int number, String suit) {
		this.number = number;
		this.suit = suit;
	}
	
	public Tile() {
		
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public void tileToString() {
		System.out.println(number + " " + suit);
		return;
	}
	
	public boolean tileEquals(Tile tile2) {
		String name = this.getSuit();
		if (this.getSuit().charAt(0) == ' ') {
			name = name.substring(1, name.length());
		}
		if (this.getNumber() == tile2.getNumber() && name.equalsIgnoreCase(tile2.getSuit())) {
	return true;
	}
	
	return false;
}
}
