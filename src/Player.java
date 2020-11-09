import java.util.HashSet;
import java.util.LinkedList;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;
import java.text.MessageFormat;

public class Player extends CardDeck {
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {

            writeToFile(playerOutputPathName, MessageFormat.format("Player {0}'s initial hand {1}\n", playerID, deck.toString()));
            notIndexCards = (LinkedList) deck.clone();
            while (notIndexCards.contains(playerID)) {
                notIndexCards.removeFirstOccurrence(playerID);
            }
            while (!isVictory() && CardGame.turn !=-1) {
                System.out.println(CardGame.turn);
                synchronized (CardGame.lock) {
                    while (CardGame.turn != index) {
                        try {
                            CardGame.lock.wait();
                        } catch (Exception e) {//
                        }
                    }
                    //Strategy
                    System.out.println(MessageFormat.format("next index: {0}", nextIndex));

                    drawAndDiscard(cardDeckList[index], notIndexCards.peekLast(), cardDeckList[nextIndex]);
                    //if drawn card is not the player's preferred denomination (i.e. their playerID), then add it to notIndexCards
                    if (deck.getFirst() != playerID) {
                        notIndexCards.add(deck.getFirst());
                        System.out.println(MessageFormat.format("I want to discard {0}", notIndexCards.toString()));
                    }

                    CardGame.turn = nextIndex;
                    CardGame.lock.notifyAll();

                }
            if (CardGame.turn == -1){

            }
            //Victory is achieved
            //Do all the things that break the game
            }
            System.out.println("VICTORY");
            writeToFile(deckOutputPathName, MessageFormat.format("Deck {0} content: {1}", playerID, cardDeckList[nextIndex].deck.toString()));
            System.out.println(MessageFormat.format("Hello, I''m Player {0} and here is my hand{1}", playerID, deck.toString()));
        }
    });

    int playerID;
    int nextIndex;
    int index;
    LinkedList<Integer> notIndexCards = new LinkedList<>();
    Player[] playerList;
    CardDeck[] cardDeckList;
    String playerOutputPathName;
    String deckOutputPathName;

    /**
     * Player constructor, helps establish the indexes of players and decks
     *
     * @param list  Player array
     * @param list2 Deck array
     * @param a     this player's array index (Not ID)
     */
    public Player(Player[] list, CardDeck[] list2, int a) {
        playerList = list;
        cardDeckList = list2;
        index = a;
        playerID = a + 1;
        nextIndex = index + 1;
        if (playerID >= playerList.length) {
            nextIndex = 0;
        }
        playerOutputPathName = MessageFormat.format("Player{0}_output.txt", playerID);
        deckOutputPathName = MessageFormat.format("Deck{0}_output.txt", playerID);
        createFile(playerOutputPathName);
        createFile(deckOutputPathName);
    }


    /**
     * @param sourceDeck      Deck to draw from
     * @param cardValue       Value of the card you wish to discard
     * @param destinationDeck Deck you want to discard to
     */
    public void drawAndDiscard(CardDeck sourceDeck, int cardValue, CardDeck destinationDeck) {
        int cardAdded = sourceDeck.deck.poll();
        notIndexCards.removeFirstOccurrence(cardValue);
        deck.removeFirstOccurrence(cardValue);
        deck.addFirst(cardAdded);
        destinationDeck.deck.addLast(cardValue);
        String content = MessageFormat.format(
                "Player {0} draws a {1} from deck {2}\n" +
                        "Player {3} discards a {4} to deck {5}\n" +
                        "Player {6}''s current hand is {7}\n",
                playerID, cardAdded, index + 1, playerID, cardValue, nextIndex + 1, playerID, deck.toString());
        writeToFile(playerOutputPathName, content);
        System.out.println(content);
    }


    public boolean isVictory() {
        if (new HashSet<>(deck).size() <= 1) {
            CardGame.turn =-1;
            return true;
        } else return false;
    }

    public void victory() {

    }

    public void createFile(String pathName) {
        try {
            File outputFile = new File(pathName);
            if (!outputFile.createNewFile()) {
                new FileWriter(pathName).close();
            }
            System.out.println("File created: " + outputFile.getName());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile(String pathName, String content) {
        try {
            FileWriter myWriter = new FileWriter(pathName, true);
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}





