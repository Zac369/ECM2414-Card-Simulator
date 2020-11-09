import javax.sound.midi.SysexMessage;
import javax.sql.rowset.CachedRowSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CardGame {
    public static Object lock = new Object();
    public static int turn = 0;

    public static void main(String[] args) throws InterruptedException {
        int numberOfPlayers = 4;
        String filePath = "src/pack.txt";

        Scanner scanner = new Scanner(System.in);
        /*

        int numberOfPlayers = 0;

        boolean done = false;
        while (!done) {
            try {
                System.out.println("Number of players:");
                numberOfPlayers = Integer.parseInt(scanner.nextLine());
                if (numberOfPlayers > 1) {
                    done = true;
                } else {
                    System.out.println("That number is not greater than 1. Please input a number greater than 1");
                }
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number. Please input a valid number that is greater than 1.");
            }
        }

        System.out.println("Enter filepath:");
        String filePath = scanner.nextLine();*/




        Pack test = new Pack();

        while (!(test.validatePack(numberOfPlayers, filePath))) {
            System.out.println("Please enter a valid file path to a valid pack file");
            filePath = scanner.nextLine();
           /* System.out.println("Number of players:");
            numberOfPlayers = Integer.parseInt(scanner.nextLine());*/
        }

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
            //playerList[i].thread.join();

        }
        synchronized (lock){
            lock.notifyAll();
        }
        System.out.println("plop");


    }
}
