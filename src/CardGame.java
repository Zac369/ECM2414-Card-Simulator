import javax.sound.midi.SysexMessage;
import javax.sql.rowset.CachedRowSet;

public class CardGame {
    public static void main(String[] args) {
        int numberOfPlayers = 4;
        String filePath = "C:\\Users\\willi\\IdeaProjects\\ECM2414Coursework\\src\\pack.txt";


        Pack test = new Pack();

        test.validatePack(numberOfPlayers, filePath);
        Player[] playerList = new Player[numberOfPlayers];
        CardDeck[] cardDeckList = new CardDeck[numberOfPlayers];

    }
}
