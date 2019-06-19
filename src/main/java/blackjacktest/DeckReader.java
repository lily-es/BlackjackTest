package blackjacktest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class DeckReader {
    private String filename;

    DeckReader(String filename) {
        this.filename = filename;
    }

    /**
     * Reads a deck as an array of Strings from a text file
     * @return Deck as String array
     * @throws IOException In case the file is not found, or it fails to open or read it
     * @throws InvalidDeckException In case the deck is invalid
     */
    String[] readDeck() throws IOException, InvalidDeckException {
        String deck = new String(Files.readAllBytes(Paths.get(filename)));
        String[] separatedDeck = deck.split(",\\s?");
        if (validateDeck(separatedDeck)) return separatedDeck;
        throw new InvalidDeckException("Invalid deck detected");
    }

    /**
     * Validates the deck ensuring that all cards are valid cards
     * @param deck Deck as string array
     * @return true if deck is valid, false if deck is not valid
     */
    private boolean validateDeck(String[] deck) {
        if (deck.length == 0) return false;
        for (String card :
                deck) {
            if (!card.matches("^[CDHS]([2-9]|10|J|Q|K|A)$")) return false;
        }
        return true;
    }
}
