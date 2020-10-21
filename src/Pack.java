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
}

/*
import java.io.FileReader;


public class FileReaderExample {

    public static void main(String[] args) throws Exception {

        FileReader fileReader = new FileReader("C:\\Users\\Desktop\\test.txt");

        int i;

        while ((i = fileReader.read()) != -1)
            System.out.print((char) i);
    }





import java.io.*;
import java.util.Scanner;
public class ReadLineByLineExample2
{
    public static void main(String args[])
    {
        try
        {
            //the file to be opened for reading
            FileInputStream fis=new FileInputStream("Demo.txt");
            Scanner sc=new Scanner(fis);    //file to be scanned
            //returns true if there is another line to read
            while(sc.hasNextLine())
            {
                list.add(toInt((sc.nextLine()));      //returns the line that was skipped
            }
            sc.close();     //closes the scanner
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
}*/
