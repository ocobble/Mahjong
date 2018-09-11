import java.util.ArrayList;
import java.util.Collections;

public class GameHelper {

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


}
