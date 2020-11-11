import java.util.HashSet;
import java.util.LinkedList;
import java.text.MessageFormat;

public class Player extends CardDeck {
    int playerID;
    int nextIndex;
    int index;
    LinkedList<Integer> cardsToDiscard = new LinkedList<>();
    Player[] playerList;
    CardDeck[] cardDeckList;
    String playerOutputPathName;
    String deckOutputPathName;

    public Player(Player[] playerArray, CardDeck[] deckArray, int arrayIndex) {
        playerList = playerArray;
        cardDeckList = deckArray;
        index = arrayIndex; // Player's Index in the array of players
        playerID = arrayIndex + 1; // Human readable Player Number
        // Make nextIndex loop around to 0
            nextIndex = index + 1;
            if (playerID >= playerList.length) {
                nextIndex = 0;
            }
        // Creates and wipes output files for this player and the deck on its left
            playerOutputPathName = MessageFormat.format("logs/Player{0}_output.txt", playerID);
            deckOutputPathName = MessageFormat.format("logs/Deck{0}_output.txt", playerID);
            createFile(playerOutputPathName);
            createFile(deckOutputPathName);
    }


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            writeToFile(playerOutputPathName, MessageFormat.format("Player {0}''s initial hand {1} \n", playerID, deck.toString()));
            cardsToDiscard = (LinkedList) deck.clone();
            while (cardsToDiscard.contains(playerID)) {
                cardsToDiscard.removeFirstOccurrence(playerID);
            }
            synchronized (CardGame.lock) {
                // Keeps players checking for victory in order if dealt hand is winning
                    while (CardGame.turn != index && CardGame.turn >= 0) {
                        try {
                            CardGame.lock.wait();
                        } catch (Exception e) {//
                        }
                    }
                while (!isVictory() && CardGame.turn >= 0) {
                    // Wait for the lock to be released and checks someone hasn't won
                        while (CardGame.turn != index && CardGame.turn >= 0) {
                            try {
                                CardGame.lock.wait();
                            } catch (Exception e) {//
                            }
                        }
                        if (CardGame.turn < 0) {
                            break;
                        }
                    // STRATEGY
                        drawAndDiscard(cardDeckList[index], cardsToDiscard.peekLast(), cardDeckList[nextIndex]);
                        // If drawn card is not the player's preferred denomination (i.e. their playerID), then add it to cardsToDiscard
                            if (deck.getFirst() != playerID) {
                                cardsToDiscard.add(deck.getFirst());
                            }
                        // Sets turn to next player, and release turn lock
                            CardGame.turn = nextIndex;
                            CardGame.lock.notifyAll();
                        // Checks for victory, implements victory protocol and exits if true
                            if (isVictory()) {
                                break;
                            }
                }
            }

            // Checks for Defeat, and implements losing protocol if true
                if (CardGame.turn < 0 && CardGame.turn != -playerID) {
                    String content = MessageFormat.format(
                            "Player {0} has informed Player {1} that player {0} has won\n" +
                                    "Player {1} exits\n" +
                                    "Player {1} hand is {2}\n",
                            (-CardGame.turn), playerID, deck.toString());
                    writeToFile(playerOutputPathName, content);
                }
        }
    });

    /**
     * @param sourceDeck      Deck to draw from
     * @param cardValue       Value of the card you wish to discard
     * @param destinationDeck Deck you want to discard to
     */
    public void drawAndDiscard(CardDeck sourceDeck, int cardValue, CardDeck destinationDeck) {
        // Remove first matching card from the Player's deck and cards to discard
        // and adds it at the bottom of the destination deck
            cardsToDiscard.removeFirstOccurrence(cardValue);
            deck.removeFirstOccurrence(cardValue);
            destinationDeck.deck.addLast(cardValue);
        // Draws first card from source deck to top of this Player's deck
            int cardAdded = sourceDeck.deck.poll();
            deck.addFirst(cardAdded);

        String content = MessageFormat.format(
                "Player {0} draws a {1} from deck {0}\n" +
                        "Player {0} discards a {2} to deck {3}\n" +
                        "Player {0}''s current hand is {4}\n",
                playerID, cardAdded, cardValue, nextIndex + 1, deck.toString());
        writeToFile(playerOutputPathName, content);
    }

    /**
     * If victory is achieved, implements winning protocol and returns true
     */
    public boolean isVictory() {
        // Checks if deck contains only matching elements
        // (if all elements in the deck are the same, Hash size will be 1)
        if (new HashSet<>(deck).size() <= 1) {
            CardGame.turn = -playerID; // Sets itself as the winning player
            String content = MessageFormat.format(
                    "Player {0} wins\n" +
                            "Player {0} exits\n" +
                            "Player {0}''s final hand is {1}\n",
                    playerID, deck.toString());
            writeToFile(playerOutputPathName, content);
            System.out.println(MessageFormat.format("Player {0} wins", playerID));
            writeToFile(deckOutputPathName, MessageFormat.format("Deck {0} content: {1}\n",
                                                playerID, cardDeckList[nextIndex].deck.toString()));

            CardGame.lock.notifyAll(); //Releases lock for all players so they can proceed with defeat protocol
            return true;
        } else return false;
    }

}





