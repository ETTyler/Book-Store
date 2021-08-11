import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * I decided to make this class and not make it object orientated because
 * I found that I needed to use these methods throughout the program and 
 * they did not really fit into any class as a method. The reason this is
 * not object orientated is because this class has no representation of any
 * part of the system and the methods are just tools I created to allow me
 * to interact with the text files. 
 */
public class Tools {

	// Appends a String line to the end of a text file.
    public static void append(String file, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
        	bw.newLine();
            bw.write(line);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Writes a String to a text file
    public static void write(String file, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(data);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Returns the contents of a text file as an ArrayList where each line is an array
    public static ArrayList<String[]> read(String file) throws FileNotFoundException {
        try {
            ArrayList<String[]> dataList = new ArrayList<>();
            File inputFile = new File(file);
            Scanner fileScanner = new Scanner(inputFile);
            while (fileScanner.hasNextLine()) {
                String[] details = fileScanner.nextLine().split(",");
                dataList.add(details);
            }
            fileScanner.close();
            return dataList;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
