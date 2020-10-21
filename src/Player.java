public class Player extends CardDeck {
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {

                System.out.println("Hello, I'm Player " + index + " and here is my hand" + deck.toString());
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    });

    int index;






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
