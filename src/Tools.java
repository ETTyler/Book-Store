import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Tools {
    public Tools() {

    }

    public static void append(String file, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.newLine();
            bw.write(line);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
