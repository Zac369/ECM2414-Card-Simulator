import java.util.Random;

public class Player extends CardDeck {
    Random r = new Random();
    int preferredDenomination = r.nextInt(8);















    /**
     * @param cardIndex       0 <= int < 4
     * @param destinationDeck CardDeck to discard to
     */
    public synchronized void discard(int cardIndex, CardDeck destinationDeck) {
        int discardedCard = deck.get(cardIndex);
        deck.remove(cardIndex);
        destinationDeck.deck.addLast(discardedCard);
        notify();
    }

    /**
     *
     * @param sourceDeck CardDeck to draw from
     */
    public synchronized void draw(CardDeck sourceDeck) {
        deck.addFirst(sourceDeck.deck.poll());
    }

    //Strategy



}
