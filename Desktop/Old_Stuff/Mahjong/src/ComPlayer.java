import java.util.ArrayList;

public class ComPlayer extends Player {
	
	public ComPlayer(String name) {
		super (name);
	}
	
	public Tile playOneTurn(ArrayList<Tile> deck, Tile discard) {
		boolean alreadyDeclared = false;
		
		if (isFishing()) {
			if (tileOccurences(discard) >= 1 && isSuitValid(discard.getSuit(), this)) {
				addTile(discard);
				System.out.print(getName() + " took the ");
				discard.tileToString();
				// player.addSet(); (need?)
					addPair(discard, discard, this);
					alreadyDeclared = true;
					System.out.print(getName() + " declared a pair of ");
					discard.tileToString();
					System.out.println("Mahjong!");
					return new Tile(0, "");
		}
		}
		
		if (tileOccurences(discard) >= 2 && isSuitValid(discard.getSuit(), this)) {
			addTile(discard);
			System.out.print(getName() + " took the ");
			discard.tileToString();
			if (tileOccurences(discard) == 4) {
				addKong(discard, this);
				if (getDeclaredSuit().equals("")) {
					setDeclaredSuit(discard.getSuit());
				}
				alreadyDeclared = true;
				System.out.print(getName() + " declared a kong of ");
				discard.tileToString();
			}
			else if (tileOccurences(discard) == 3) {
				addPung(discard, this);
				alreadyDeclared = true;
				if (getDeclaredSuit().equals("")) {
					setDeclaredSuit(discard.getSuit());
				}
				System.out.print(getName() + " declared a pung of ");
				discard.tileToString();
			}
			
		}
		else if (chowPossible(this, discard) != 0 && isSuitValid(discard.getSuit(), this) && !getHasChow()) {
			System.out.print(getName() + " took the ");
			discard.tileToString();
			alreadyDeclared = comDiscardChow(chowPossible(this, discard), discard, this);
			setHasChow();
			if (getDeclaredSuit().equals("")) {
				setDeclaredSuit(discard.getSuit());
			}
		}
		// End discard part
		
		Tile draw = deck.get(0);
		deck.remove(0);
		addTile(draw);
		
		
		
		if (!alreadyDeclared) {
			comDeclareTiles(this);
		}
		
		System.out.println(getName() + "'s declared tiles:");
		displayDeclaredTiles();
		
		// For now they always discard the first tile in their hand...
		Tile toDiscard = comDiscard(this);
		getDeck().remove(toDiscard);
		System.out.print(getName() + " discarded a ");
		toDiscard.tileToString();
		return toDiscard;
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
