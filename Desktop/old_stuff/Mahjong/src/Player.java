import java.util.ArrayList;
import java.util.Scanner;
public class Player {
	
	private ArrayList<Tile> playerDeck = new ArrayList<Tile>();
	private ArrayList<Tile> declaredTiles = new ArrayList<Tile>();
	private String windDirection;
	private String name;
	private boolean fishing = false;
	private boolean mahjong = false;
	private boolean hasChow = false;
	private boolean hasPair = false;
	private int numberOfSets = 0;
	private String declaredSuit = "";
	private int points = 0;
	

	public Player(String name) {
		this.name = name;
	}
	
	public void setDeck(ArrayList<Tile> deck) {
		playerDeck = deck;
	}
	
	public void setDeclaredTiles(ArrayList<Tile> deck) {
		declaredTiles = deck;
	}
	
	public void setWind(String wind) {
		windDirection = wind;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setHasChow() {
		hasChow = true;
	}
	
	public void setHasPair() {
		hasPair = true;
	}
	
	public void addSet() {
		++numberOfSets;
	}
	
	public void setFishing() {
		fishing = true;
	}
	
	public void setMahjong() {
		mahjong = true;
	}
	
	public void setDeclaredSuit(String suit) {
		String[] doNotDeclare = {"White dragon", "Green dragon", "Red dragon", "North wind", "South wind", "East wind", "West wind"};
		for (String word: doNotDeclare) {
			if (word.equalsIgnoreCase(suit)) {
				return;
			}
		}
		declaredSuit = suit;
	}
	
	public ArrayList<Tile> getDeck() {
		return playerDeck;
	}
	
	public ArrayList<Tile> getDeclaredTiles() {
		return declaredTiles;
	}
	
	public String getWind() {
		return windDirection;
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean getHasChow() {
		return hasChow;
	}
	
	public boolean getHasPair() {
		return hasPair;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDeclaredSuit() {
		return declaredSuit;
	}
	
	public boolean getFishing() {
		return fishing;
	}
	
	public boolean getMahjong() {
		return mahjong;
	}
	
	public void addTile(Tile tile) {
		playerDeck.add(tile);
		return;
	}
	
	public void removeTile(Tile tile, Scanner scnr) {
		boolean valid = false;;
		for (int i = 0; i < playerDeck.size(); ++i) {
			if (tile.tileEquals(playerDeck.get(i))) {
				playerDeck.remove(i);
				valid = true;
				break;
			}
		}
		if (!valid) {
			System.out.println("You don't have that tile. Enter a valid tile.");
			System.out.println("Make sure to include the 0 if it's a dragon or wind.");
			this.displayHand();
			int tileNumber = scnr.nextInt();
			String tileName = scnr.nextLine();
			
			
			this.removeTile(new Tile(tileNumber, tileName), scnr);
		}
		
		return;
	}
	
	public void displayHand() {
		this.sortHand();
		for (int i = 0; i < playerDeck.size(); ++i) {
			playerDeck.get(i).tileToString();
		}
	}
	
	public void displayDeclaredTiles() {
		for (int i = 0; i < declaredTiles.size(); ++i) {
			declaredTiles.get(i).tileToString();
		}
	}
	
	public void unSortedDisplay() { // tester
			for (int i = 0; i < playerDeck.size(); ++i) {
				playerDeck.get(i).tileToString();
			}
		}
	
	
	public void sortHand() {
		String test = "";
		Tile toAdd = new Tile();
		Tile temp = new Tile();
		int secondIndex = 0;
		boolean needSwitch = false;
		for (int i = 0; i < playerDeck.size() - 1; ++i) {
			test = playerDeck.get(i).getSuit();
			toAdd = playerDeck.get(i);
			needSwitch = false;
			for (int j = i + 1; j < playerDeck.size(); ++j) {
				if (test.compareTo(playerDeck.get(j).getSuit()) > 0 || test.compareTo(playerDeck.get(j).getSuit()) == 0 && toAdd.getNumber() > playerDeck.get(j).getNumber()) {
					test = playerDeck.get(j).getSuit();
					toAdd = playerDeck.get(j);
					secondIndex = j;
					needSwitch = true;
					//System.out.println("Need to swap " + test + " and " + playerDeck.get(j).getSuit());
				}
				
				
				
			}
			if (needSwitch) {
				temp = toAdd;
				playerDeck.remove(secondIndex);
				playerDeck.add(secondIndex, playerDeck.get(i));
				playerDeck.remove(i);
				playerDeck.add(i, temp);
				//System.out.println("Swapping index " + i + " and " + secondIndex);
				//this.unSortedDisplay();
				}
			
		}
		return;
	}
	
	public int tileOccurences(Tile tile) {
		int counter = 0;
		for (int i = 0; i < playerDeck.size(); ++i) {
			if (playerDeck.get(i).tileEquals(tile)) {
				++counter;
			}
		}
		return counter;
	}
	
	public int declaredTileOccurences(Tile tile) {
		int counter = 0;
		for (int i = 0; i < declaredTiles.size(); ++i) {
			if (declaredTiles.get(i).tileEquals(tile)) {
				++counter;
			}
		}
		return counter;
	}
	
	public boolean isMahjong() {
		if (hasChow && hasPair && numberOfSets == 3) {
			
			mahjong = true;
			return true;
		}
			return false;
	}
	
	public boolean isFishing() {
		if (declaredTiles.size() >= 12 && hasChow) {
			this.setFishing();
			return true;
		}
		return false;
	}
	
	public int calculatePoints() {
		int total = 0;
		total += numberOfSets * 3;
		total *= (this.declaredTileOccurences(new Tile(0, "White Dragon")) / 3) * 2;
		total *= (this.declaredTileOccurences(new Tile(0, "Red Dragon")) / 3) * 2;
		total *= (this.declaredTileOccurences(new Tile(0, "Green Dragon")) / 3) * 2;
		if (declaredTiles.size() > 14) {
			total *= 2;
		}
		points = total;
		return total;
	}
}
