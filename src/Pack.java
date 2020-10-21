import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pack{
    List<Integer> pack = new ArrayList<>();
    public boolean validatePack(int numberOfPlayers, String filePath) {

        try {
            //the file to be opened for reading
            FileInputStream fis = new FileInputStream(filePath);
            Scanner sc = new Scanner(fis);    //file to be scanned
            //returns true if there is another line to read
            while (sc.hasNextLine()) {
                String cardValue = sc.nextLine();
                try {

                    pack.add(Integer.parseInt(cardValue));      //returns the line that was skipped

                } catch (NumberFormatException e) {
                    System.out.printf("Invalid format. The value: \"%s\" should be an integer.%n", cardValue);
                    return false;
                }
            }
            sc.close();     //closes the scanner

            if (pack.size() == 8 * numberOfPlayers) {
                System.out.println(pack.toString());
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

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < size; j++) {
                playerList[j].deck.add(pack.get(j+i*4));
            }
        }
        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < size; j++) {
                cardDeckList[j].deck.add(pack.get(j+i*4));
            }
        }
        for (int i = 0; i < size; i++) {
            System.out.println(playerList[i].deck.toString());
            System.out.println(cardDeckList[i].deck.toString());
        }






    }
}
