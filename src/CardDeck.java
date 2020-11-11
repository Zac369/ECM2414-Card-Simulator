import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;

public class CardDeck {
    LinkedList<Integer> deck = new LinkedList<>();

    public void createFile(String pathName) {
        try {
            File outputFile = new File(pathName);
            File logsPath = new File("logs/");
            logsPath.mkdir();
            if (!outputFile.createNewFile()) {
                new FileWriter(pathName).close(); // Wipes file if file exists
            }
            // System.out.println("File created: " + outputFile.getName());
        } catch (IOException e) {
            System.out.println(MessageFormat.format("Couldn''t create log file{0}, check read write access", pathName));
            e.printStackTrace();
        }
    }

    public void writeToFile(String pathName, String content) {
        try {
            FileWriter myWriter = new FileWriter(pathName, true);
            myWriter.write(content);
            myWriter.close();
            System.out.println(content); // for testing purposes
        } catch (IOException e) {
            System.out.println(MessageFormat.format("Couldn''t write to log file{0}, check read write access.", pathName));;
            e.printStackTrace();
        }
    }
}
