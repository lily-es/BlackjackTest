import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeckReaderTest {

    @Test
    void readDeck() {
        DeckReader reader = new DeckReader("gooddecktest.txt");
        try {
            String[] result = reader.readDeck();
            String[] expected = {"C10", "D2", "HA", "SK", "C8", "D6", "CJ", "SQ"};
            assertArrayEquals(result, expected);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidDeckException e) {
            fail();
        }

        reader = new DeckReader("baddecktest.txt");
        assertThrows(InvalidDeckException.class, reader::readDeck);
    }

}