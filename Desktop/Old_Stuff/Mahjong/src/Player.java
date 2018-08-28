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
		int multiplier = 0;
		total += numberOfSets * 3;
		multiplier += (this.declaredTileOccurences(new Tile(0, "White Dragon")) / 3) * 2;
		multiplier += (this.declaredTileOccurences(new Tile(0, "Red Dragon")) / 3) * 2;
		multiplier += (this.declaredTileOccurences(new Tile(0, "Green Dragon")) / 3) * 2;
		if (declaredTiles.size() > 14) {
			multiplier += 2;
		}
		
		if (multiplier != 0) {
			points *= multiplier;
		}
		points = total;
		return total;
	}
	
	public Tile playOneTurn(ArrayList<Tile> deck, Scanner scnr, boolean turnSkip) {
		if (isFishing()) {
			System.out.println("You are fishing!");
		}
		if (!turnSkip) {
		Tile draw = deck.get(0);
		deck.remove(0);
		addTile(draw);
		System.out.print("The tile you drew was a ");
		draw.tileToString();
		}
		if (getDeclaredTiles().size() > 0) {
			System.out.println("Your declared tiles:");
			displayDeclaredTiles();
		}
		System.out.println("Your hand: ");
		displayHand();
		
		if (!turnSkip) {
		System.out.println("Do you have anything you want to declare? Type yes or no.");
		String answer = scnr.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
			declareSomething(deck, scnr, this);
		}
		if (isMahjong()) {
			return new Tile(0, "");
		}
		}
		
		System.out.println("What do you want to discard? Enter a tile.");
		System.out.println("Make sure to include the 0 if it's a dragon or wind.");
		int tileNumber = scnr.nextInt();
		String tileName = scnr.nextLine();
		Tile discard = new Tile(tileNumber, tileName);
		removeTile(discard, scnr);
		System.out.println("You discarded a " + tileNumber + tileName + ".");
		return discard;
	}
	
	public static void declareSomething(ArrayList<Tile> deck, Scanner scnr, Player player) {
		System.out.println("What kind of set are you declaring?");
		if (player.isFishing()) {
			System.out.println("Pair: a set of 2 matching numbers.");
		}
		else {
		System.out.println("Chow: a run of numbers like 1 2 3");
		System.out.println("Pung: a set of 3 matching numbers");
		System.out.println("Kong: a set of 4 matching numbers");
		}
		// 
		String answer = scnr.nextLine();
		if (answer.equalsIgnoreCase("Chow")) {
			declareChow(scnr, player);
		}
		else if (answer.equalsIgnoreCase("Pung")) {
			declarePung(scnr, player);
		}
		else if (answer.equalsIgnoreCase("Kong")) {
			declareKong(scnr, player);
		}
		else if (answer.equalsIgnoreCase("Pair")) {
			userDeclarePair(scnr, player);
		}
		else {
			System.out.println("Enter a valid answer.");
			declareSomething(deck, scnr, player);
			
		}
		if (player.isMahjong()) {
			return;
		}
		System.out.println("Your declared tiles: ");
		player.displayDeclaredTiles();
		return;
	}
	
	public static void declareChow(Scanner scnr, Player player) {
		System.out.println("Which suit is the thing you're declaring a part of?");
		String suit = scnr.nextLine();
		if (suit.equalsIgnoreCase("cancel")) {
			return;
		}
		if (!isSuitValid(suit, player)) { // Don't want sets of different suits
			declareChow(scnr, player);
			return;
		}
		System.out.println("Enter the numbers of the tiles that are part of this set.");
		int firstNumber = scnr.nextInt();
		int secondNumber = scnr.nextInt();
		int thirdNumber = scnr.nextInt();
		scnr.nextLine();
		
		Tile firstTile = new Tile(firstNumber, suit);
		Tile secondTile = new Tile(secondNumber, suit);
		Tile thirdTile = new Tile(thirdNumber, suit);
		
		if (secondNumber - firstNumber != 1 || thirdNumber - secondNumber != 1 || thirdNumber - firstNumber != 2) {
			System.out.println("Invalid numbers. Enter \"continue\" to try again or \"cancel\" to cancel.");
			String answer = scnr.nextLine();
			if (answer.equalsIgnoreCase("continue")) {
				declareSomething(player.getDeck(), scnr, player);
				return;
			}
			else {
				return;
			}
		}
		if (!(player.tileOccurences(firstTile) >= 1) || !(player.tileOccurences(secondTile) >= 1) || !(player.tileOccurences(thirdTile) >= 1)) {
			System.out.println("Invalid numbers. Enter \"continue\" to try again or \"cancel\" to cancel.");
			String answer = scnr.nextLine();
			if (answer.equalsIgnoreCase("continue")) {
				declareSomething(player.getDeck(), scnr, player);
				return;
			}
			else {
				return;
			}
		}
		addChow(firstTile, secondTile, thirdTile, player);
		return;
	}
	
	public static void declarePung(Scanner scnr, Player player) {
		System.out.println("Which suit is the thing you're declaring a part of?");
		String suit = scnr.nextLine();
		if (suit.equalsIgnoreCase("cancel")) {
			return;
		}
		if (!isSuitValid(suit, player)) {
			declarePung(scnr, player);
			return;
		}
		System.out.println("Enter the number of the matching tiles.");
		int number = scnr.nextInt();
		scnr.nextLine();
		Tile tileToMatch = new Tile(number, suit);
		if (!(player.tileOccurences(tileToMatch) >= 3)) {
			System.out.println("Invalid numbers. Enter \"continue\" to try again or \"cancel\" to cancel.");
			String answer = scnr.nextLine();
			if (answer.equalsIgnoreCase("continue")) {
				declareSomething(player.getDeck(), scnr, player);
				return;
			}
			else {
				return;
			}
		}
		
		addPung(tileToMatch, player);
		
		if (player.getDeclaredSuit().equals("")) {
			player.setDeclaredSuit(tileToMatch.getSuit());
		}
		
		System.out.println("You declared a pung!");
		return;
	}
	
	public static void declareKong(Scanner scnr, Player player) {
		System.out.println("Which suit is the thing you're declaring a part of?");
		String suit = scnr.nextLine();
		if (suit.equalsIgnoreCase("cancel")) {
			return;
		}
		if (!isSuitValid(suit, player)) {
			declareKong(scnr, player);
			return;
		}
		System.out.println("Enter the numbers of the tiles that are part of this set.");
		int number = scnr.nextInt();
		scnr.nextLine();
		Tile tileToMatch = new Tile(number, suit);
		if (!(player.tileOccurences(tileToMatch) == 4)) {
			System.out.println("Invalid numbers. Enter \"continue\" to try again or \"cancel\" to cancel.");
			String answer = scnr.nextLine();
			if (answer.equalsIgnoreCase("continue")) {
				declareSomething(player.getDeck(), scnr, player);
				return;
			}
			else {
				return;
			}
		}
		
		addKong(tileToMatch, player);
		
			if (player.getDeclaredSuit().equals("")) {
				player.setDeclaredSuit(tileToMatch.getSuit());
			}
		System.out.println("You declared a kong!");
		return;
	}
	
	public boolean discardTile(Scanner scnr, Tile discard) {
		boolean canTake = false;
		boolean willTake = false;
		System.out.println("Will you take it? Enter yes or no.");
		System.out.println("Remember you can only take it if you can declare something with it.");
		String answer = scnr.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
			if (!isSuitValid(discard.getSuit(), this)) {
				System.out.print("You can't take that, you already declared ");
				System.out.println(getDeclaredSuit());
				return false;
			} // end invalid statement
			
			
			// Pair declaration if fishing
			// Review rules
			if (isFishing()) {
				if (discard.tileEquals(getDeck().get(0))) {
					addPair(discard, getDeck().get(0), this);
					System.out.print("You declared a pair of ");
					discard.tileToString();
					System.out.println("Mahjong!");
					return true;
				}
				else {
					System.out.println("You can't declare a pair with that.");
					return false;
				}
			}
			
			// Kong declaration
			if (tileOccurences(discard) == 3) {
				willTake = discardKong(discard, scnr, this);
				if (willTake) {
					return willTake;
				}
			}
			
			// Pung declaration
			else if (tileOccurences(discard) >= 2) {
				willTake = discardPung(discard, scnr, this);
				if (willTake) {
					return willTake;
				}
			} // end pung part
			
				
			
			
			
			else if (chowPossible(this, discard) != 0) {
				int type = chowPossible(this, discard);
				willTake = discardChow(type, discard, scnr, this);
				if (willTake) {
					return willTake;
				}
			} // end chow part
			
			
			else {
				System.out.println("There is nothing you can declare.");
					return false;
				}
		} // end wanting to take tile
		
		
		else {
			return false;
		}
		return canTake;
	}
	
	public static boolean discardChow(int type, Tile discard, Scanner scnr, Player player) {
		String answer;
		//boolean willTake = false;
		
		if (type == 1 || type == 4 || type == 5 || type == 7) {
			System.out.println("Would you like to declare a chow consisting of");
			discard.tileToString();
			System.out.println((discard.getNumber() + 1) + " " + discard.getSuit());
			System.out.println((discard.getNumber() + 2) + " " + discard.getSuit());
			System.out.println("Enter yes or no.");
			answer = scnr.nextLine();
			if (answer.equalsIgnoreCase("yes")) {
				addDiscardChow(new Tile(discard.getNumber() + 1, discard.getSuit()), new Tile(discard.getNumber() + 2, discard.getSuit()), discard, 1, player);
				System.out.println("You declared a chow!");
				return true;
			} // done adding chow
		} // finished with type 1
		
		if (type == 2 || type == 4 || type == 6 || type == 7) {
			System.out.println("Would you like to declare a chow consisting of");
			System.out.println((discard.getNumber() - 1) + " " + discard.getSuit());
			discard.tileToString();
			System.out.println((discard.getNumber() + 1) + " " + discard.getSuit());
			System.out.println("Enter yes or no.");
			answer = scnr.nextLine();
			if (answer.equalsIgnoreCase("yes")) {
				addDiscardChow(new Tile(discard.getNumber() - 1, discard.getSuit()), new Tile(discard.getNumber() + 1, discard.getSuit()), discard, 2, player);
				return true;
			} // done adding chow
		} // finished with type 2
		
		if (type == 3 || type == 5 || type == 6 || type == 7) {
			System.out.println("Would you like to declare a chow consisting of");
			System.out.println((discard.getNumber() - 2) + " " + discard.getSuit());
			System.out.println((discard.getNumber() - 1) + " " + discard.getSuit());
			discard.tileToString();
			System.out.println("Enter yes or no.");
			answer = scnr.nextLine();
			if (answer.equalsIgnoreCase("yes")) {
				addDiscardChow(new Tile(discard.getNumber() - 2, discard.getSuit()), new Tile(discard.getNumber() - 1, discard.getSuit()), discard, 3, player);
				return true;
			} // done adding chow
		}
		return false;
	}
	
	public static boolean discardPung(Tile discard, Scanner scnr, Player player) {
		String answer;
		boolean willTake = false;
		
		System.out.print("Would you like to declare a pung of ");
		System.out.println(discard.getNumber() + " " + discard.getSuit() + "?");
		answer = scnr.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
			willTake = true;
			player.getDeclaredTiles().add(discard);
			int counter = 0;
			for (int i = 0; i < player.getDeck().size() && counter < 2; ++i) {
				if (player.getDeck().get(i).tileEquals(discard)) {
					player.getDeclaredTiles().add(discard);
					player.getDeck().remove(i);
					++counter;
					--i;
				} // end adding tile
			} // end for loop
			if (player.getDeclaredSuit().equals("")) {
				player.setDeclaredSuit(discard.getSuit());
			} // end setting declared suit
			if (willTake) {
				return willTake;
			} // end setting boolean
		} // end adding pung
		return willTake;
	}
	
	public static boolean discardKong(Tile discard, Scanner scnr, Player player) {
		String answer;
		boolean willTake = false;
		System.out.print("Would you like to declare a kong of ");
		System.out.println(discard.getNumber() + " " + discard.getSuit() + "?");
		answer = scnr.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
			willTake = true;
			player.getDeclaredTiles().add(discard);
			int counter = 0;
			for (int i = 0; i < player.getDeck().size() && counter < 3; ++i) {
				if (player.getDeck().get(i).tileEquals(discard)) {
					player.getDeclaredTiles().add(discard);
					player.getDeck().remove(i);
					++counter;
					--i;
				} 
			} // end declaring tiles
			if (player.getDeclaredSuit().equals("")) {
				player.setDeclaredSuit(discard.getSuit());
			}
			// if you did the stuff correctly, break
		} // end if you want to add kong
		return willTake;
	}
	
	public static void addChow(Tile firstTile, Tile secondTile, Tile thirdTile, Player player) {
		for (int i = 0; i < player.getDeck().size(); ++i) {
			if (player.getDeck().get(i).tileEquals(firstTile)) {
				player.getDeclaredTiles().add(firstTile);
				player.getDeck().remove(i);
				break;
			}
		}
		for (int i = 0; i < player.getDeck().size(); ++i) {
			if (player.getDeck().get(i).tileEquals(secondTile)) {
				player.getDeclaredTiles().add(secondTile);
				player.getDeck().remove(i);
				break;
			}
		}
		for (int i = 0; i < player.getDeck().size(); ++i) {
			if (player.getDeck().get(i).tileEquals(thirdTile)) {
				player.getDeclaredTiles().add(thirdTile);
				player.getDeck().remove(i);
				break;
			}
		}
		if (player.getDeclaredSuit().equals("")) {
			player.setDeclaredSuit(firstTile.getSuit());
		}
		
		System.out.println("You declared a chow!");
		player.setHasChow();
		return;
	}
	
	public static void userDeclarePair(Scanner scnr, Player player) {
		System.out.println("Which suit is the thing you're declaring a part of?");
		String suit = scnr.nextLine();
		if (suit.equalsIgnoreCase("cancel")) {
			return;
		}
		if (!isSuitValid(suit, player)) {
			userDeclarePair(scnr, player);
			return;
		}
		System.out.println("Enter the number of the matching tiles.");
		int number = scnr.nextInt();
		scnr.nextLine();
		Tile tileToMatch = new Tile(number, suit);
		if (!(player.tileOccurences(tileToMatch) >= 2)) {
			System.out.println("Invalid numbers. Enter \"continue\" to try again or \"cancel\" to cancel.");
			String answer = scnr.nextLine();
			if (answer.equalsIgnoreCase("continue")) {
				declareSomething(player.getDeck(), scnr, player);
				return;
			}
			else {
				addPair(tileToMatch, tileToMatch, player);
				return;
			}
		}
	}
	
	public void dealTiles(ArrayList<Tile> deck) {
		ArrayList<Tile> hand = new ArrayList<Tile>();
		for (int i = 0; i < 13; ++i) {
			hand.add(deck.get(i));
		}
		this.setDeck(hand);
		deck.subList(0, 13).clear();
	return;
	}

	public static boolean isSuitValid(String suit, Player player) {
		String[] validSuits = {"white dragon", "red dragon", "green dragon", "north wind", "south wind", "east wind", "west wind"};
		if (player.getDeclaredSuit().equals("")) {
			return true;
		}
		
		for (String sute: validSuits) {
			if (suit.equalsIgnoreCase(sute)) {
				return true;
			}
		}
		
		if (!player.getDeclaredSuit().equalsIgnoreCase(suit)) {
			
			// invalid suit
			// TODO: Fix this mess
			//System.out.println("You already declared " + player.getDeclaredSuit() + ".");
			//System.out.println("Enter your declared suit or type \"cancel\" to cancel.");
			return false;
		}
		return true;
	}

	public static int chowPossible(Player player, Tile tile) {
		int tileNum = tile.getNumber();
		String tileSuit = tile.getSuit();
		
		if (player.tileOccurences(new Tile(tileNum + 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum + 2, tileSuit)) >= 1) {
			if (player.tileOccurences(new Tile(tileNum + 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum - 1, tileSuit)) >= 1) {
				if (player.tileOccurences(new Tile(tileNum - 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum - 2, tileSuit)) >= 1) {
				// All combos available
			return 7;
		}
		}
		}
		
		if (player.tileOccurences(new Tile(tileNum + 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum - 1, tileSuit)) >= 1) {
			if (player.tileOccurences(new Tile(tileNum - 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum - 2, tileSuit)) >= 1) {
			// Chow at middle and end
			return 6;
		}
		}
		
		if (player.tileOccurences(new Tile(tileNum + 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum + 2, tileSuit)) >= 1) {
			if (player.tileOccurences(new Tile(tileNum - 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum - 2, tileSuit)) >= 1) {
			// Chow at start and end
			return 5;
		}
		}
		
		if (player.tileOccurences(new Tile(tileNum + 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum + 2, tileSuit)) >= 1) {
			if (player.tileOccurences(new Tile(tileNum + 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum - 1, tileSuit)) >= 1) {
			// Chow at start and middle
			return 4;
		}
		}
		
		
		else if (player.tileOccurences(new Tile(tileNum - 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum - 2, tileSuit)) >= 1) {
			// Tile at end of chow
			return 3;
		}
		
		else if (player.tileOccurences(new Tile(tileNum + 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum - 1, tileSuit)) >= 1) {
			// Tile at middle of chow
			return 2;
		}
		
		if (player.tileOccurences(new Tile(tileNum + 1, tileSuit)) >= 1 && player.tileOccurences(new Tile(tileNum + 2, tileSuit)) >= 1) {
			// Tile at start of chow
			return 1;
		}
		
		return 0;
	}

	public static void addPung(Tile tile, Player player) {
		int counter = 0;
		for (int i = 0; i < player.getDeck().size() && counter < 3; ++i) {
			if (player.getDeck().get(i).tileEquals(tile)) {
				player.getDeclaredTiles().add(tile);
				player.getDeck().remove(i);
				++counter;
				--i;
			}
		}
		player.addSet();
		return;
	}

	public static void addKong(Tile tile, Player player) {
		int counter = 0;
		for (int i = 0; i < player.getDeck().size() && counter < 4; ++i) {
			if (player.getDeck().get(i).tileEquals(tile)) {
				player.getDeclaredTiles().add(tile);
				player.getDeck().remove(i);
				++counter;
				--i;
			}
		}
		player.addSet();
		return;
	}

	public static boolean pungPossible(Tile first, Tile second, Tile third, Player player) {
		String testSuit = first.getSuit(); 
		int testNum = first.getNumber();
		
		if (!isSuitValid(testSuit, player)) {
			return false;
		}
		
		if (testSuit.equals(second.getSuit()) && testSuit.equals(third.getSuit())) {
			if (testNum == second.getNumber() && testNum == third.getNumber()) {
				return true;
			}
		}
		return false;
	}

	public static void addPair(Tile tileOne, Tile tileTwo, Player player) {
		player.getDeclaredTiles().add(tileOne);
		player.getDeclaredTiles().add(tileTwo);
		player.getDeck().remove(tileOne);
		player.getDeck().remove(tileTwo); // Bug?
		player.setHasPair();
		return;
	}

	public static void addDiscardChow(Tile firstTile, Tile secondTile, Tile discard, int number, Player player) {
		if (number == 1) {
			player.getDeclaredTiles().add(discard);		
		}
		
		for (int i = 0; i < player.getDeck().size(); ++i) {
			if (player.getDeck().get(i).tileEquals(firstTile)) {
				player.getDeclaredTiles().add(firstTile);
				player.getDeck().remove(i);
				break;
			}
		}
		
		if (number == 2) {
			player.getDeclaredTiles().add(discard);		
		}
		
		for (int i = 0; i < player.getDeck().size(); ++i) {
			if (player.getDeck().get(i).tileEquals(secondTile)) {
				player.getDeclaredTiles().add(secondTile);
				player.getDeck().remove(i);
				break;
			}
		}
		
		if (number == 3) {
			player.getDeclaredTiles().add(discard);		
		}
		
		if (player.getDeclaredSuit().equals("")) {
			player.setDeclaredSuit(firstTile.getSuit());
		}
		
		player.setHasChow();
		return;
	}
}
