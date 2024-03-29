package blackjacktest;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void shuffleDeck() {
        String[] startingDeck = {"C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "CJ", "CQ", "CK", "CA",
                "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "DJ", "DQ", "DK", "DA",
                "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "HJ", "HQ", "HK", "HA",
                "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10", "SJ", "SQ", "SK", "SA"};
        String[] shuffledDeck = Game.shuffleDeck(startingDeck.clone()).toArray(String[]::new);
        assertFalse(Arrays.equals(startingDeck, shuffledDeck));
    }

    @Test
    void playGame(){
        Game game = new Game();

        //example given
        String[] deck = {"CA", "D5", "H9", "HQ", "S8"};
        String[] samExpected = {"CA", "H9"};
        String[] dealerExpected = {"D5", "HQ", "S8"};
        Game.Players expectedWinner = Game.Players.sam;

        game.setDeck(Arrays.asList(deck));

        try {
            game.playGame();
        } catch (InvalidDeckException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(expectedWinner, game.winner);
        assertArrayEquals(samExpected, game.samCards.toArray());
        assertArrayEquals(dealerExpected, game.dealerCards.toArray());


        //both get 21, sam wins
        game.reset();
        deck = new String[]{"C10", "D10", "CA", "DA"};
        samExpected = new String[]{"C10", "CA"};
        dealerExpected = new String[]{"D10", "DA"};
        expectedWinner = Game.Players.sam;

        game.setDeck(Arrays.asList(deck));
        try {
            game.playGame();
        } catch (InvalidDeckException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(expectedWinner, game.winner);
        assertArrayEquals(samExpected, game.samCards.toArray());
        assertArrayEquals(dealerExpected, game.dealerCards.toArray());

        //both get 22, dealer wins
        game.reset();
        deck = new String[]{"CA", "DA", "HA", "SA"};
        samExpected = new String[]{"CA", "HA"};
        dealerExpected = new String[]{"DA", "SA"};
        expectedWinner = Game.Players.dealer;

        game.setDeck(Arrays.asList(deck));
        try {
            game.playGame();
        } catch (InvalidDeckException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(expectedWinner, game.winner);
        assertArrayEquals(samExpected, game.samCards.toArray());
        assertArrayEquals(dealerExpected, game.dealerCards.toArray());

        //Only 4 cards, sam has bigger score
        game.reset();
        deck = new String[]{"C10", "D5", "H10", "S6"};
        samExpected = new String[]{"C10", "H10"};
        dealerExpected = new String[]{"D5", "S6"};
        expectedWinner = Game.Players.sam;

        game.setDeck(Arrays.asList(deck));
        try {
            game.playGame();
        } catch (InvalidDeckException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(expectedWinner, game.winner);
        assertArrayEquals(samExpected, game.samCards.toArray());
        assertArrayEquals(dealerExpected, game.dealerCards.toArray());

        //Only 4 cards, dealer has bigger score
        game.reset();
        deck = new String[]{"C5", "D10", "H6", "S10"};
        samExpected = new String[]{"C5", "H6"};
        dealerExpected = new String[]{"D10", "S10"};
        expectedWinner = Game.Players.dealer;

        game.setDeck(Arrays.asList(deck));
        try {
            game.playGame();
        } catch (InvalidDeckException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(expectedWinner, game.winner);
        assertArrayEquals(samExpected, game.samCards.toArray());
        assertArrayEquals(dealerExpected, game.dealerCards.toArray());

        //Only 4 cards, players tie
        game.reset();
        deck = new String[]{"C10", "D10", "H10", "S10"};
        samExpected = new String[]{"C10", "H10"};
        dealerExpected = new String[]{"D10", "S10"};

        game.setDeck(Arrays.asList(deck));
        try {
            game.playGame();
        } catch (InvalidDeckException e) {
            e.printStackTrace();
            fail();
        }

        assertNull(game.winner);
        assertArrayEquals(samExpected, game.samCards.toArray());
        assertArrayEquals(dealerExpected, game.dealerCards.toArray());

        //not enough cards
        game.reset();
        deck = new String[]{"C10", "D10"};

        game.setDeck(Arrays.asList(deck));
        assertThrows(InvalidDeckException.class, game::playGame);
    }
}