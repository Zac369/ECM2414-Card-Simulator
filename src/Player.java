import jdk.jfr.StackTrace;

import javax.crypto.NullCipher;
import java.util.LinkedList;

public class Player extends CardDeck {
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            notIndexCards = (LinkedList) deck.clone();
            while (notIndexCards.contains(playerID)) {
                notIndexCards.removeFirstOccurrence(playerID);
            }
            while (!isVictory()) {
                //Strategy
                draw(cardDeckList[index]);
                System.out.println("This is a turn from Player "+ playerID + deck.toString() + "Deck to my left " +cardDeckList[index].deck.toString() + "Deck to my right "+cardDeckList[nextIndex].deck.toString());
                if (deck.getFirst()!= playerID) {
                    notIndexCards.add(deck.getFirst());
                }
                if (isVictory()) {
                    //Do all the things that break the game
                }
                try {
                    discard(notIndexCards.peekLast(), cardDeckList[nextIndex]);
                } catch (NullPointerException e) {
                    //e.printStackTrace();
                    System.out.println("discard broke");
                }
                thread.notify();
                /*try {
                    thread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

            }
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

    public Player(Player[] list, CardDeck[] list2, int a) {
        playerList = list;
        cardDeckList = list2;
        index = a;
        playerID = a + 1;
        nextIndex = index + 1;
        if (playerID >= playerList.length){
            nextIndex = 0;
        }
    }





    /**
     * @param cardValue       Discarded Card
     * @param destinationDeck CardDeck to discard to
     */
    public synchronized void discard(int cardValue, CardDeck destinationDeck) {
        notIndexCards.removeFirstOccurrence(cardValue);
        deck.removeFirstOccurrence(cardValue);
        destinationDeck.deck.addLast(cardValue);
        //notify();
    }

    /**
     *
     * @param sourceDeck CardDeck to draw from
     */
    public synchronized void draw(CardDeck sourceDeck) {
        deck.addFirst(sourceDeck.deck.poll());
    }


    public synchronized boolean isVictory() {
        return notIndexCards.size() == 0;
    }


}
