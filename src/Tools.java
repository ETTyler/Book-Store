import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Tools {

    public static void append(String file, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(line);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String file, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(data);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String[]> read(String file) throws FileNotFoundException {
        try {
            ArrayList<String[]> dataList = new ArrayList<>();
            File inputFile = new File(file);
            Scanner fileScanner = new Scanner(inputFile);
            while (fileScanner.hasNextLine()) {
                String[] details = fileScanner.nextLine().split(",");
                dataList.add(details);
            }
            return dataList;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }



}
