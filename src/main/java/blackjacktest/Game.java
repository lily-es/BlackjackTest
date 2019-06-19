package blackjacktest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Game {


    /**
     * Unshuffled deck with 52 cards used as default
     */
    private static final String[] startingDeck = {"C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "CJ", "CQ", "CK", "CA",
            "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "DJ", "DQ", "DK", "DA",
            "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "HJ", "HQ", "HK", "HA",
            "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10", "SJ", "SQ", "SK", "SA"};


    enum Players {
        sam,
        dealer
    }

    Players winner = null;

    //need to constantly evaluate the score as the game progresses, so we store the score instead of calculating it every time
    int samScore = 0;
    int dealerScore = 0;

    private List<String> deck;

    List<String> samCards = new ArrayList<>();
    List<String> dealerCards = new ArrayList<>();


    /**
     *
     * @param args path to text file containing the deck
     */
    public static void main(String[] args) {
        Game game = new Game();
        if (args.length == 0) {
            //if no deck provided, start with default deck and shuffle it
            game.setDeck(shuffleDeck(startingDeck.clone()));
            try {
                game.playGame();
            } catch (InvalidDeckException e) {
                e.printStackTrace();
            }
        } else {
            try {
                //read deck from file, and start game with it
                DeckReader reader = new DeckReader(args[0]);
                String[] deck = reader.readDeck();
                game.setDeck(Arrays.asList(deck));
                game.playGame();
            } catch (IOException|InvalidDeckException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        game.printResult();
    }

    /**
     * Simulates Blackjack game according to the rules set out by the test
     */
    void playGame() throws InvalidDeckException {
        if (deck.size() < 4) {
            throw new InvalidDeckException("Not enough cards to start the game (need at least 4)");
        }
        for (int i = 0; i < 2; i++) {
            pullCard(Players.sam);
            pullCard(Players.dealer);
        }
        //both have blackjack, sam wins
        if (samScore == 21 && dealerScore == 21) {
            winner = Players.sam;
            return;
        }
        //both busted, dealer wins
        if (samScore == 22 && dealerScore == 22) {
            winner = Players.dealer;
            return;
        }

        //if one wins or busts then winner is decided
        if (samScore == 21 || dealerScore > 21) {
            winner = Players.sam;
            return;
        }
        if (dealerScore == 21 || samScore > 21) {
            winner = Players.dealer;
            return;
        }

        //sam draws
        while (samScore < 17 && !deck.isEmpty()) {
            pullCard(Players.sam);
        }
        if (samScore > 21) {
            winner = Players.dealer;
            return;
        }
        if(samScore == 21){
            winner=Players.sam;
            return;
        }


        //dealer draws
        while (dealerScore <= samScore && !deck.isEmpty()) {
            pullCard(Players.dealer);
        }
        if (dealerScore > 21) {
            winner = Players.sam;
            return;
        }
        if (dealerScore > samScore) {
            winner = Players.dealer;
            return;
        } else if(samScore > dealerScore){
            winner = Players.sam;
            return;
        }
    }

    /**
     * Prints the result of the game
     */
    private void printResult() {
        if(winner==null){
            System.out.println("tie");
        } else System.out.println(winner);
        //easiest way to remove brackets while using toString
        System.out.println("sam: "+samCards.toString().replace("[","").replace("]",""));
        System.out.println("dealer: "+dealerCards.toString().replace("[","").replace("]",""));
    }

    /**
     * Shuffles an array (deck of cards) and returns a List
     * @param deck String array containing all the cards
     * @return List containing all cards
     */
    static List<String> shuffleDeck(String[] deck) {
        List<String> newDeck = Arrays.asList(deck);
        Collections.shuffle(newDeck);

        return newDeck;
    }

    /**
     * Calculates the score of a certain card
     * @param card String representation of the card
     * @return Its corresponding points value
     */
    private int calculateScore(String card) {
        String value = card.substring(1);
        switch (value) {
            case "J":
            case "Q":
            case "K":
                return 10;
            case "A":
                return 11;
            default:
                //user input decks are validated by DeckReader so no need to worry here
                return Integer.parseInt(value);
        }
    }

    /**
     * Pulls a card from the deck and gives it to the specified player
     * @param player
     */
    private void pullCard(Players player) {
        String card = deck.get(0);
        deck.remove(0);

        if (player == Players.sam) {
            samCards.add(card);
            samScore += calculateScore(card);
        } else {
            dealerCards.add(card);
            dealerScore += calculateScore(card);
        }
    }


    void setDeck(List<String> deck){
        this.deck = new ArrayList<>(deck);
    }

    /**
     * Resets the game to its initial values
     */
    void reset(){
        samScore=0;
        dealerScore=0;
        samCards.clear();
        dealerCards.clear();
        winner=null;
    }
}