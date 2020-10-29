import javax.sound.midi.SysexMessage;
import javax.sql.rowset.CachedRowSet;

public class CardGame {
    public static void main(String[] args) throws InterruptedException {
        int numberOfPlayers = 4;
        String filePath = "C:\\Users\\willi\\IdeaProjects\\ECM2414Coursework\\src\\pack.txt";


        Pack test = new Pack();

        test.validatePack(numberOfPlayers, filePath);

        CardDeck[] cardDeckList = new CardDeck[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            cardDeckList[i] = new CardDeck();
        }

        Player[] playerList = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            playerList[i] = new Player(playerList, cardDeckList, i);;
        }


        test.distributePack(playerList, cardDeckList);

        for (int i = 0; i < numberOfPlayers; i++) {
            playerList[i].thread.start();
            playerList[i].thread.join();

        }

    }
}
