import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

    private static String[] startingDeck = {"C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "CJ", "CQ", "CK", "CA",
            "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "DJ", "DQ", "DK", "DA",
            "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "HJ", "HQ", "HK", "HA",
            "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10", "SJ", "SQ", "SK", "SA"};
    int samScore = 0;
    int dealerScore = 0;
    private int[] deckScores;

    public static void main(String[] args) {
        Game game = new Game();
        if (args.length == 0) {
            game.setupGame();
            game.playGame();
        } else {
            try {
                DeckReader reader = new DeckReader(args[0]);
                String[] deck = reader.readDeck();
                //read deck from file
                game.setupGame(deck);
                game.playGame();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setupGame(String[] deck) {
        deckScores = convertDeck(shuffleDeck(deck));
    }

    private void setupGame() {
        deckScores = convertDeck(shuffleDeck(startingDeck));
    }

    private void playGame() {

    }

    List<String> shuffleDeck(String[] deck) {
        List<String> newDeck = new ArrayList<>(Arrays.asList(deck));
        Collections.shuffle(newDeck);
        return newDeck;
    }

    int[] convertDeck(List<String> shuffledDeck) {
        int size = shuffledDeck.size();
        int[] convertedDeck = new int[size];

        for (int i = 0; i < size; i++) {
            //get second character (2-10, J,Q,K,A)
            String value = shuffledDeck.get(i).substring(1);
            switch (value) {
                case "J":
                case "Q":
                case "K":
                    convertedDeck[i] = 10;
                    break;
                case "A":
                    convertedDeck[i] = 11;
                    break;
                default:
                    //user input decks are validated by DeckReader
                    convertedDeck[i] = Integer.parseInt(value);
            }
        }
        return convertedDeck;
    }

}