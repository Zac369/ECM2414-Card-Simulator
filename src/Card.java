import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Card {
    List<Integer> pack = new ArrayList<>();
    public boolean validatePack(int numberOfPlayers, String filePath) {
        pack.clear();

        try {
            //the file to be opened for reading
            FileInputStream cardPackFile = new FileInputStream(filePath);
            Scanner scanner = new Scanner(cardPackFile);
            //returns true if there is another line to read
            while (scanner.hasNextLine()) {
                String cardValue = scanner.nextLine();
                try {
                    int newCard = Integer.parseInt(cardValue);
                    if (newCard < 0) {
                        System.out.printf("Card \" %s\" should be a positive integer.%n", newCard);
                        return false;

                    }
                    pack.add(newCard);      //returns the line that was skipped

                } catch (NumberFormatException e) {
                    System.out.printf("Invalid format. The value: \"%s\" should be an integer.%n", cardValue);
                    return false;
                }
            }
            scanner.close();

            if (pack.size() == 8 * numberOfPlayers) {
                //System.out.println(pack.toString()); // for testing purposes
                return true;
            } else {
                System.out.printf("The number of cards is: %d. The expected number of cards is %s.%n", pack.size(), numberOfPlayers * 8);
                return false;
            }


        } catch (FileNotFoundException e) {
            System.out.println("File not found. Check path is right and file has read access.");
        }


        return false;
    }

    public void distributePack(Player[] playerList, CardDeck[] cardDeckList){
        int size = playerList.length;
        // Hands out cards to Players
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < size; j++) {
                    playerList[j].deck.add(pack.get(j+i*4));
                }
            }
        // Hands out cards to Decks
            for (int i = 4; i < 8; i++) {
                for (int j = 0; j < size; j++) {
                    cardDeckList[j].deck.add(pack.get(j+i*4));
                }
            }

    }
}
