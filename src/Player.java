import java.util.LinkedList;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;

public class Player extends CardDeck {
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            writeToFile(playerOutputPathName, "Player " + playerID + " initial hand " + deck.toString() + "\n");
            notIndexCards = (LinkedList) deck.clone();
            while (notIndexCards.contains(playerID)) {
                notIndexCards.removeFirstOccurrence(playerID);
            }
            while (!isVictory()) {
                //Strategy

                drawAndDiscard(cardDeckList[index], notIndexCards.peekLast(), cardDeckList[nextIndex]);
                //if drawn card is not the player's preferred denomination (i.e. their playerID), then add it to notIndexCards
                if (deck.getFirst() != playerID) {
                    notIndexCards.add(deck.getFirst());
                }
                //System.out.println("Player "+ playerID + "'s cards to be discarded are " +notIndexCards.toString() +
                //"\nPlayer "+ playerID + "'s current hand is " +deck.toString());


                //thread.notify();
                /*try {
                    thread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

            }

            //Victory is achieved
            //Do all the things that break the game
            writeToFile(deckOutputPathName, "Deck " +playerID+" content: "+cardDeckList[playerID].deck.toString());
            System.out.println("Hello, I'm Player " + playerID + " and here is my hand" + deck.toString());

/*            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace(); }*/


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
        playerOutputPathName = "Player" + playerID + "_output.txt";
        deckOutputPathName = "Deck" + playerID + "_output.txt";
        createFile(playerOutputPathName);
        createFile(deckOutputPathName);
    }


    /**
     * @param sourceDeck      Deck to draw from
     * @param cardValue       Value of the card you wish to discard
     * @param destinationDeck Deck you want to discard to
     */
    public synchronized void drawAndDiscard(CardDeck sourceDeck, int cardValue, CardDeck destinationDeck) {
        int cardAdded = sourceDeck.deck.poll();
        notIndexCards.removeFirstOccurrence(cardValue);
        deck.removeFirstOccurrence(cardValue);
        deck.addFirst(cardAdded);
        destinationDeck.deck.addLast(cardValue);
        writeToFile(playerOutputPathName, "Player " + playerID + " draws a " + cardAdded + " from deck " + (index + 1) +
                "\nPlayer " + playerID + " discards a " + cardValue + " to deck " + (nextIndex + 1) +
                "\nPlayer " + playerID + "'s current hand is " + deck.toString() + "\n");
    }


    public synchronized boolean isVictory() {
        return notIndexCards.size() == 0;
    }

    public boolean createFile(String pathName) {
        try {
            File outputFile = new File(pathName);
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists.");
                new FileWriter(pathName).close();
            }
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    public void writeToFile(String pathName, String content) {
        try {
            FileWriter myWriter = new FileWriter(pathName, true);
            myWriter.write(content);
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}





