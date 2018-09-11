import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
public class MahjongGame {
	public static void main(String[] args) {
	// Main (yeah I know this is poorly formatted lol)	
		Scanner keyboard = new Scanner(System.in);
		
		
		ArrayList<Tile> gameDeck = createDeck();
		
		
		Collections.shuffle(gameDeck);
		
		
		playTheGame(gameDeck, keyboard);
	}
	
	// Here's where the magic happens!
	public static void playTheGame(ArrayList<Tile> deck, Scanner scnr) {
		ArrayList<Player> players = new ArrayList<Player>();
		Player user = new Player("user");
		Player Olivia = new Player("Olivia");
		Player Andrew = new Player("Andrew");
		Player Amanda = new Player("Amanda");
		Tile discard = new Tile();
		int turnCount = 0;
		boolean turnSkip = false;
		//boolean tookDiscard = false;
		boolean mahjong = false;
		
		players.add(user);
		players.add(Olivia);
		players.add(Andrew);
		players.add(Amanda);
		
		// Dealing tiles...
		dealTiles(deck, user);
		dealTiles(deck, Olivia);
		dealTiles(deck, Andrew);
		dealTiles(deck, Amanda);
		
		// Test stuff...
		/*
		System.out.println("user's deal: ");
		user.displayHand();
		
		
		System.out.println("User deck's size: " + user.getDeck().size());
		System.out.println("Olivia's deal: ");
		Olivia.displayHand();
		System.out.println("Andrew's deal: ");
		Andrew.displayHand();
		System.out.println("Amanda's deal: ");
		Amanda.displayHand();
		/*
		System.out.println("Wall: ");
		for (int i = 0; i < deck.size(); ++i) {
			deck.get(i).tileToString();
		}
		*/
		
		// Obvi...
		setWinds(user, Olivia, Andrew, Amanda);
		System.out.println("You are the " + user.getWind() + " wind.");
		
		/* Test winds
		System.out.println(user.getWind());
		System.out.println(Olivia.getWind());
		System.out.println(Andrew.getWind());
		System.out.println(Amanda.getWind());
		*/
		
		// Gameplay!
		while (!mahjong && deck.size() > 0) {
			if (turnCount > 0 && !turnSkip) {
			turnSkip = discardTile(scnr, discard, user);
			mahjong = user.isMahjong();
			}
			if (!mahjong) {
			discard = playOneTurnUser(deck, user, scnr, turnSkip);
			turnSkip = false;
			mahjong = user.isMahjong();
		}
		if (!mahjong && deck.size() > 0) {
			discard = playOneTurn(deck, Olivia, discard);
			mahjong = Olivia.isMahjong();
		}
		if (!mahjong && deck.size() > 0) {
			turnSkip = discardTile(scnr, discard, user);
			if (turnSkip) {
				continue;
			}
			discard = playOneTurn(deck, Andrew, discard);
			mahjong = Andrew.isMahjong();
		}
		if (!mahjong && deck.size() > 0) {
			turnSkip = discardTile(scnr, discard, user);
			if (turnSkip) {
				continue;
			}
			discard = playOneTurn(deck, Amanda, discard);
			mahjong = Amanda.isMahjong();
		}
		++turnCount;
	}
		
		/* if (!mahjong) {
			System.out.println("The game is a draw.");
			return;
		} */
		
		user.calculatePoints();
		Olivia.calculatePoints();
		Andrew.calculatePoints();
		Amanda.calculatePoints();
		
		System.out.println("You: " + user.getPoints());
		System.out.println("Olivia: " + Olivia.getPoints());
		System.out.println("Andrew: " + Andrew.getPoints());
		System.out.println("Amanda: " + Amanda.getPoints());
		
		//TODO: Calculate points
	}
	
	public static void setWinds(Player user, Player Olivia, Player Andrew, Player Amanda) {
		ArrayList<String> compass = new ArrayList<String>();
		compass.add("East");
		compass.add("West");
		compass.add("North");
		compass.add("South");
		
		Collections.shuffle(compass);
		
		user.setWind(compass.get(0));
		Olivia.setWind(compass.get(1));
		Andrew.setWind(compass.get(2));
		Amanda.setWind(compass.get(3));
		
		return;
	}
	
	public static ArrayList<Tile> createDeck() {
		ArrayList<Tile> deck = new ArrayList<Tile>();
		for (int i = 0; i < 36; ++i) {
			deck.add(new Tile(i % 9 + 1, "Circles"));
		}
		for (int i = 0; i < 36; ++i) {
			deck.add(new Tile(i % 9 + 1, "Characters"));
		}
		for (int i = 0; i < 36; ++i) {
			deck.add(new Tile(i % 9 + 1, "Bamboo"));
		}
		for (int i = 0; i < 4; ++i) {
			deck.add(new Tile(0, "North Wind"));
			deck.add(new Tile(0, "South Wind"));
			deck.add(new Tile(0, "East Wind"));
			deck.add(new Tile(0, "West Wind"));
			deck.add(new Tile(0, "White Dragon"));
			deck.add(new Tile(0, "Red Dragon"));
			deck.add(new Tile(0, "Green Dragon"));
		}
		for (int i = 0; i < 4; ++i) {
			deck.add(new Tile(i + 1, "Season"));
			deck.add(new Tile(i + 1, "Flower"));
		}
		return deck;
	}
	
	public static void dealTiles(ArrayList<Tile> deck, Player player) {
		ArrayList<Tile> hand = new ArrayList<Tile>();
		for (int i = 0; i < 13; ++i) {
			hand.add(deck.get(i));
		}
		player.setDeck(hand);
		deck.subList(0, 13).clear();
	return;
	}
	
	// Turn play for com
	public static Tile playOneTurn(ArrayList<Tile> deck, Player player, Tile discard) {
		boolean alreadyDeclared = false;
		
		if (player.isFishing()) {
			if (player.tileOccurences(discard) >= 1 && isSuitValid(discard.getSuit(), player)) {
				player.addTile(discard);
				System.out.print(player.getName() + " took the ");
				discard.tileToString();
				// player.addSet(); (need?)
					addPair(discard, discard, player);
					alreadyDeclared = true;
					System.out.print(player.getName() + " declared a pair of ");
					discard.tileToString();
					System.out.println("Mahjong!");
					return new Tile(0, "");
		}
		}
		
		if (player.tileOccurences(discard) >= 2 && isSuitValid(discard.getSuit(), player)) {
			player.addTile(discard);
			System.out.print(player.getName() + " took the ");
			discard.tileToString();
			if (player.tileOccurences(discard) == 4) {
				addKong(discard, player);
				if (player.getDeclaredSuit().equals("")) {
					player.setDeclaredSuit(discard.getSuit());
				}
				alreadyDeclared = true;
				System.out.print(player.getName() + " declared a kong of ");
				discard.tileToString();
			}
			else if (player.tileOccurences(discard) == 3) {
				addPung(discard, player);
				alreadyDeclared = true;
				if (player.getDeclaredSuit().equals("")) {
					player.setDeclaredSuit(discard.getSuit());
				}
				System.out.print(player.getName() + " declared a pung of ");
				discard.tileToString();
			}
			
		}
		else if (chowPossible(player, discard) != 0 && isSuitValid(discard.getSuit(), player) && !player.getHasChow()) {
			System.out.print(player.getName() + " took the ");
			discard.tileToString();
			alreadyDeclared = comDiscardChow(chowPossible(player, discard), discard, player);
			player.setHasChow();
			if (player.getDeclaredSuit().equals("")) {
				player.setDeclaredSuit(discard.getSuit());
			}
		}
		// End discard part
		
		Tile draw = deck.get(0);
		deck.remove(0);
		player.addTile(draw);
		
		
		
		if (!alreadyDeclared) {
			comDeclareTiles(player);
		}
		
		System.out.println(player.getName() + "'s declared tiles:");
		player.displayDeclaredTiles();
		
		// For now they always discard the first tile in their hand...
		Tile toDiscard = comDiscard(player);
		player.getDeck().remove(toDiscard);
		System.out.print(player.getName() + " discarded a ");
		toDiscard.tileToString();
		return toDiscard;
	}
	
	
	// Turn play for user
	public static Tile playOneTurnUser(ArrayList<Tile> deck, Player player, Scanner scnr, boolean turnSkip) {
		if (player.isFishing()) {
			System.out.println("You are fishing!");
		}
		if (!turnSkip) {
		Tile draw = deck.get(0);
		deck.remove(0);
		player.addTile(draw);
		System.out.print("The tile you drew was a ");
		draw.tileToString();
		}
		if (player.getDeclaredTiles().size() > 0) {
			System.out.println("Your declared tiles:");
			player.displayDeclaredTiles();
		}
		System.out.println("Your hand: ");
		player.displayHand();
		
		if (!turnSkip) {
		System.out.println("Do you have anything you want to declare? Type yes or no.");
		String answer = scnr.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
			declareSomething(deck, scnr, player);
		}
		if (player.isMahjong()) {
			return new Tile(0, "");
		}
		}
		
		System.out.println("What do you want to discard? Enter a tile.");
		System.out.println("Make sure to include the 0 if it's a dragon or wind.");
		int tileNumber = scnr.nextInt();
		String tileName = scnr.nextLine();
		Tile discard = new Tile(tileNumber, tileName);
		player.removeTile(discard, scnr);
		System.out.println("You discarded a " + tileNumber + tileName + ".");
		return discard;
	}
	
	// Below is all of the declaration methods
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
	
	public static boolean discardTile(Scanner scnr, Tile discard, Player player) {
		boolean canTake = false;
		boolean willTake = false;
		System.out.println("Will you take it? Enter yes or no.");
		System.out.println("Remember you can only take it if you can declare something with it.");
		String answer = scnr.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
			if (!isSuitValid(discard.getSuit(), player)) {
				System.out.print("You can't take that, you already declared ");
				System.out.println(player.getDeclaredSuit());
				return false;
			} // end invalid statement
			
			
			// Pair declaration if fishing
			// Review rules
			if (player.isFishing()) {
				if (discard.tileEquals(player.getDeck().get(0))) {
					addPair(discard, player.getDeck().get(0), player);
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
			if (player.tileOccurences(discard) == 3) {
				willTake = discardKong(discard, scnr, player);
				if (willTake) {
					return willTake;
				}
			}
			
			// Pung declaration
			else if (player.tileOccurences(discard) >= 2) {
				willTake = discardPung(discard, scnr, player);
				if (willTake) {
					return willTake;
				}
			} // end pung part
			
				
			
			
			
			else if (chowPossible(player, discard) != 0) {
				int type = chowPossible(player, discard);
				willTake = discardChow(type, discard, scnr, player);
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
	
	public static void comDeclareTiles(Player player) {
		
		if (player.isFishing()) {
			if (player.tileOccurences(player.getDeck().get(0)) == 2) {
				addPair(player.getDeck().get(0), player.getDeck().get(0), player);
				System.out.print(player.getName() + " declared a pair of ");
				player.getDeck().get(0).tileToString();
				System.out.println("Mahjong!");
			}
		}
		
		for (int i = 0; i < player.getDeck().size(); ++i) {
			if (player.tileOccurences(player.getDeck().get(i)) == 4 && isSuitValid(player.getDeck().get(i).getSuit(), player)) {
				if (player.getDeclaredSuit().equals("")) {
					player.setDeclaredSuit(player.getDeck().get(i).getSuit());
				}
				System.out.print(player.getName() + " declared a kong of " );
				player.getDeck().get(i).tileToString();
				addKong(player.getDeck().get(i), player);
				return;
			}
			if (player.tileOccurences(player.getDeck().get(i)) == 3 && isSuitValid(player.getDeck().get(i).getSuit(), player)) {
				if (player.getDeclaredSuit().equals("")) {
					player.setDeclaredSuit(player.getDeck().get(i).getSuit());
				}
				addPung(player.getDeck().get(i), player);
				System.out.print(player.getName() + " declared a pung of " );
				player.getDeck().get(i).tileToString();
				return;
			}
			if (chowPossible(player, player.getDeck().get(i)) != 0 && isSuitValid(player.getDeck().get(i).getSuit(), player) && !player.getHasChow()) {
				if (player.getDeclaredSuit().equals("")) {
					player.setDeclaredSuit(player.getDeck().get(i).getSuit());
				}
				int type = chowPossible(player, player.getDeck().get(i));
				comDiscardChow(type, player.getDeck().get(i), player);
			}
		}
	}
	
	public static boolean comDiscardChow(int type, Tile discard, Player player) {
		//boolean willTake = false;
		player.setHasChow();
		
		// Kind of bad but...
				if (player.tileOccurences(discard) >= 1) {
					player.getDeck().remove(discard);
				}
		
		if (type == 1 || type == 4 || type == 5 || type == 7) {
				addDiscardChow(new Tile(discard.getNumber() + 1, discard.getSuit()), new Tile(discard.getNumber() + 2, discard.getSuit()), discard, 1, player);
				System.out.println(player.getName() + " declared a chow consisting of ");
				discard.tileToString();
				System.out.println((discard.getNumber() + 1) + " " + discard.getSuit());
				System.out.println((discard.getNumber() + 2) + " " + discard.getSuit());
				return true;
			} // done adding chow
		// finished with type 1
		
		else if (type == 2 || type == 4 || type == 6 || type == 7) {
				addDiscardChow(new Tile(discard.getNumber() - 1, discard.getSuit()), new Tile(discard.getNumber() + 1, discard.getSuit()), discard, 2, player);
				System.out.println(player.getName() + " declared a chow consisting of ");
				System.out.println((discard.getNumber() - 1) + " " + discard.getSuit());
				discard.tileToString();
				System.out.println((discard.getNumber() + 1) + " " + discard.getSuit());
				return true;
			} // done adding chow
		 // finished with type 2
		
		else if (type == 3 || type == 5 || type == 6 || type == 7) {
				addDiscardChow(new Tile(discard.getNumber() - 2, discard.getSuit()), new Tile(discard.getNumber() - 1, discard.getSuit()), discard, 3, player);
				System.out.println(player.getName() + " declared a chow consisting of ");
				System.out.println((discard.getNumber() - 2) + " " + discard.getSuit());
				System.out.println((discard.getNumber() - 1) + " " + discard.getSuit());
				discard.tileToString();
				return true;
			} // done adding chow
		return false;
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
	
	public static Tile comDiscard(Player player) {
		for (int i = 0; i < player.getDeck().size(); ++i) {
			if (!isSuitValid(player.getDeck().get(i).getSuit(), player)) {
				return player.getDeck().get(i);
			}
		}
		
		for (int i = 0; i < player.getDeck().size(); ++i) {
			if (player.tileOccurences(player.getDeck().get(i)) < 2) {
				return player.getDeck().get(i);
			}
		}
		
		return player.getDeck().get(0);
	}
	
	}

