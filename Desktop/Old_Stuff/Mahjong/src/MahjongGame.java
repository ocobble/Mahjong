import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class MahjongGame {
	public static void main(String[] args) {
	// Main (yeah I know this is poorly formatted lol)	
		Scanner scnr = new Scanner(System.in);
		
		
		ArrayList<Tile>deck = GameHelper.createDeck();
		
		
		Collections.shuffle(deck);
		
		ArrayList<Player> players = new ArrayList<Player>();
		Player user = new Player("user");
		ComPlayer Olivia = new ComPlayer("Olivia");
		ComPlayer Andrew = new ComPlayer("Andrew");
		ComPlayer Amanda = new ComPlayer("Amanda");
		
		Tile discard = new Tile();
		int turnCount = 0;
		boolean turnSkip = false;
		boolean mahjong = false;
		//boolean tookDiscard = false;
		
		players.add(user);
		players.add(Olivia);
		players.add(Andrew);
		players.add(Amanda);
		
		// Dealing tiles...
		user.dealTiles(deck);
		Olivia.dealTiles(deck);
		Andrew.dealTiles(deck);
		Amanda.dealTiles(deck);
		
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
		GameHelper.setWinds(user, Olivia, Andrew, Amanda);
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
			turnSkip = user.discardTile(scnr, discard);
			mahjong = user.isMahjong();
			}
			if (!mahjong) {
			discard = user.playOneTurn(deck, scnr, turnSkip);
			turnSkip = false;
			mahjong = user.isMahjong();
		}
		if (!mahjong && deck.size() > 0) {
			discard = Olivia.playOneTurn(deck, discard);
			mahjong = Olivia.isMahjong();
		}
		if (!mahjong && deck.size() > 0) {
			turnSkip = user.discardTile(scnr, discard);
			if (turnSkip) {
				continue;
			}
			discard = Andrew.playOneTurn(deck, discard);
			mahjong = Andrew.isMahjong();
		}
		if (!mahjong && deck.size() > 0) {
			turnSkip = user.discardTile(scnr, discard);
			if (turnSkip) {
				continue;
			}
			discard = Amanda.playOneTurn(deck, discard);
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
}

