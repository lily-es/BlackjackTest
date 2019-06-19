import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class DeckReader {
    private String filename;

    DeckReader(String filename) {
        this.filename = filename;
    }

    String[] readDeck() throws IOException, InvalidDeckException {
        String deck = new String(Files.readAllBytes(Paths.get(filename)));
        String[] separatedDeck = deck.split(",\\s?");
        if (validateDeck(separatedDeck)) return separatedDeck;
        throw new InvalidDeckException("Invalid deck detected");
    }

    private boolean validateDeck(String[] deck) {
        if (deck.length == 0) return false;
        for (String card :
                deck) {
            if (!card.matches("^[CDHS]([2-9]|10|J|Q|K|A)$")) return false;
        }
        return true;
    }
}
