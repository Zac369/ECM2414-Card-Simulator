import java.util.Scanner;

public class CardGame {
    public final static Object lock = new Object();
    public static int turn = 0;


    public static void main(String[] args) {
        /*int numberOfPlayers = 4;
        String filePath = "src/pack2.txt";*/

        Card cardPack = new Card();

        int numberOfPlayers = 0;

        // Player number input and validation
            numberOfPlayers = validatePlayerNumber();

        // Pack path input and validation
            validateCardPack(cardPack, numberOfPlayers);

        // Initialises Players and Decks
            CardDeck[] cardDeckList = new CardDeck[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                cardDeckList[i] = new CardDeck();
            }
            Player[] playerList = new Player[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                playerList[i] = new Player(playerList, cardDeckList, i);
            }

        // Distributes Cards, starts Player threads
            cardPack.distributePack(playerList, cardDeckList);

            for (int i = 0; i < numberOfPlayers; i++) {
                playerList[i].thread.start();
            }
            synchronized (lock){
                lock.notifyAll();
            }

    }
    private static int validatePlayerNumber(){
        Scanner scanner = new Scanner(System.in);

        int numberOfPlayers = 0;

        boolean done = false;
        while (!done) {
            try {
                System.out.println("Number of players:");
                numberOfPlayers = Integer.parseInt(scanner.nextLine());
                if (numberOfPlayers > 1) {
                    done = true;
                } else {
                    System.out.println("That is not a strictly positive integer. Please input a number greater than 1");
                }
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number. Please input a valid number that is greater than 1.");
            }
        }
        return numberOfPlayers;
    }
    private static void validateCardPack(Card cardPack, int numberOfPlayers){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter filepath to a valid pack file:");
        String filePath = scanner.nextLine();
        while (!(cardPack.validatePack(numberOfPlayers, filePath))) {
            System.out.println("Please enter a valid file path to a valid pack file");
            filePath = scanner.nextLine();
        }
    }

}
