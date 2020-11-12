import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validatePack() {
    }

    @Test
    void distributePack() {
    }

    @Test
    void testValidatePack() {
        int numberOfPlayers = 4;
        String filepath = "./valid4PlayerPack.txt";
        Card card = new Card();
        assertTrue(card.validatePack(numberOfPlayers, filepath));
    }

    @Test
    void testFileNotFoundException() {
        int numberOfPlayers = 4;
        String filepath = "./doesNotExist.txt"; // not a real pack
        Card card = new Card();
        card.validatePack(numberOfPlayers, filepath);
    }

    @Test
    void testNegativeCard() {
        int numberOfPlayers = 4;
        String filepath = "./packWithNegativeCard.txt";
        Card card = new Card();
        assertFalse(card.validatePack(numberOfPlayers, filepath));
    }

    @Test
    void incorrectNumberOfCards() {
        int numberOfPlayers = 7; // anything other than 4 should not be valid
        String filepath = "./valid4PlayerPack.txt";
        Card card = new Card();
        assertFalse(card.validatePack(numberOfPlayers, filepath));
    }

}