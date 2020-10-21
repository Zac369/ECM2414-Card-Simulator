import java.util.LinkedList;

public class CardDeck {
    //Reusing materials from W3 CW.
    LinkedList<Integer> deck = new LinkedList<>();
    int decksize = 4;
    // Function called by producer thread

/*
    public synchronized void discard(int cardIndex, CardDeck destinationDeck){
        int discardedCard = pack.get(cardIndex);
        pack.remove(cardIndex);
        destinationDeck.pack.addLast(discardedCard);
        notify();

    }



    // Function called by consumer thread
    public synchronized void consume() throws InterruptedException {
        for (int i = 0; i < 20; ) {
            if (!pack.isEmpty()) {
                pack.remove();
                System.out.println(pack.toString());
                i++;
                notify();
            } else {
                wait();
            }
        }


//*/
}