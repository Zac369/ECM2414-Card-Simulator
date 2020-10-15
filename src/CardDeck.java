import java.util.LinkedList;

public class CardDeck {
    //Reusing materials from W3 CW.
    LinkedList<Integer> deck = new LinkedList<>();
    int capacity = 4;
    // Function called by producer thread

/*
    public synchronized void discard(int cardIndex, CardDeck destinationDeck){
        int discardedCard = deck.get(cardIndex);
        deck.remove(cardIndex);
        destinationDeck.deck.addLast(discardedCard);
        notify();

    }



    // Function called by consumer thread
    public synchronized void consume() throws InterruptedException {
        for (int i = 0; i < 20; ) {
            if (!deck.isEmpty()) {
                deck.remove();
                System.out.println(deck.toString());
                i++;
                notify();
            } else {
                wait();
            }
        }


//*/
}